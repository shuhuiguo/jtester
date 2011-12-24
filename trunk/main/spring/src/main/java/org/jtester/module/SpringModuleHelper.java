package org.jtester.module;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import mockit.internal.RedefinitionEngine;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.core.TestedContext;
import org.jtester.helper.AnnotationHelper;
import org.jtester.helper.ClazzHelper;
import org.jtester.helper.LogHelper;
import org.jtester.module.helper.ModulesManager;
import org.jtester.module.spring.ApplicationContextFactory;
import org.jtester.module.spring.JTesterBeanFactory;
import org.jtester.module.spring.JTesterSpringContext;
import org.jtester.module.spring.strategy.cleaner.SpringBeanCleaner;
import org.springframework.aop.framework.MockCglib2AopProxy;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class SpringModuleHelper {

	/**
	 * 获得当前测试类spring容器中名称为beanname的spring bean
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBeanByName(String beanname) {
		BeanFactory factory = (BeanFactory) SpringModuleHelper.getSpringBeanFactory();
		if (factory == null) {
			throw new RuntimeException("can't find SpringApplicationContext for tested class:"
					+ TestedContext.currTestedClazzName());
		} else {
			Object bean = factory.getBean(beanname);
			return bean;
		}
	}

	/**
	 * 强制重新加载spring 容器<br>
	 * Forces the reloading of the application context the next time that it is
	 * requested. If classes are given only contexts that are linked to those
	 * classes will be reset. If no classes are given, all cached contexts will
	 * be reset.
	 * 
	 * @param classes
	 *            The classes for which to reset the contexts
	 */
	public static void invalidateApplicationContext() {
		boolean springModuleEnabled = ModulesManager.isModuleEnabled(SpringModule.class);
		if (springModuleEnabled) {
			SpringModule module = ModulesManager.getModuleInstance(SpringModule.class);
			module.invalidateApplicationContext();
		}
	}

	/**
	 * 初始化当前测试类用到的spring application context对象
	 * 
	 * @param testedObject
	 * @param contextFactory
	 * @return does initial spring context successfully
	 */
	@SuppressWarnings("rawtypes")
	public static JTesterSpringContext initSpringContext(Class testClazz, ApplicationContextFactory contextFactory) {
		JTesterSpringContext springContext = SpringModuleHelper.getSpringContext();
		if (springContext != null) {
			return springContext;
		}
		SpringApplicationContext annotation = AnnotationHelper.getClassLevelAnnotation(SpringApplicationContext.class,
				testClazz);
		if (annotation == null) {
			return null;
		}

		long startTime = System.currentTimeMillis();

		String[] locations = annotation.value();
		boolean ignoreNoSuchBean = annotation.ignoreNoSuchBean();
		springContext = contextFactory.createApplicationContext(Arrays.asList(locations), ignoreNoSuchBean);

		springContext.refresh();
		long duration = System.currentTimeMillis() - startTime;
		LogHelper.warn(String.format("take %d ms to init spring context for test obejct[%s]", duration,
				testClazz.getName()));

		SpringModuleHelper.setSpringContext(springContext);
		return springContext;
	}

	/**
	 * 释放测试类的spring容器
	 * 
	 * @param springContext
	 *            AbstractApplicationContext实例，这里定义为Object是方便其它模块脱离spring依赖
	 */
	public static void closeSpringContext(Object springContext) {
		if (springContext == null) {
			return;
		}
		if (springContext instanceof JTesterSpringContext) {
			((JTesterSpringContext) springContext).destroy();
			LogHelper.warn("close spring context for class:" + TestedContext.currTestedClazzName());
		} else {
			String error = String.format("there must be something error, the type[%s] object isn't a spring context.",
					springContext.getClass().getName());
			throw new RuntimeException(error);
		}
	}

	/**
	 * 增加自动跟踪的auto tracer bean definition
	 * 
	 * @param beanFactory
	 */
	public static void addTracerBeanDefinition(final BeanDefinitionRegistry beanFactory) {
		AbstractBeanDefinition pointcut = new GenericBeanDefinition();
		pointcut.setBeanClassName(org.jtester.module.tracer.spring.TracerMethodRegexPointcut.class.getName());
		pointcut.setScope("singleton");
		pointcut.setAutowireCandidate(false);

		pointcut.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_NO);

		beanFactory.registerBeanDefinition("jtester-internal-methodname-pointcut", pointcut);

		AbstractBeanDefinition advice = new GenericBeanDefinition();
		advice.setBeanClassName(org.jtester.module.tracer.spring.SpringBeanTracer.class.getName());
		advice.setScope("singleton");
		advice.setAutowireCandidate(false);

		advice.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_NO);

		beanFactory.registerBeanDefinition("jtester-internal-springbeantracer", advice);

		AbstractBeanDefinition advisor = new GenericBeanDefinition();
		advisor.setBeanClassName(org.springframework.aop.support.DefaultPointcutAdvisor.class.getName());
		advisor.setScope("singleton");
		advisor.setAutowireCandidate(false);

		advisor.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_NO);
		advisor.getPropertyValues().addPropertyValue("pointcut",
				new RuntimeBeanReference("jtester-internal-methodname-pointcut"));
		advisor.getPropertyValues().addPropertyValue("advice",
				new RuntimeBeanReference("jtester-internal-springbeantracer"));

		beanFactory.registerBeanDefinition("jtester-internal-beantracer-advisor", advisor);
	}

	/**
	 * 清空spring AOP容器的Dump容器，防止多次启动spring容器导致的Cglib AOP内存泄漏
	 */
	public static void resetDumpReference() {
		boolean isAvailable = ClazzHelper.isClassAvailable("org.aspectj.weaver.Dump");
		if (isAvailable == false) {
			return;
		}
		org.aspectj.weaver.Dump.reset();
	}

	/**
	 * 全局性替换Cglib2AopProxy类的createEnhancer方法<br>
	 * 将net.sf.cglib.proxy.Enhancer的useCache置为false<br>
	 * 防止多次启动spring容器导致的Cglib AOP内存泄漏
	 */
	public static void mockCglibAopProxy() {
		boolean isAvailable = ClazzHelper.isClassAvailable("org.springframework.aop.framework.Cglib2AopProxy");
		if (isAvailable == false) {
			return;
		}
		new RedefinitionEngine(null, MockCglib2AopProxy.class).setUpStartupMock();
	}

	/**
	 * AbstractApplicationContext类<br>
	 * <br>
	 * <br>
	 * 第一个Object是测试类实例<br>
	 * 第二个Object是AbstractApplicationContext实例，这里定义为Object类型是为了兼容没有使用spring容器的测试
	 */
	private static Map<Class, JTesterSpringContext> springBeanFactories = new HashMap<Class, JTesterSpringContext>();

	/**
	 * 获取当前测试实例的spring application context实例
	 * 
	 * @return
	 */
	public static JTesterBeanFactory getSpringBeanFactory() {
		if (TestedContext.currTestedClazz() == null) {
			throw new RuntimeException("the tested object can't be null.");
		} else {
			JTesterSpringContext springContext = springBeanFactories.get(TestedContext.currTestedClazz());
			return springContext == null ? null : springContext.getJTesterBeanFactory();
		}
	}

	public static JTesterSpringContext getSpringContext() {
		if (TestedContext.currTestedClazz() == null) {
			throw new RuntimeException("the tested object can't be null.");
		} else {
			JTesterSpringContext springContext = springBeanFactories.get(TestedContext.currTestedClazz());
			return springContext;
		}
	}

	/**
	 * 释放spring context，清空测试类中的spring bean实例
	 */
	public static void removeSpringContext() {
		if (TestedContext.currTestedClazz() == null) {
			throw new RuntimeException("the tested object can't be null.");
		} else {
			SpringBeanCleaner.cleanSpringBeans(TestedContext.currTestedObject());
			Object springContext = springBeanFactories.remove(TestedContext.currTestedClazz());
			if (springContext != null) {
				SpringModuleHelper.closeSpringContext(springContext);
			}
		}
	}

	/**
	 * 存放当前测试实例下的spring application context实例
	 * 
	 * @param springContext
	 */
	public static void setSpringContext(JTesterSpringContext springContext) {
		if (springContext == null) {
			LogHelper.info("no spring application context for test:" + TestedContext.currTestedClazzName());
			return;
		}
		if (TestedContext.currTestedClazz() == null) {
			throw new RuntimeException("the tested object can't be null.");
		} else {
			springBeanFactories.put(TestedContext.currTestedClazz(), springContext);
		}
	}
}

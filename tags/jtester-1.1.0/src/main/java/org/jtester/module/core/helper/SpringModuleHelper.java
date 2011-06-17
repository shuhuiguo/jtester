package org.jtester.module.core.helper;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.core.TestContext;
import org.jtester.module.core.SpringModule;
import org.jtester.module.spring.ApplicationContextFactory;
import org.jtester.utility.AnnotationUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.AbstractApplicationContext;

public class SpringModuleHelper {
	private final static Logger log4j = Logger.getLogger(SpringModuleHelper.class);

	/**
	 * 获得当前测试类spring容器中名称为beanname的spring bean
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBeanByName(String beanname) {
		BeanFactory factory = (BeanFactory) TestContext.getSpringContext();
		if (factory == null) {
			throw new RuntimeException("can't find SpringApplicationContext for tested class:"
					+ TestContext.currTestedClazzName());
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
	public static AbstractApplicationContext initSpringContext(Object testedObject,
			ApplicationContextFactory contextFactory) {
		AbstractApplicationContext context = (AbstractApplicationContext) TestContext.getSpringContext();
		if (context != null) {
			return context;
		}
		SpringApplicationContext annotation = AnnotationUtils.getClassLevelAnnotation(SpringApplicationContext.class,
				testedObject.getClass());
		if (annotation == null) {
			return null;
		}

		long startTime = System.currentTimeMillis();

		String[] locations = annotation.value();
		boolean ignoreNoSuchBean = annotation.ignoreNoSuchBean();
		context = contextFactory.createApplicationContext(Arrays.asList(locations), ignoreNoSuchBean);

		context.refresh();
		long duration = System.currentTimeMillis() - startTime;
		log4j.warn(String.format("take %d ms to init spring context for test obejct[%s]", duration, testedObject
				.getClass().getName()));

		TestContext.setSpringContext(context);
		return context;
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
		if (springContext instanceof AbstractApplicationContext) {
			((AbstractApplicationContext) springContext).close();
			springContext = null;
			log4j.warn("close spring context for class:" + TestContext.currTestedClazzName());
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
}

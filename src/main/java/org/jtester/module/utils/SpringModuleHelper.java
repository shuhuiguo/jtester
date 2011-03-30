package org.jtester.module.utils;

import java.sql.Connection;
import java.util.Arrays;

import javax.sql.DataSource;

import org.jtester.core.intf.ISpringContextContainer;
import org.jtester.exception.JTesterException;
import org.jtester.module.ModulesRepository;
import org.jtester.module.core.SpringModule;
import org.jtester.module.spring.ApplicationContextFactory;
import org.jtester.utility.AnnotationUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.unitils.spring.annotation.SpringApplicationContext;

public class SpringModuleHelper {

	/**
	 * 返回测试类的spring容器对象
	 * 
	 * @param testedObject
	 * @return
	 */
	public static AbstractApplicationContext getContext(Object testedObject) {
		if (testedObject == null || !(testedObject instanceof ISpringContextContainer)) {
			throw new JTesterException("tested object must be inherit from ISpringContextContainer");
		}
		ISpringContextContainer container = (ISpringContextContainer) testedObject;
		AbstractApplicationContext context = (AbstractApplicationContext) container.getSpringContext();
		if (context == null) {
			throw new JTesterException("Unable to get SpringContext for tested class:"
					+ testedObject.getClass().getName());
		} else {
			return context;
		}
	}

	public static BeanFactory getBeanFactory(Object testObject) {
		AbstractApplicationContext context = getContext(testObject);
		return context.getBeanFactory();
	}

	/**
	 * 获得当前测试类spring容器中名称为beanname的spring bean
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBeanByName(Object testedObject, String beanname) {
		BeanFactory factory = getBeanFactory(testedObject);

		return factory.getBean(beanname);
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
		ModulesRepository modulesRepository = ModuleHelper.getInstance().getModulesRepository();
		if (!modulesRepository.isModuleEnabled(SpringModule.class)) {
			return;
		}

		SpringModule module = (SpringModule) modulesRepository.getModuleOfType(SpringModule.class);
		module.invalidateApplicationContext();
	}

	/**
	 * 初始化当前测试类用到的spring application context对象
	 * 
	 * @param testedObject
	 * @param applicationContextFactory
	 * @return does initial spring context successfully
	 */
	public static boolean initSpringContext(Object testedObject, ApplicationContextFactory applicationContextFactory) {
		ISpringContextContainer contextContainer = (ISpringContextContainer) testedObject;
		AbstractApplicationContext context = (AbstractApplicationContext) contextContainer.getSpringContext();
		if (context != null) {
			return false;
		}
		SpringApplicationContext annotation = AnnotationUtils.getClassLevelAnnotation(SpringApplicationContext.class,
				testedObject.getClass());
		if (annotation == null) {
			return false;
		}

		String[] locations = annotation.value();
		boolean ignoreNoSuchBean = annotation.ignoreNoSuchBean();
		context = applicationContextFactory.createApplicationContext(testedObject, Arrays.asList(locations),
				ignoreNoSuchBean);

		context.refresh();
		contextContainer.setSpringContext(context);
		return true;
	}

	/**
	 * 释放测试类的spring容器
	 * 
	 * @param testedObject
	 */
	public static void closeSpringContext(Object testedObject) {
		ISpringContextContainer container = (ISpringContextContainer) testedObject;
		if (container == null) {
			throw new RuntimeException("tested object can't be null!");
		}
		SpringModuleHelper.cleanTestedObjectSpringBean(testedObject);
		AbstractApplicationContext context = (AbstractApplicationContext) container.getSpringContext();
		container.setSpringContext(null);
		if (context != null) {
			context.close();
			context = null;
		}
	}

	/**
	 * 往测试类实例中注入spring bean
	 * 
	 * @param testObject
	 */
	public static void injectTestedObjectSpringBean(Object testObject) {
		SpringModule.injectSpringBeansByType(testObject);
		SpringModule.injectSpringBeansByName(testObject);
	}

	/**
	 * 清空测试实例中spring bean的引用
	 * 
	 * @param testedObject
	 */
	public static void cleanTestedObjectSpringBean(Object testedObject) {
		SpringModule.cleanSpringBeansByType(testedObject);
		SpringModule.cleanSpringBeansByName(testedObject);
	}

	public static Connection getConnection(DataSource dataSource) {
		try {
			return DataSourceUtils.getConnection(dataSource);
		} catch (Exception ex) {
			return null;
		}
	}
}

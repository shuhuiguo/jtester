package org.jtester.module.spring;

import java.lang.reflect.Field;

import javax.sql.DataSource;

import org.jtester.module.database.transaction.TransactionManager;
import org.jtester.module.jmock.MockBeanRegister;
import org.jtester.module.tracer.TracerBeanManager;
import org.jtester.module.utils.ConfigUtil;
import org.jtester.module.utils.FieldProxy;
import org.jtester.utility.JTesterLogger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

@SuppressWarnings("rawtypes")
public class JTesterBeanFactory extends DefaultListableBeanFactory {
	private final Object testedObject;

	private boolean ignoreNoSuchBean;

	public JTesterBeanFactory(final BeanFactory parentBeanFactory, final Object testedObject, boolean ignoreNoSuchBean) {
		super(parentBeanFactory);
		this.testedObject = testedObject;
		this.ignoreNoSuchBean = ignoreNoSuchBean;
		if (testedObject == null) {
			throw new RuntimeException("tested object can't be null!");
		}
	}

	@Override
	public Object getBean(final String name, final Class requiredType, final Object[] args) throws BeansException {
		try {
			Object bean = getMyBean(name, requiredType, args);
			return bean;
		} catch (NoSuchBeanDefinitionException e) {
			if (ignoreNoSuchBean) {
				JTesterLogger.info("Ignore NoSuchBeanDefinitionException:" + e.getMessage());
				return null;
			} else {
				throw e;
			}
		}
	}

	private Object getMyBean(final String name, final Class requiredType, final Object[] args) throws BeansException {
		if (MockBeanRegister.hasRegistedName(name)) {
			Field mockField = MockBeanRegister.getRegistedField(name);
			Object mockBean = FieldProxy.proxy(testedObject, mockField);
			return mockBean;
		} else if (ConfigUtil.isSpringDataSourceName(name)) {
			DataSource dataSource = TransactionManager.getDataSource();
			return dataSource;
		} else {
			Object bean = super.getBean(name, requiredType, args);
			return bean;
		}
	}

	@Override
	public boolean containsBean(String name) {
		if (MockBeanRegister.hasRegistedName(name)) {
			return true;
		} else {
			return super.containsBean(name);
		}
	}

	@Override
	public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) {
		TracerBeanManager.registerBean(existingBean, beanName);
		return super.applyBeanPostProcessorsBeforeInitialization(existingBean, beanName);
	}

	public Object getTestedObject() {
		return testedObject;
	}
}

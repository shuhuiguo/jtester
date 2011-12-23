package org.jtester.module.forbid.model;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jtester.exception.ForbidCallException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ForTesedForbidBeanFactoryMock implements FactoryBean, BeanClassLoaderAware, InitializingBean,
		ApplicationContextAware, BeanNameAware, MethodInterceptor {
	private Object refBean;

	public void setRefBean(Object refBean) {
		this.refBean = refBean;
	}

	public Object invoke(MethodInvocation method) throws Throwable {
		throw new ForbidCallException("forbid call.");
	}

	public void setBeanName(String name) {
		throw new ForbidCallException("forbid call.");
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		throw new ForbidCallException("forbid call.");
	}

	public void afterPropertiesSet() throws Exception {
		throw new ForbidCallException("forbid call.");
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		throw new ForbidCallException("forbid call.");
	}

	public Object getObject() throws Exception {
		return this.refBean;
	}

	@SuppressWarnings("rawtypes")
	public Class getObjectType() {
		return this.refBean.getClass();
	}

	public boolean isSingleton() {
		return true;
	}
}

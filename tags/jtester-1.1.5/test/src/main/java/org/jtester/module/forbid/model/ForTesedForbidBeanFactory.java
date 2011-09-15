package org.jtester.module.forbid.model;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ForTesedForbidBeanFactory implements FactoryBean, BeanClassLoaderAware, InitializingBean,
		ApplicationContextAware, BeanNameAware, MethodInterceptor {
	private Object refBean;

	public void setRefBean(Object refBean) {
		this.refBean = refBean;
	}

	public Object invoke(MethodInvocation method) throws Throwable {
		return method.proceed();
	}

	public void setBeanName(String name) {
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}

	public void afterPropertiesSet() throws Exception {
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
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

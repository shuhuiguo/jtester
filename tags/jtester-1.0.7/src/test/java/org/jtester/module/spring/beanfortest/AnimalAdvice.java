package org.jtester.module.spring.beanfortest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

public class AnimalAdvice implements MethodInterceptor {
	private final static Logger log4j = Logger.getLogger(AnimalAdvice.class);

	public Object invoke(MethodInvocation invocation) throws Throwable {
		StringBuilder sb = new StringBuilder();
		sb.append("Target Class:").append(invocation.getThis()).append("\n").append(invocation.getMethod())
				.append("\n");
		Object retVal = invocation.proceed();
		sb.append(" return value:").append(retVal).append("\n");
		log4j.info(sb.toString());
		return retVal;
	}
}

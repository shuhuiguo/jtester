package org.jtester.module.spring.autowired;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.jtester.testng.JTester;
import org.springframework.aop.framework.ProxyFactory;
import org.testng.annotations.Test;

public class SpringAroundTest extends JTester {
	private final static Logger log4j = Logger.getLogger(SpringAroundTest.class);

	@Test
	public void testSpringAround() {
		log4j.info("test spring around");
		Hello target = new Hello();// target class
		ProxyFactory pf = new ProxyFactory();// create the proxy
		pf.addAdvice(new MethodTracer());// add advice
		pf.setTarget(target);// setTarget
		Hello proxy = (Hello) pf.getProxy();
		proxy.greeting();
	}

	public static class MethodTracer implements MethodInterceptor {

		public Object invoke(MethodInvocation invocation) throws Throwable {
			log4j.info("before invoke");
			Object o = invocation.proceed();
			log4j.info("after invoke");
			return o;
		}
	}

	public static class Hello {
		public void greeting() {
			log4j.info("reader");
		}
	}
}

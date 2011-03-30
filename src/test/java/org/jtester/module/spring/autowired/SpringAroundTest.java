package org.jtester.module.spring.autowired;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jtester.testng.JTester;
import org.jtester.utility.JTesterLogger;
import org.springframework.aop.framework.ProxyFactory;
import org.testng.annotations.Test;

public class SpringAroundTest extends JTester {
	@Test
	public void testSpringAround() {
		JTesterLogger.info("test spring around");
		Hello target = new Hello();// target class
		ProxyFactory pf = new ProxyFactory();// create the proxy
		pf.addAdvice(new MethodTracer());// add advice
		pf.setTarget(target);// setTarget
		Hello proxy = (Hello) pf.getProxy();
		proxy.greeting();
	}

	public static class MethodTracer implements MethodInterceptor {

		public Object invoke(MethodInvocation invocation) throws Throwable {
			JTesterLogger.info("before invoke");
			Object o = invocation.proceed();
			JTesterLogger.info("after invoke");
			return o;
		}
	}

	public static class Hello {
		public void greeting() {
			JTesterLogger.info("reader");
		}
	}
}

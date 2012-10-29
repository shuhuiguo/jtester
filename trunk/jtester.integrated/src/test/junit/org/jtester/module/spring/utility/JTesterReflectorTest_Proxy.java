package org.jtester.module.spring.utility;

import org.jtester.junit.JTester;
import org.jtester.module.spring.annotations.AutoBeanInject;
import org.jtester.module.spring.annotations.SpringContext;
import org.jtester.module.spring.annotations.SpringBeanByName;
import org.junit.Test;

@SpringContext("org/jtester/module/spring/testedbeans/xml/data-source.xml")
@AutoBeanInject
public class JTesterReflectorTest_Proxy extends JTester {
	@SpringBeanByName(claz = MyImpl.class)
	MyIntf myIntf;

	@Test
	public void testProxy_SetValueInotSpringProxy() {
		reflector.setField(myIntf, "myHello", "proxy private field");
		String hello = myIntf.hello();
		want.string(hello).isEqualTo("proxy private field");
	}

	@Test
	public void testProxy_GetValueFromSpringProxy() {
		reflector.invoke(myIntf, "initHell", "get proxy private field");
		String hello = (String) reflector.getField(myIntf, "myHello");
		want.string(hello).isEqualTo("get proxy private field");
	}

	@Test
	public void testProxy_CallSpringProxyPrivateMethod() {
		reflector.invoke(myIntf, "initHell", "proxy private method");
		String hello = myIntf.hello();
		want.string(hello).isEqualTo("proxy private method");
	}

	public static interface MyIntf {
		String hello();
	}

	public static class MyImpl implements MyIntf {
		private String myHello = "my hello";

		@SuppressWarnings("unused")
		private void initHell(String hello) {
			this.myHello = hello;
		}

		public String hello() {
			return myHello;
		}
	}
}

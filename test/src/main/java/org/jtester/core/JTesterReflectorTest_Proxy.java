package org.jtester.core;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SpringApplicationContext("org/jtester/module/spring/testedbeans/xml/data-source.xml")
@AutoBeanInject
public class JTesterReflectorTest_Proxy extends JTester {
	@SpringBeanByName(claz = MyImpl.class)
	MyIntf myIntf;

	public void testProxy_spring代理的字段赋值() {
		reflector.setField(myIntf, "myHello", "proxy private field");
		String hello = myIntf.hello();
		want.string(hello).isEqualTo("proxy private field");
	}

	public void testProxy_获取spring代理的字段值() {
		reflector.invoke(myIntf, "initHell", "get proxy private field");
		String hello = (String) reflector.getField(myIntf, "myHello");
		want.string(hello).isEqualTo("get proxy private field");
	}

	public void testProxy_调用spring代理的私有方法() {
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

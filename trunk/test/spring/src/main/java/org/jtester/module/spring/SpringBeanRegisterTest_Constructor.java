package org.jtester.module.spring;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SpringApplicationContext
@AutoBeanInject
@Test(groups = "for-test")
public class SpringBeanRegisterTest_Constructor implements IAssertion {

	@SpringBeanByName
	BeanClass bean;

	public void test_自动注册的Clazz没有默认的构造函数() {

	}

	public static class BeanClass {
		public BeanClass(String value) {

		}
	}
}

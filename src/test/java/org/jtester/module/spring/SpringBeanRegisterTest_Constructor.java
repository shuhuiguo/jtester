package org.jtester.module.spring;

import org.jtester.testng.JTester;
import org.jtester.unitils.spring.AutoBeanInject;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SpringApplicationContext
@AutoBeanInject
@Test(groups = "for-test")
public class SpringBeanRegisterTest_Constructor extends JTester {

	@SpringBeanByName
	BeanClass bean;

	public void test_自动注册的Clazz没有默认的构造函数() {

	}

	public static class BeanClass {
		public BeanClass(String value) {

		}
	}
}

package org.jtester.module.spring;

import org.jtester.IAssertion;
import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.junit.Test;

@SpringApplicationContext
@AutoBeanInject
public class SpringBeanRegisterTest_Constructor implements IAssertion {

	@SpringBeanByName
	BeanClass bean;

	@Test
	public void test_自动注册的Clazz没有默认的构造函数() {

	}

	public static class BeanClass {
		public BeanClass(String value) {

		}
	}
}

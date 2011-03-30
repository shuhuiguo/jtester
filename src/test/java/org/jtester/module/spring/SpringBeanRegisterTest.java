package org.jtester.module.spring;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.core.testng.TestNgUtil;
import org.jtester.testng.JTester;
import org.jtester.utility.StringHelper;
import org.testng.annotations.Test;

@SpringApplicationContext
@AutoBeanInject
@Test(groups = "jtester")
public class SpringBeanRegisterTest extends JTester {

	@SpringBeanByName
	BeanClass bean;

	public void test_自动注入排除Enum类型() {
		want.object(bean.beanEnum).isNull();
		want.object(bean.i).isEqualTo(0);
		want.object(bean.array).isNull();
	}

	public void test_() {
		try {
			TestNgUtil.run(SpringBeanRegisterTest_Constructor.class.getName(), "test_自动注册的Clazz没有默认的构造函数", true);
			want.fail();
		} catch (Exception e) {
			String error = StringHelper.exceptionTrace(e);
			want.string(error)
					.contains(
							"find default constructor function of class[org.jtester.module.spring.SpringBeanRegisterTest_Constructor$BeanClass] error.");
		}
	}

	public static class BeanClass {
		BeanEnum beanEnum;

		int i;

		Object[] array;

		public void setBeanEnum(BeanEnum beanEnum) {
			this.beanEnum = BeanEnum.one;
		}

		public void setI(int i) {
			this.i = 100;
		}

		public void setArray(Object[] array) {
			this.array = new Object[] {};
		}
	}

	public static enum BeanEnum {
		one, two;
	}
}

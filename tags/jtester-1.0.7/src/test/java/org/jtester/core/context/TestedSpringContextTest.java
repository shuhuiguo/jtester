package org.jtester.core.context;

import org.jtester.testng.JTester;
import org.jtester.unitils.spring.AutoBeanInject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@Test(groups = "jtester")
@SpringApplicationContext("org/jtester/fortest/spring/data-source.cglib.xml")
@AutoBeanInject
public class TestedSpringContextTest extends JTester {

	@SpringBeanByName(claz = SpringAfterInit.class)
	SpringAfterInit bean;

	@Test
	public void testInvalidate_未失效() {
		String ret = bean.getProp();
		want.string(ret).isEqualTo("unset");

		TestedSpringContextTest.prop = "invalid";
		spring.invalidate();
	}

	@Test(dependsOnMethods = "testInvalidate_未失效")
	public void testInvalidate_失效() {
		String ret = bean.getProp();
		want.string(ret).isEqualTo("invalid");
	}

	public static class SpringAfterInit implements BeanFactoryAware {
		private String prop = "uninit";

		public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
			this.prop = TestedSpringContextTest.prop;
		}

		public String getProp() {
			return prop;
		}
	}

	static String prop = "unset";
}

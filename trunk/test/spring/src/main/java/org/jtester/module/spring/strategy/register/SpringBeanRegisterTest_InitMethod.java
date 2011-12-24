package org.jtester.module.spring.strategy.register;

import org.jtester.IAssertion;
import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanByType;
import org.junit.Test;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
public class SpringBeanRegisterTest_InitMethod implements IAssertion {
	@SpringBeanByName(claz = InitMethodBeanByName.class, init = "init")
	InitMethodBeanByName beanByName;

	@SpringBeanByType(value = InitMethodBeanByType.class, init = "init")
	InitMethodBeanByType beanByType;

	/**
	 * 自动注册bean时识别init-method
	 */
	@Test
	public void testBeanDefinitionByName_InitMethod() {
		String result = beanByName.say();
		want.string(result).isEqualTo("bean init");
	}

	/**
	 * 自动注册bean时识别init-method
	 */
	@Test
	public void testBeanDefinitionByType_InitMethod() {
		String result = beanByType.say();
		want.string(result).isEqualTo("bean init");
	}

	public static class InitMethodBeanByName {

		private String name = "uninit";

		public String say() {
			return name;
		}

		public void init() {
			this.name = "bean init";
		}
	}

	public static class InitMethodBeanByType {

		private String name = "uninit";

		public String say() {
			return name;
		}

		public void init() {
			this.name = "bean init";
		}
	}
}

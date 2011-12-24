package org.jtester.module.spring;

import org.jtester.IAssertion;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fortest.hibernate.AddressService;
import org.junit.Before;
import org.junit.Test;

@SpringApplicationContext({ "file:./extern-spring/project.xml" })
public class SpringModuleTest implements IAssertion {
	@SpringBeanByName("addressService")
	private AddressService addressService;

	/**
	 * 更改spring bean的字段的值
	 */
	@Test
	public void testBeforeTestSetUp_ChangeSpringBeanValue() {
		this.addressService = null;
	}

	/**
	 * 验证spring bean的字段的值是从spring容器重新注入的
	 */
	@Test
	// (dependsOnMethods = "testBeforeTestSetUp_ChangeSpringBeanValue"
	public void testBeforeTestSetUp_CheckSpringBeanValue() {
		want.object(addressService).notNull();
	}

	@Before
	public void atestSetupClass() {
		want.object(addressService).notNull();
	}
}

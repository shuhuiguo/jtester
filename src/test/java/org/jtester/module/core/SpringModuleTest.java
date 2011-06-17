package org.jtester.module.core;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fortest.hibernate.AddressService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SpringApplicationContext({ "file:./extern-spring/project.xml" })
@Test(groups = "jtester")
public class SpringModuleTest extends JTester {
	@SpringBeanByName("addressService")
	private AddressService addressService;

	@Test(description = "更改spring bean的字段的值")
	public void testBeforeTestSetUp_ChangeSpringBeanValue() {
		this.addressService = null;
	}

	@Test(dependsOnMethods = "testBeforeTestSetUp_ChangeSpringBeanValue", description = "验证spring bean的字段的值是从spring容器重新注入的")
	public void testBeforeTestSetUp_CheckSpringBeanValue() {
		want.object(addressService).notNull();
	}
}

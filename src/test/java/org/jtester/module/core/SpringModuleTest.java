package org.jtester.module.core;

import org.jtester.fortest.hibernate.AddressService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SpringApplicationContext({ "file:./extern-spring/project.xml" })
@Test(groups = "jtester")
public class SpringModuleTest extends JTester {
	@SpringBeanByName("addressService")
	private AddressService addressService;

	@Test
	public void testInitDb() {
		want.object(addressService).notNull();
	}
}

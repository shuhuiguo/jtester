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

	@Test
	public void testInitDb() {
		want.object(addressService).notNull();
	}
}

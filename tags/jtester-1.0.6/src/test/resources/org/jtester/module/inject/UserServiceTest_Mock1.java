package org.jtester.module.inject;

import mockit.Mocked;

import org.jtester.annotations.Inject;
import org.jtester.fortest.hibernate.AddressService;
import org.jtester.fortest.hibernate.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SpringApplicationContext({ "classpath:/org/jtester/fortest/hibernate/project.xml" })
@Test(groups = { "jtester", "hibernate" })
public class UserServiceTest_Mock1 extends JTester {
	@SpringBeanByName("userService")
	private UserService userService;

	@Mocked
	@Inject(targets = "userService")
	private AddressService addressService;

	@Test
	public void findAddress() {
		want.object(addressService).notNull();
		want.object(userService).notNull();
		new Expectations() {
			{
				when(addressService.findAddress()).thenReturn("文二路120#");
			}
		};
		String address = userService.findAddress();
		want.string(address).contains("120#");
	}

	@Test
	public void findAddress02() {
		want.object(addressService).notNull();
		want.object(userService).notNull();
		new Expectations() {
			{
				when(addressService.findAddress()).thenReturn("文二路120#");
			}
		};
		String address = userService.findAddress();
		want.string(address).contains("120#");
	}
}

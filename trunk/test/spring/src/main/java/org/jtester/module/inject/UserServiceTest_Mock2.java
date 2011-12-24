package org.jtester.module.inject;

import mockit.Mocked;

import org.jtester.IAssertion;
import org.jtester.annotations.Inject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fortest.hibernate.AddressService;
import org.jtester.fortest.hibernate.UserService;
import org.junit.Test;

@SpringApplicationContext({ "classpath:/org/jtester/fortest/hibernate/project.xml" })
public class UserServiceTest_Mock2 implements IAssertion {
	@SpringBeanByName("userService")
	private UserService userService1;

	@Mocked
	@Inject(targets = "userService1")
	private AddressService addressService1;

	@Test
	public void findAddress() {
		want.object(addressService1).notNull();
		want.object(userService1).notNull();
		new Expectations() {
			{
				when(addressService1.findAddress()).thenReturn("文三路131#");
			}
		};
		String address = userService1.findAddress();
		want.string(address).contains("131#");
	}
}

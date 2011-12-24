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
public class UserServiceTest_ByInjectedMock implements IAssertion {
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
}

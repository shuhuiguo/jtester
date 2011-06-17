package org.jtester.module.jmock;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.deprecated.Mock;
import org.jtester.core.Je;
import org.jtester.fortest.hibernate.AddressService;
import org.jtester.fortest.hibernate.UserService;
import org.jtester.module.core.helper.JmockModuleHelper;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings("deprecation")
@SpringApplicationContext({ "classpath:/org/jtester/fortest/hibernate/project.xml" })
@Test(groups = { "jtester", "hibernate" })
public class UserServiceTest_Mock1 extends JTester {
	@SpringBeanByName("userService")
	private UserService userService;

	@Mock(injectInto = "userService")
	private AddressService addressService;

	@Test
	public void findAddress() {
		want.object(addressService).notNull();
		want.object(userService).notNull();
		JmockModuleHelper.checking(new Je() {
			{
				will.call.one(addressService).findAddress();
				will.returns.value("文二路120#");
			}
		});
		String address = userService.findAddress();
		want.string(address).contains("120#");
	}

	@Test
	public void findAddress02() {
		want.object(addressService).notNull();
		want.object(userService).notNull();
		JmockModuleHelper.checking(new Je() {
			{
				will.call.one(addressService).findAddress();
				will.returns.value("文二路120#");
			}
		});
		String address = userService.findAddress();
		want.string(address).contains("120#");
	}
}

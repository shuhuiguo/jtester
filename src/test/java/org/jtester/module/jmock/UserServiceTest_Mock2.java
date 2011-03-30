package org.jtester.module.jmock;

import org.jtester.annotations.deprecated.Mock;
import org.jtester.core.Je;
import org.jtester.fortest.hibernate.AddressService;
import org.jtester.fortest.hibernate.UserService;
import org.jtester.module.utils.JmockModuleHelper;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SuppressWarnings("deprecation")
@SpringApplicationContext({ "classpath:/org/jtester/fortest/hibernate/project.xml" })
@Test(groups = { "jtester", "hibernate" })
public class UserServiceTest_Mock2 extends JTester {
	@SpringBeanByName("userService")
	private UserService userService1;

	@Mock(injectInto = "userService1")
	private AddressService addressService1;

	@Test
	public void findAddress() {
		want.object(addressService1).notNull();
		want.object(userService1).notNull();
		JmockModuleHelper.checking(new Je() {
			{
				will.call.one(addressService1).findAddress();
				will.returns.value("文三路131#");
			}
		});
		String address = userService1.findAddress();
		want.string(address).contains("131#");
	}
}

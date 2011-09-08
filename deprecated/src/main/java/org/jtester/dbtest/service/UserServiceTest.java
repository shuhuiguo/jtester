package org.jtester.dbtest.service;

import java.util.Collection;

import org.jtester.fortest.hibernate.Address;
import org.jtester.fortest.hibernate.User;
import org.jtester.fortest.hibernate.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;

@org.jtester.annotations.SpringApplicationContext({ "classpath:/org/jtester/fortest/hibernate/project.xml" })
@Test(groups = "jtester")
public class UserServiceTest extends JTester {
	@org.jtester.annotations.SpringBeanByType
	private UserService userService;

	@Test
	@DataSet({ "UserServiceTest.getUser.xml" })
	@ExpectedDataSet({ "UserServiceTest.getUser.xml" })
	public void getUser() {
		User user1 = userService.getUser(1);
		want.object(user1).notNull();
		User user2 = userService.getUser(2);
		want.object(user2).notNull();

		User user3 = userService.getUser(3);
		want.object(user3).isNull();
		User user4 = userService.getUser(4);
		want.object(user4).isNull();
	}

	@Test
	@DataSet({ "UserServiceTest.getUser_LazyAddress.xml" })
	@ExpectedDataSet({ "UserServiceTest.getUser_LazyAddress.expected.xml" })
	public void getUser_LazyAddress() {
		User user = userService.getUser(1);
		want.object(user).notNull();

		want.object(user.getAddresses()).notNull();
		Collection<Address> addresses = user.getAddresses();
		want.object(addresses.size()).isEqualTo(1);
		for (Address address : addresses) {
			want.string(address.getCity()).contains("city");
		}
	}
}

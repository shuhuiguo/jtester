package org.jtester.module.jmockit;

import java.util.ArrayList;
import java.util.List;

import mockit.Mock;
import mockit.Mocked;
import mockit.Mockit;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserDao;
import org.jtester.fortest.service.UserDaoImpl;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SpringApplicationContext( { "org/jtester/fortest/spring/beans.xml", "org/jtester/fortest/spring/data-source.xml" })
public class MockitSpringBeanTest extends JTester {
	@SpringBeanByName
	private UserService userService;

	@SpringBeanByName
	private UserDao userDao;

	@DbFit(when = "MockitSpringBeanTest.wiki")
	public void parySalary_mockBean() {
		new Expectations() {
			@Mocked(methods = "findUserByPostcode")
			UserDaoImpl userDaoImpl;
			{
				when(userDaoImpl.findUserByPostcode(anyString)).thenReturn(getUserList());
			}
		};
		double total = this.userService.paySalary("310000");
		want.number(total).isEqualTo(4300d);

		List<User> users = userDao.findAllUser();
		want.number(users.size()).isEqualTo(2);
	}

	@DbFit(when = "MockitSpringBeanTest.wiki")
	public void paySalary() {
		Mockit.setUpMock(UserDaoImpl.class, MockUserDao.class);

		// mock的行为
		double total = this.userService.paySalary("310000");
		want.number(total).isEqualTo(4300d);

		// not mock的行为
		List<User> users = userDao.findAllUser();
		want.number(users.size()).isEqualTo(2);
		Mockit.tearDownMocks();
	}

	public static class MockUserDao {
		@Mock
		public List<User> findUserByPostcode(String postcode) {
			return getUserList();
		}
	}

	private static List<User> getUserList() {
		return new ArrayList<User>() {
			private static final long serialVersionUID = -2799578129563837839L;
			{
				this.add(new User(1, 1000d));
				this.add(new User(2, 1500d));
				this.add(new User(2, 1800d));
			}
		};
	}
}

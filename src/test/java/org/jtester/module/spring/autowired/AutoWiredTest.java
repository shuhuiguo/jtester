package org.jtester.module.spring.autowired;

import mockit.Mock;
import mockit.Mocked;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByType;
import org.jtester.annotations.SpringBeanFrom;
import org.jtester.fortest.autowired.IUserDao;
import org.jtester.fortest.autowired.IUserService;
import org.jtester.fortest.autowired.UserDaoImpl;
import org.jtester.fortest.beans.User;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SpringApplicationContext({ "org/jtester/fortest/autowired/springbeans-component.xml" })
public class AutoWiredTest extends JTester {

	@SpringBeanByType
	IUserService userService;

	@SpringBeanFrom("userDaoImpl")
	@Mocked
	IUserDao userDao;

	public void test() {
		new MockUp<UserDaoImpl>() {
			@SuppressWarnings("unused")
			@Mock
			public void insertUser(User user) {
				want.fail("can't be execute");
			}
		};
		new Expectations() {
			{
				userDao.insertUser((User) any);
			}
		};
		userService.insertUser(null);
	}
}

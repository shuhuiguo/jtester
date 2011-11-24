package org.jtester.module.spring.resource;

import mockit.Mock;
import mockit.Mocked;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanFrom;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserDao;
import org.jtester.fortest.service.UserService;
import org.jtester.module.spring.testedbeans.resource.UserDaoResourceImpl;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/resource/resource-bean.xml" })
public class ResourceBeanTest_SpringBeanFrom extends JTester {

	@SpringBeanByName
	UserService userService;

	@SpringBeanFrom
	@Mocked
	UserDao userDao;

	public void testResourceBean() {
		new MockUp<UserDaoResourceImpl>() {
			@SuppressWarnings("unused")
			@Mock
			public void insertUser(User user) {
				want.fail("this api can't be invoke.");
			}
		};

		new Expectations() {
			{
				userDao.insertUser((User) any);
			}
		};
		userService.insertUser(new User());
	}
}

package org.jtester.module.spring.autowired;

import mockit.Mock;
import mockit.Mocked;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByType;
import org.jtester.annotations.SpringBeanFrom;
import org.jtester.fortest.beans.User;
import org.jtester.module.spring.testedbeans.autowired.IUserDao;
import org.jtester.module.spring.testedbeans.autowired.IUserService;
import org.jtester.module.spring.testedbeans.autowired.UserDaoImpl;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/autowired/autowired-scan.xml" })
public class AutoWiredTest_AutoScan extends JTester {

	@SpringBeanByType
	IUserService userService;

	@SpringBeanFrom("userDaoImpl")
	@Mocked
	IUserDao userDao;

	@Test(description = "@AutoWired自动包扫描情况下，使用@SpringBeanFrom来替换spring扫描到的bean")
	public void testAutoWired_AutoScan() {
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

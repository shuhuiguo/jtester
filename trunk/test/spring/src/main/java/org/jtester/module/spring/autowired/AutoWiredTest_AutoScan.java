package org.jtester.module.spring.autowired;

import mockit.Mock;
import mockit.Mocked;

import org.jtester.IAssertion;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByType;
import org.jtester.annotations.SpringBeanFrom;
import org.jtester.fortest.beans.User;
import org.junit.Test;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/autowired/autowired-scan.xml" })
public class AutoWiredTest_AutoScan implements IAssertion {

	@SpringBeanByType
	IUserService userService;

	@SpringBeanFrom("userDaoImpl")
	@Mocked
	IUserDao userDao;

	/**
	 * @AutoWired自动包扫描情况下，使用@SpringBeanFrom来替换spring扫描到的bean
	 */
	@Test
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

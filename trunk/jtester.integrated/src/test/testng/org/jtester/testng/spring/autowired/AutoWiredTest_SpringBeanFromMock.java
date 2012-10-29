package org.jtester.testng.spring.autowired;

import mockit.Mock;
import mockit.Mocked;

import org.jtester.fortest.beans.User;
import org.jtester.module.spring.annotations.SpringContext;
import org.jtester.module.spring.annotations.SpringBeanByType;
import org.jtester.module.spring.annotations.SpringBeanFrom;
import org.jtester.module.spring.testedbeans.autowired.IUserDao;
import org.jtester.module.spring.testedbeans.autowired.IUserService;
import org.jtester.module.spring.testedbeans.autowired.UserDaoImpl;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SpringContext({ "org/jtester/module/spring/testedbeans/autowired/autowired.xml" })
public class AutoWiredTest_SpringBeanFromMock extends JTester {

	@SpringBeanByType
	IUserService userService;

	@SpringBeanFrom
	@Mocked
	IUserDao userDao;

	@Test(description = "测试@AutoWired加载的bean被@SpringBeanFrom方式注入的bean替换")
	public void testAutoWired() {
		new MockUp<UserDaoImpl>() {
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

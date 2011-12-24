package org.jtester.module.spring.resource;

import mockit.Mock;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserService;
import org.jtester.module.spring.testedbeans.resource.UserDaoResourceImpl;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/resource/resource-bean.xml" })
public class ResourceBeanTest_NormalLoading implements IAssertion {

	@SpringBeanByName
	UserService userService;

	public void testResourceBean() {
		final boolean[] checked = new boolean[] { false };
		new MockUp<UserDaoResourceImpl>() {
			@SuppressWarnings("unused")
			@Mock
			public void insertUser(User user) {
				checked[0] = true;
			}
		};

		userService.insertUser(new User());
		want.bool(checked[0]).is(true);
	}
}

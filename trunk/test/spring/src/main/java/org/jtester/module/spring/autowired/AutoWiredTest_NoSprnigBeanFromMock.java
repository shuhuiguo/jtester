package org.jtester.module.spring.autowired;

import mockit.Mock;

import org.jtester.IAssertion;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByType;
import org.jtester.fortest.beans.User;
import org.junit.Test;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/autowired/autowired.xml" })
public class AutoWiredTest_NoSprnigBeanFromMock implements IAssertion {

	@SpringBeanByType
	IUserService userService;

	/**
	 * 测试@AutoWired的正常加载方式
	 */
	@Test
	public void testAutoWired() {
		final boolean[] checked = new boolean[] { false };
		new MockUp<UserDaoImpl>() {
			@SuppressWarnings("unused")
			@Mock
			public void insertUser(User user) {
				checked[0] = true;
			}
		};

		userService.insertUser(new User("daui.wu"));
		want.bool(checked[0]).is(true);
	}
}

package org.jtester.module.spring.aop;

import java.util.Arrays;
import java.util.List;

import org.jtester.IAssertion;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserDao;
import org.jtester.helper.LogHelper;
import org.junit.Test;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/aop/proxybeans.xml",
		"org/jtester/module/spring/testedbeans/xml/data-source.xml" })
public class SpringBeanFromFactoryTest implements IAssertion {

	UserDao userDao = new UserDao() {
		public List<User> findUserByPostcode(String postcode) {
			return null;
		}

		public void insertUser(User user) {

		}

		public List<User> findAllUser() {
			LogHelper.info("find all user");
			return Arrays.asList(new User(), new User());
		}

		public int partialNotMock() {
			return 0;
		}

	};

	@Test
	public void test() {
		want.fail();
		// UserService userService = spring.getBean("userService");
		// List<User> users = userService.findAllUser();
		// want.collection(users).sizeEq(2);
	}
}

package org.jtester.module.spring;

import java.util.Arrays;
import java.util.List;

import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserDao;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;

@Test
@SpringApplicationContext({ "org/jtester/module/spring/proxybeans.xml", "org/jtester/fortest/spring/data-source.xml" })
public class SpringBeanForFactoryTest extends JTester {

	UserDao userDao = new UserDao() {
		public List<User> findUserByPostcode(String postcode) {
			return null;
		}

		public void insertUser(User user) {

		}

		public List<User> findAllUser() {
			System.out.println("find all user");
			return Arrays.asList(new User(), new User());
		}

		public int partialNotMock() {
			return 0;
		}

	};

	public void test() {
		UserService userService = spring.getBean("userService");
		List<User> users = userService.findAllUser();
		want.collection(users).sizeEq(2);
	}
}

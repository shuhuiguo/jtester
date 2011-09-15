package org.jtester.fit.fixture;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserService;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/beans.xml",
		"org/jtester/module/spring/testedbeans/xml/data-source.xml" })
public class TestedSpringFixture extends DtoPropertyFixture {
	@SpringBeanByName
	private UserService userService;

	public void insertUser(User user) {
		userService.insertUser(user);
	}
}

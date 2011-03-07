package org.jtester.fit.fixture;

import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserService;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SpringApplicationContext( { "org/jtester/fortest/spring/beans.xml", "org/jtester/fortest/spring/data-source.xml" })
public class TestedSpringFixture extends DtoPropertyFixture {
	@SpringBeanByName
	private UserService userService;

	public void insertUser(User user) {
		userService.insertUser(user);
	}
}

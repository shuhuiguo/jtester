package org.jtester.fit.fixture;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserAnotherDao;
import org.jtester.fortest.service.UserService;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/beans.xml",
		"org/jtester/module/spring/testedbeans/xml/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*", impl = "**.*Impl") })
public class TestedSpringFixture2 extends DtoPropertyFixture {
	@SpringBeanByName
	private UserService userService;

	@SpringBeanByName
	private UserAnotherDao anotherUserDao;

	public void insertUser(User user) {

		want.object(anotherUserDao).notNull();
		userService.insertUser(user);
	}
}

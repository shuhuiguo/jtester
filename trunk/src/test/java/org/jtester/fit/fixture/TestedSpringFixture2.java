package org.jtester.fit.fixture;

import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserAnotherDao;
import org.jtester.fortest.service.UserService;
import org.jtester.unitils.spring.AutoBeanInject;
import org.jtester.unitils.spring.AutoBeanInject.BeanMap;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SpringApplicationContext({ "org/jtester/fortest/spring/beans.xml", "org/jtester/fortest/spring/data-source.xml" })
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

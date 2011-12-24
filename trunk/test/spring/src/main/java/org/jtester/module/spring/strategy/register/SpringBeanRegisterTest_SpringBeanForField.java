package org.jtester.module.spring.strategy.register;

import java.util.List;

import org.jtester.IAssertion;
import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanFrom;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserDao;
import org.jtester.fortest.service.UserDaoImpl2;
import org.jtester.fortest.service.UserService;
import org.junit.Test;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
public class SpringBeanRegisterTest_SpringBeanForField implements IAssertion {
	@SpringBeanByName
	private UserService userService;

	@SpringBeanFrom
	UserDao userDao = new UserDaoImpl2();

	@Test
	public void getSpringBean() {
		List<User> users = userService.findAllUser();
		// want.collection(users).sizeEq(1).propertyEq("first", "ccc");
		want.collection(users).sizeEq(1).propertyEq("first", new String[] { "ccc" });
	}
}

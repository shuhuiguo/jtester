package org.jtester.module.spring.dynamicinject;

import java.util.List;

import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserDao;
import org.jtester.fortest.service.UserDaoImpl2;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.jtester.unitils.spring.AutoBeanInject;
import org.jtester.unitils.spring.SpringBeanFor;
import org.jtester.unitils.spring.AutoBeanInject.BeanMap;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SpringApplicationContext( { "org/jtester/fortest/spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
@Test(groups = "jtester")
public class DynamicBeanTest_SpringBeanForField extends JTester {
	@SpringBeanByName
	private UserService userService;

	@SpringBeanFor
	UserDao userDao = new UserDaoImpl2();

	public void getSpringBean() {
		List<User> users = userService.findAllUser();
		want.collection(users).sizeEq(1).propertyEq("first", "ccc");
	}
}

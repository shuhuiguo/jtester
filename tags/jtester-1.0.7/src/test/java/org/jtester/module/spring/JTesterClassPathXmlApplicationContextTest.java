package org.jtester.module.spring;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.fortest.service.UserAnotherDao;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SpringApplicationContext({ "org/jtester/fortest/spring/beans.xml", "org/jtester/fortest/spring/data-source.xml" })
@AutoBeanInject(maps = @BeanMap(intf = "**.UserAnotherDao", impl = "**.UserAnotherDaoImpl"))
@Test(groups = "jtester")
public class JTesterClassPathXmlApplicationContextTest extends JTester {
	@SpringBeanByName
	private UserService userService;

	@SpringBeanByName
	private UserAnotherDao userAnotherDao;

	public void getSpringBean() {
		want.object(userService).notNull();
		Object o = spring.getBean("userAnotherDao");
		want.object(o).same(userAnotherDao);
	}
}

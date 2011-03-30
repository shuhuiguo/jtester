package org.jtester.module.spring.dynamicinject;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.Tracer;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.fortest.service.UserAnotherDao;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;

@SpringApplicationContext( { "org/jtester/fortest/spring/data-source.xml" })
// @AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "") })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*222Service") })
// @org.testng.annotations.Test(groups = "test illegal @BeanMap")
@Tracer(spring = false)
public class DynamicBeanTest_InvalidIntfRegex extends JTester {
	@SpringBeanByName
	private UserService userService;

	@SpringBeanByName
	private UserAnotherDao userAnotherDao;

	public void getSpringBean() {
		want.object(userService).notNull();
		Object o = spring.getBean("userAnotherDao");
		want.object(o).same(userAnotherDao);

		Object userDao = spring.getBean("userDao");
		want.object(userDao).notNull();
	}
}

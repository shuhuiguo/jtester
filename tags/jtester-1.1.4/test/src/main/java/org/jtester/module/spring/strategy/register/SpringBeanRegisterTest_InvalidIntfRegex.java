package org.jtester.module.spring.strategy.register;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.Tracer;
import org.jtester.fortest.service.UserAnotherDao;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/data-source.xml" })
// @AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "") })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*222Service") })
// @org.testng.annotations.Test(groups = "test illegal @BeanMap")
@Tracer(spring = false)
public class SpringBeanRegisterTest_InvalidIntfRegex extends JTester {
	@SpringBeanByName
	private UserService userService;

	@SpringBeanByName
	private UserAnotherDao userAnotherDao;

	@Test(description = "查找userAnotherDao的实现类失败的case", groups = "for-test")
	public void getSpringBean() {
		want.object(userService).notNull();
		Object o = spring.getBean("userAnotherDao");
		want.object(o).same(userAnotherDao);

		Object userDao = spring.getBean("userDao");
		want.object(userDao).notNull();
	}
}

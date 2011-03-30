package org.jtester.module.spring.dynamicinject;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.fortest.service.UserAnotherDao;
import org.jtester.fortest.service.UserServiceNoIntf;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SpringApplicationContext( { "org/jtester/fortest/spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
@Test(groups = "jtester")
public class DynamicBeanTest_UseImpl extends JTester {
	@SpringBeanByName
	private UserServiceNoIntf userService;

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

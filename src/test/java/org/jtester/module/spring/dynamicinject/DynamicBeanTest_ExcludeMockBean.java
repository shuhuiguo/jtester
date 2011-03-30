package org.jtester.module.spring.dynamicinject;

import mockit.Mocked;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanFrom;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.deprecated.MockBean;
import org.jtester.fortest.service.UserAnotherDao;
import org.jtester.fortest.service.UserDao;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SpringApplicationContext({ "org/jtester/fortest/spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl") })
@Test(groups = "jtester")
@SuppressWarnings("deprecation")
public class DynamicBeanTest_ExcludeMockBean extends JTester {
	@SpringBeanByName
	private UserService userService;

	@SpringBeanFrom
	@Mocked
	private UserAnotherDao userAnotherDao;

	@MockBean
	private UserDao userDao;

	public void getSpringBean_MockedBean() {
		want.object(userService).notNull();
		Object o = spring.getBean("userAnotherDao");
		want.object(o).notNull();
		want.object(o).not(the.object().same(userAnotherDao));
	}

	@Test
	public void getSpringBean_MockBean() {
		Object o = spring.getBean("userDao");
		want.object(o).notNull();
		want.object(o).not(the.object().same(userDao));
	}
}

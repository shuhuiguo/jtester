package org.jtester.module.spring.dynamicinject;

import org.jtester.fortest.service.UserAnotherDao;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.jtester.unitils.spring.AutoBeanInject;
import org.jtester.unitils.spring.AutoBeanInject.BeanMap;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SpringApplicationContext( { "org/jtester/fortest/spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") }, excludePackages = { "org.jtester.**.UserDao" })
@Test(groups = "jtester")
public class DynamicBeanTest_ExcludePackage extends JTester {
	@SpringBeanByName
	private UserService userService;

	@SpringBeanByName
	private UserAnotherDao userAnotherDao;

	public void getSpringBean() {
		want.object(userService).notNull();
		Object o = spring.getBean("userAnotherDao");
		want.object(o).same(userAnotherDao);
	}

	@Test(expectedExceptions = NoSuchBeanDefinitionException.class)
	public void getSpringBean_NoSuchBean() {
		Object userDao = spring.getBean("userDao");
		want.object(userDao).notNull();
	}
}

package org.jtester.core.testng;

import java.util.List;

import org.jtester.annotations.SpringInitMethod;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.ResourceLoader;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.jtester.unitils.spring.AutoBeanInject;
import org.jtester.utility.JTesterLogger;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@Test(groups = "jtester")
@SpringApplicationContext({ "org/jtester/fortest/spring/beans.xml", "org/jtester/fortest/spring/data-source.xml",
		"org/jtester/fortest/spring/load-data-init.xml" })
@AutoBeanInject
public class JTesterTest extends JTester {
	@SpringBeanByName(claz = ResourceLoaderEx.class)
	ResourceLoader resourceLoader;

	@SpringBeanByName
	UserService userService;

	/**
	 * 测试程序中准备初始的数据供spring加载时使用
	 */
	public void testInitMethod() {
		List<String> users = resourceLoader.getUsers();
		want.collection(users).notNull().sizeEq(2);
	}

	@Test(dependsOnMethods = "testInitMethod")
	public void testInitMethod2() {
		List<User> users = this.userService.findAllUser();
		want.collection(users).sizeIs(2);
	}

	public static class ResourceLoaderEx extends ResourceLoader {
		@Override
		@SpringInitMethod
		public void init() throws Exception {
			JTesterLogger.info("readyDb");
			fit.runDbFit(JTesterTest.class, "JTesterTest.readyDb.wiki");
			super.init();
		}
	}
}

package org.jtester.core.testng;

import java.util.List;

import org.apache.log4j.Logger;
import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringInitMethod;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.ResourceLoader;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/beans.xml",
		"org/jtester/module/spring/testedbeans/xml/data-source.xml",
		"org/jtester/module/spring/testedbeans/xml/load-data-init.xml" })
@AutoBeanInject
public class JuniTesterTest extends JTester {
	private final static Logger log4j = Logger.getLogger(JuniTesterTest.class);

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
			log4j.info("readyDb");
			fit.runDbFit(JuniTesterTest.class, "JTesterTest.readyDb.wiki");
			super.init();
		}
	}
}

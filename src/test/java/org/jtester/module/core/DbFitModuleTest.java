package org.jtester.module.core;

import org.jtester.fortest.hibernate.User;
import org.jtester.fortest.hibernate.UserService;
import org.jtester.testng.JTester;
import org.jtester.unitils.dbfit.DbFit;
import org.jtester.unitils.dbfit.FitVar;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

@Test(groups = { "JTester", "hibernate" })
@SpringApplicationContext({ "classpath:/org/jtester/fortest/hibernate/project.xml" })
public class DbFitModuleTest extends JTester {
	@SpringBeanByType
	private UserService userService;

	@DbFit(when = "org/jtester/unitils/dbfit/DbFitModuleTest.getUser.wiki")
	public void getUser() {
		User user1 = userService.getUser(1);
		want.object(user1).notNull();
		User user2 = userService.getUser(2);
		want.object(user2).notNull();

		User user3 = userService.getUser(3);
		want.object(user3).isNull();
		User user4 = userService.getUser(4);
		want.object(user4).isNull();
	}

	@DbFit(when = "DbFitModuleTest.testCn.utf8.when.wiki", then = "DbFitModuleTest.testCn.utf8.then.wiki")
	public void testCn_utf8_utf8() {
	}

	@DbFit(when = "DbFitModuleTest.testCn.utf8.when.wiki", then = "DbFitModuleTest.testCn.gbk.then.wiki")
	public void testCn_utf8_gbk() {
	}

	@DbFit(when = "DbFitModuleTest.testCn.gbk.when.wiki", then = "DbFitModuleTest.testCn.utf8.then.wiki")
	public void testCn_gbk_utf8() {
	}

	@DbFit(when = "DbFitModuleTest.testCn.gbk.when.wiki", then = "DbFitModuleTest.testCn.gbk.then.wiki")
	public void testCn_gbk_gbk() {
	}

	@DbFit(when = "DbFitModuleTest.exactFitVar.when.wiki", 
			then = "DbFitModuleTest.exactFitVar.then.wiki", 
			vars = {@FitVar(key = "name", value = "darui.wu"), 
					@FitVar(key = "myid", value = "2") })
	public void exactFitVar() {
		
	}
}

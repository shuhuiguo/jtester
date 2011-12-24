package org.jtester.database;

import org.jtester.IAssertion;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.FitVar;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByType;
import org.junit.Test;

@SpringApplicationContext({ "classpath:/org/jtester/fortest/hibernate/project.xml" })
public class DbFitModuleTest implements IAssertion {
	@SpringBeanByType
	private UserService userService;

	@Test
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

	@Test
	@DbFit(when = "DbFitModuleTest.testCn.utf8.when.wiki", then = "DbFitModuleTest.testCn.utf8.then.wiki")
	public void testCn_utf8_utf8() {
	}

	@Test
	@DbFit(when = "DbFitModuleTest.testCn.utf8.when.wiki", then = "DbFitModuleTest.testCn.gbk.then.wiki")
	public void testCn_utf8_gbk() {
	}

	@Test
	@DbFit(when = "DbFitModuleTest.testCn.gbk.when.wiki", then = "DbFitModuleTest.testCn.utf8.then.wiki")
	public void testCn_gbk_utf8() {
	}

	@Test
	@DbFit(when = "DbFitModuleTest.testCn.gbk.when.wiki", then = "DbFitModuleTest.testCn.gbk.then.wiki")
	public void testCn_gbk_gbk() {
	}

	@Test
	@DbFit(when = "DbFitModuleTest.exactFitVar.when.wiki", then = "DbFitModuleTest.exactFitVar.then.wiki", vars = {
			@FitVar(key = "wikiName", value = "darui.wu"), @FitVar(key = "myid", value = "2") })
	public void exactFitVar() {

	}
}

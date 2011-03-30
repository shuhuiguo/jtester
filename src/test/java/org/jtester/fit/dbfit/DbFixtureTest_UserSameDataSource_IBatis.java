package org.jtester.fit.dbfit;

import org.jtester.annotations.Transactional;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserService;
import org.jtester.module.dbfit.DbFitTest;
import org.jtester.testng.JTester;
import org.jtester.unitils.dbfit.DbFit;
import org.testng.annotations.Test;
import org.jtester.annotations.Transactional.TransactionMode;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

@SpringApplicationContext( { "org/jtester/fortest/spring/beans.xml", "org/jtester/fortest/spring/data-source.xml" })
@Test(groups = { "jtester", "dbfit" })
public class DbFixtureTest_UserSameDataSource_IBatis extends JTester {
	@SpringBeanByType
	private UserService userService;

	@Test
	@Transactional(TransactionMode.COMMIT)
	@DbFit(when = "DbFixtureTest_UserSameDataSource_IBatis.getUser.when.wiki", then = "DbFixtureTest_UserSameDataSource_IBatis.getUser.then.wiki")
	public void getUser_VerifyThenWiki_WhenTransactionCommit() {
		User user = DbFitTest.newUser();
		userService.insertUser(user);
	}

	@Test
	@Transactional(TransactionMode.ROLLBACK)
	@DbFit(when = "DbFixtureTest_UserSameDataSource_IBatis.getUser.when.wiki", then = "DbFixtureTest_UserSameDataSource_IBatis.getUser.then.wiki")
	public void getUser_VerifyThenWiki_WhenTransactionRollBack() {
		User user = DbFitTest.newUser();
		userService.insertUser(user);
	}

	@Test
	@Transactional(TransactionMode.DISABLED)
	@DbFit(when = "DbFixtureTest_UserSameDataSource_IBatis.getUser.when.wiki", then = "DbFixtureTest_UserSameDataSource_IBatis.getUser.then.wiki")
	public void getUser_VerifyThenWiki_WhenTransactionDisabled() {
		User user = DbFitTest.newUser();
		userService.insertUser(user);
	}
}

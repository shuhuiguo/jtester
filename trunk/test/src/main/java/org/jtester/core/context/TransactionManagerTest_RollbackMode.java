package org.jtester.core.context;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jtester.annotation.Transactional;
import org.jtester.annotation.Transactional.TransactionMode;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SuppressWarnings("rawtypes")
@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/beans.xml",
		"org/jtester/module/spring/testedbeans/xml/data-source.xml" })
public class TransactionManagerTest_RollbackMode extends JTester {
	@SpringBeanByName
	private UserService userService;

	@DbFit(when = "data/TransactionManagerTest/init.when.wiki", then = "data/TransactionManagerTest/check.then.wiki")
	@Transactional(TransactionMode.ROLLBACK)
	public void testTransactionRollback() throws SQLException {
		List<User> users1 = this.userService.findAllUser();
		want.collection(users1).sizeEq(2);
		List<Map> maps1 = fit.queryList("select * from tdd_user", Map.class);
		want.collection(maps1).sizeEq(2);

		this.userService.insertUser(new User("test"));

		List<User> users2 = this.userService.findAllUser();
		want.collection(users2).sizeEq(3);
		List<Map> maps2 = fit.queryList("select * from tdd_user", Map.class);
		want.collection(maps2).sizeEq(3);
	}

	/**
	 * TestNG的dependsOnMethod是非常不靠谱的，为了验证事务<br>
	 * 所以这里只写2个方法，用@AfterClass替换dependsOnMethods
	 */
	@AfterClass
	// @Test(dependsOnMethods = "testTransactionRollback")
	public void checkTransactionRollback() {
		db.table("tdd_user").count().isEqualTo(1);
	}
}

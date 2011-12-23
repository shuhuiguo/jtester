package org.jtester.core.context;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.Transactional;
import org.jtester.annotations.Transactional.TransactionMode;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SuppressWarnings("rawtypes")
@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/beans.xml",
		"org/jtester/module/spring/testedbeans/xml/data-source.xml" })
public class TransactionManagerTest_DisableMode extends JTester {
	@SpringBeanByName
	private UserService userService;

	@DbFit(when = "data/TransactionManagerTest/init.when.wiki", then = "data/TransactionManagerTest/testTransactionDisable.then.wiki")
	@Transactional(TransactionMode.DISABLED)
	public void testTransactionDisable() throws SQLException {
		List<User> users1 = this.userService.findAllUser();
		want.collection(users1).sizeEq(2);
		List<Map> maps1 = fit.queryList("select * from tdd_user", Map.class);
		want.collection(maps1).sizeEq(2);

		try {
			this.userService.insertUserWillException(new User("test"));
			want.fail();
		} catch (Throwable e) {
			// 不做任何事，只是验证Disabled状态下的事务回滚
			want.string(e.getMessage()).contains("insert user exception!");
		}

		List<User> users2 = this.userService.findAllUser();
		want.collection(users2).sizeEq(2);

		db.table("tdd_user").count().isEqualTo(2);
	}

	/**
	 * TestNG的dependsOnMethod是非常不靠谱的，为了验证事务<br>
	 * 所以这里只写2个方法，用@AfterClass替换dependsOnMethods
	 */
	@AfterClass
	// @Test(dependsOnMethods = "testTransactionDisable")
	public void checkDisabled() {
		db.table("tdd_user").count().isEqualTo(3);
	}
}

package org.jtester.core.context;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.DbFit.AUTO;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.Transactional;
import org.jtester.annotations.Transactional.TransactionMode;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SuppressWarnings("rawtypes")
@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/beans.xml",
		"org/jtester/module/spring/testedbeans/xml/data-source.xml" })
public class TransactionManagerTest extends JTester {
	@SpringBeanByName
	private UserService userService;

	@DbFit(when = "data/TransactionManagerTest/init.when.wiki", then = "data/TransactionManagerTest/check.then.wiki")
	@Transactional(TransactionMode.COMMIT)
	public void testTransactionCommit() throws SQLException {
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

	@Test(dependsOnMethods = "testTransactionCommit")
	@DbFit(auto = AUTO.AUTO)
	public void checkTransactionCommit() {

	}

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

	@Test(dependsOnMethods = "testTransactionRollback")
	@DbFit(auto = AUTO.AUTO)
	public void checkTransactionRollback() {

	}

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
		List<Map> maps2 = fit.queryList("select * from tdd_user", Map.class);
		want.collection(maps2).sizeEq(2);
	}
}

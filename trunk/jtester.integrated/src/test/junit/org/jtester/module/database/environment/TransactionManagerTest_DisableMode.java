package org.jtester.module.database.environment;

import java.sql.SQLException;
import java.util.List;

import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserService;
import org.jtester.junit.JTester;
import org.jtester.module.database.IDatabase;
import org.jtester.module.database.annotations.Transactional;
import org.jtester.module.database.annotations.Transactional.TransactionMode;
import org.jtester.module.spring.annotations.SpringContext;
import org.jtester.module.spring.annotations.SpringBeanByName;
import org.junit.AfterClass;
import org.junit.Test;

@SuppressWarnings("serial")
@SpringContext({ "org/jtester/module/spring/testedbeans/xml/beans.xml",
		"org/jtester/module/spring/testedbeans/xml/data-source.xml" })
public class TransactionManagerTest_DisableMode extends JTester implements IDatabase {
	@SpringBeanByName
	private UserService userService;

	@Test
	@Transactional(TransactionMode.DISABLED)
	public void testTransactionDisable() throws SQLException {
		db.table("tdd_user").clean().insert(2, new DataMap() {
			{
				this.put("id", 1, 2);
				this.put("first_name", "darui.wu", "darui.wu2");
			}
		});

		List<User> users1 = this.userService.findAllUser();
		want.collection(users1).sizeEq(2);
		db.table("tdd_user").count().isEqualTo(2);

		try {
			this.userService.insertUserWillException(new User("test"));
			want.fail();
		} catch (Throwable e) {
			// 不做任何事，只是验证Disabled状态下的事务回滚
			want.string(e.getMessage()).contains("insert user exception!");
		}
		List<User> users2 = this.userService.findAllUser();
		want.collection(users2).sizeEq(2);

		db.table("tdd_user").clean().insert(2, new DataMap() {
			{
				this.put("id", 1, 2);
				this.put("first_name", "darui.wu", "darui.wu2");
			}
		});
	}

	@AfterClass
	public static void checkDisabled() {
		db.table("tdd_user").count().isEqualTo(2);
	}
}

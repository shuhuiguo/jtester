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

@SuppressWarnings({ "serial" })
@SpringContext({ "org/jtester/module/spring/testedbeans/xml/beans.xml",
		"org/jtester/module/spring/testedbeans/xml/data-source.xml" })
public class TransactionManagerTest_CommitMode extends JTester implements IDatabase {
	@SpringBeanByName
	private UserService userService;

	@Transactional(TransactionMode.COMMIT)
	@Test
	public void testTransactionCommit() throws SQLException {
		db.table("tdd_user").clean().insert(2, new DataMap() {
			{
				this.put("id", 1, 2);
				this.put("first_name", "darui.wu", "darui.wu2");
			}
		});

		List<User> users1 = this.userService.findAllUser();
		want.collection(users1).sizeEq(2);
		this.userService.insertUser(new User("test"));

		List<User> users2 = this.userService.findAllUser();
		want.collection(users2).sizeEq(3);

		db.table("tdd_user").count().isEqualTo(3);
	}

	@AfterClass
	public static void checkTransactionCommit() {
		db.table("tdd_user").count().isEqualTo(3);
	}
}

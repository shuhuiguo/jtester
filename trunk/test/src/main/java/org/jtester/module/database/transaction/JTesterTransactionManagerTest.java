package org.jtester.module.database.transaction;

import org.jtester.annotation.Transactional;
import org.jtester.annotation.Transactional.TransactionMode;
import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;
import org.jtester.annotations.AutoBeanInject.BeanMap;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
@Test(groups = "jtester")
public class JTesterTransactionManagerTest extends JTester {
	@SpringBeanByName
	private UserService userService;

	@DbFit(when = "org/jtester/unitils/database/clean user.wiki", then = "org/jtester/unitils/database/verify user.wiki")
	public void testNormalTransactonal() {
		userService.insertUser(new User("first", "last"));
	}

	@DbFit(when = "org/jtester/unitils/database/clean user.wiki", then = "org/jtester/unitils/database/verify user.wiki")
	@Test
	public void testCommitTransactonal_whenHasBeenRollback() {
		try {
			userService.insertUserException(new User("first", "last"));
		} catch (Throwable e) {
		}
		userService.insertUser(new User("first", "last"));
	}

	@DbFit(when = "org/jtester/unitils/database/clean user.wiki", then = "org/jtester/unitils/database/verify user.wiki")
	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void testRollbackTransactonal_whenHasBeenRollback() {
		try {
			userService.insertUserException(new User("first", "last"));
		} catch (Throwable e) {
		}
		userService.insertUser(new User("first", "last"));
	}
}

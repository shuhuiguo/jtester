package org.jtester.module.database.transaction;

import org.jtester.annotations.Transactional;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.jtester.unitils.dbfit.DbFit;
import org.jtester.unitils.spring.AutoBeanInject;
import org.jtester.unitils.spring.AutoBeanInject.BeanMap;
import org.testng.annotations.Test;
import org.jtester.annotations.Transactional.TransactionMode;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SpringApplicationContext({ "org/jtester/fortest/spring/data-source.xml" })
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
	// @Transactional(TransactionMode.DISABLED)
	public void testCommitTransactonal_whenHasBeenRollback() {
		try {
			userService.insertUserException(new User("first", "last"));
		} catch (Exception e) {
		}
		userService.insertUser(new User("first", "last"));
	}

	@DbFit(when = "org/jtester/unitils/database/clean user.wiki", then = "org/jtester/unitils/database/verify user.wiki")
	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void testRollbackTransactonal_whenHasBeenRollback() {
		try {
			userService.insertUserException(new User("first", "last"));
		} catch (Exception e) {
		}
		userService.insertUser(new User("first", "last"));
	}
}

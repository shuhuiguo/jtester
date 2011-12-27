package org.jtester.module.database.transaction;

import org.jtester.IAssertion;
import org.jtester.IDatabase;
import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.Transactional;
import org.jtester.annotations.Transactional.TransactionMode;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserService;
import org.junit.Test;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
public class JTesterTransactionManagerTest implements IAssertion, IDatabase {
	@SpringBeanByName
	private UserService userService;

	@Test
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

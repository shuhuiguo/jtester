package org.jtester.module.tracer;

import org.jtester.annotations.Tracer;
import org.jtester.annotations.Tracer.Info;
import org.jtester.fortest.hibernate.User;
import org.jtester.fortest.hibernate.UserService;
import org.jtester.testng.JTester;
import org.jtester.unitils.dbfit.DbFit;
import org.jtester.utility.ResourceUtil;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

@SpringApplicationContext({ "classpath:/org/jtester/fortest/hibernate/project.xml" })
@Test(groups = { "jtester", "tracer", "broken-install", "hibernate" })
public class TracerTest extends JTester {
	@SpringBeanByType
	private UserService userService;

	@Test
	@DbFit(when = "org/jtester/fit/dbfit/DbFixtureTest_UserSameDataSource.getUser.when.wiki", then = "org/jtester/fit/dbfit/DbFixtureTest_UserSameDataSource.getUser.then.wiki")
	@Tracer(spring = false)
	public void monitorSpringFalse() {
		User user1 = userService.getUser(1);
		want.object(user1).notNull();

		User user = new User();
		user.setName("new user");
		userService.newUser(user);
	}

	@Test(dependsOnMethods = "monitorSpringFalse")
	public void monitorSpringFalse_check() {
		String tracerInfo = ResourceUtil
				.readStringFromFile("target/tracer/org/jtester/unitils/tracer/TracerTest#monitorSpringFalse.html");
		want.string(tracerInfo).notContain("paras").notContain("result");
	}

	@Test
	@DbFit(when = "org/jtester/fit/dbfit/DbFixtureTest_UserSameDataSource.getUser.when.wiki", then = "org/jtester/fit/dbfit/DbFixtureTest_UserSameDataSource.getUser.then.wiki")
	@Tracer(spring = true)
	public void monitorSpringTrue() {
		User user1 = userService.getUser(1);
		want.object(user1).notNull();

		User user = new User();
		user.setName("new user");
		userService.newUser(user);
	}

	@Test(dependsOnMethods = "monitorSpringTrue")
	public void monitorSpringTrue_check() {
		String tracerInfo = ResourceUtil
				.readStringFromFile("target/tracer/org/jtester/unitils/tracer/TracerTest#monitorSpringTrue.html");
		want.string(tracerInfo).contains("call").contains("paras").contains("result");
	}

	@Test
	@DbFit(when = "org/jtester/fit/dbfit/DbFixtureTest_UserSameDataSource.getUser.when.wiki", then = "org/jtester/fit/dbfit/DbFixtureTest_UserSameDataSource.getUser.then.wiki")
	@Tracer(jdbc = false)
	public void monitorJdbcFalse() {
		User user1 = userService.getUser(1);
		want.object(user1).notNull();

		User user = new User();
		user.setName("new user");
		userService.newUser(user);
	}

	@Test(dependsOnMethods = "monitorSpringFalse")
	public void monitorJdbcFalse_check() {
		String tracerInfo = ResourceUtil
				.readStringFromFile("target/tracer/org/jtester/unitils/tracer/TracerTest#monitorJdbcFalse.html");
		want.string(tracerInfo).notContain("SQL-Statement");
	}

	@Test
	@DbFit(when = "org/jtester/fit/dbfit/DbFixtureTest_UserSameDataSource.getUser.when.wiki", then = "org/jtester/fit/dbfit/DbFixtureTest_UserSameDataSource.getUser.then.wiki")
	@Tracer(jdbc = true)
	public void monitorJdbcTrue() {
		User user1 = userService.getUser(1);
		want.object(user1).notNull();

		User user = new User();
		user.setName("new user");
		userService.newUser(user);
	}

	@Test(dependsOnMethods = "monitorSpringTrue")
	public void monitorJdbcTrue_check() {
		String tracerInfo = ResourceUtil
				.readStringFromFile("target/tracer/org/jtester/unitils/tracer/TracerTest#monitorJdbcTrue.html");
		want.string(tracerInfo).contains("SQL-Statement");
	}

	@Test
	@DbFit(when = "org/jtester/fit/dbfit/DbFixtureTest_UserSameDataSource.getUser.when.wiki", then = "org/jtester/fit/dbfit/DbFixtureTest_UserSameDataSource.getUser.then.wiki")
	@Tracer(info = Info.TOSTRING)
	public void monitorSpring_ToInfoString() {
		User user1 = userService.getUser(1);
		want.object(user1).notNull();

		User user = new User();
		user.setName("new user");
		userService.newUser(user);
	}

	@Test(dependsOnMethods = "monitorSpring_ToInfoString")
	public void monitorSpring_ToInfoString_check() {
		String tracerInfo = ResourceUtil
				.readStringFromFile("target/tracer/org/jtester/unitils/tracer/TracerTest#monitorSpring_ToInfoString.html");
		want.string(tracerInfo).contains("org.jtester.fortest.hibernate.User@");
	}
}

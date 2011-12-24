package org.jtester.module.tracer;

import java.io.FileNotFoundException;

import org.jtester.IAssertion;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByType;
import org.jtester.annotations.Tracer;
import org.jtester.annotations.Tracer.Info;
import org.jtester.fortest.hibernate.User;
import org.jtester.fortest.hibernate.UserService;
import org.jtester.helper.ResourceHelper;
import org.junit.Test;

@SpringApplicationContext({ "classpath:/org/jtester/fortest/hibernate/project.xml" })
public class TracerTest implements IAssertion {
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

	@Test
	// (dependsOnMethods = "monitorSpringFalse")
	public void monitorSpringFalse_check() throws FileNotFoundException {
		String tracerInfo = ResourceHelper
				.readFromFile("target/tracer/org/jtester/unitils/tracer/TracerTest#monitorSpringFalse.html");
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

	@Test
	// (dependsOnMethods = "monitorSpringTrue")
	public void monitorSpringTrue_check() throws FileNotFoundException {
		String tracerInfo = ResourceHelper
				.readFromFile("target/tracer/org/jtester/unitils/tracer/TracerTest#monitorSpringTrue.html");
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

	@Test
	// (dependsOnMethods = "monitorSpringFalse")
	public void monitorJdbcFalse_check() throws FileNotFoundException {
		String tracerInfo = ResourceHelper
				.readFromFile("target/tracer/org/jtester/unitils/tracer/TracerTest#monitorJdbcFalse.html");
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

	@Test
	// (dependsOnMethods = "monitorSpringTrue")
	public void monitorJdbcTrue_check() throws FileNotFoundException {
		String tracerInfo = ResourceHelper
				.readFromFile("target/tracer/org/jtester/unitils/tracer/TracerTest#monitorJdbcTrue.html");
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

	@Test
	// (dependsOnMethods = "monitorSpring_ToInfoString")
	public void monitorSpring_ToInfoString_check() throws FileNotFoundException {
		String tracerInfo = ResourceHelper
				.readFromFile("target/tracer/org/jtester/unitils/tracer/TracerTest#monitorSpring_ToInfoString.html");
		want.string(tracerInfo).contains("org.jtester.fortest.hibernate.User@");
	}
}

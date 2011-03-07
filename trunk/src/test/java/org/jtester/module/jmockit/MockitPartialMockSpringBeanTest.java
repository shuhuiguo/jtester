package org.jtester.module.jmockit;

import mockit.Mock;
import mockit.Mockit;

import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserService;
import org.jtester.fortest.service.UserServiceImpl;
import org.jtester.testng.JTester;
import org.jtester.unitils.dbfit.DbFit;
import org.jtester.utility.JTesterLogger;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@Test(groups = "jtester")
@SpringApplicationContext({ "org/jtester/fortest/spring/beans.xml", "org/jtester/fortest/spring/data-source.xml" })
public class MockitPartialMockSpringBeanTest extends JTester {
	@SpringBeanByName
	private UserService userService;

	@DbFit(when = "org/jtester/module/dbfit/DbFit.paySalary_insert.when.wiki")
	public void paySalary() {
		Mockit.setUpMock(UserServiceImpl.class, MockUserServiceImpl.class);
		// Mockit.setUpMock(MockUserServiceImpl.class);

		double total = this.userService.paySalary("310000");
		want.number(total).isEqualTo(4000d);

		want.string(wantMock).isEqualTo("unInvoked");
		this.userService.insertUser(new User(1001L));
		want.string(wantMock).isEqualTo("hasInvoked");
	}

	private static String wantMock = "unInvoked";

	// @MockClass(realClass = UserServiceImpl.class)
	public static class MockUserServiceImpl {
		@Mock
		public void insertUser(User user) {
			JTesterLogger.info("user id:" + user.getId());
			want.number(user.getId()).isEqualTo(1001L);
			wantMock = "hasInvoked";
		}
	}
}

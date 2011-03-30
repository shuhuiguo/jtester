package org.jtester.module.jmockit;

import mockit.Mock;
import mockit.Mockit;

import org.apache.log4j.Logger;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserService;
import org.jtester.fortest.service.UserServiceImpl;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SpringApplicationContext({ "org/jtester/fortest/spring/beans.xml", "org/jtester/fortest/spring/data-source.xml" })
public class MockitPartialMockSpringBeanTest extends JTester {
	private final static Logger log4j = Logger.getLogger(MockitPartialMockSpringBeanTest.class);

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
			log4j.info("user id:" + user.getId());
			want.number(user.getId()).isEqualTo(1001L);
			wantMock = "hasInvoked";
		}
	}
}

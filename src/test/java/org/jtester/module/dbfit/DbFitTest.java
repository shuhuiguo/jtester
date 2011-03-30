package org.jtester.module.dbfit;

import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.jtester.unitils.dbfit.DbFit;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@Test(groups = { "JTester" })
@SpringApplicationContext( { "classpath:org/jtester/fortest/spring/beans.xml",
		"classpath:org/jtester/fortest/spring/data-source.xml" })
public class DbFitTest extends JTester {
	@SpringBeanByName
	private UserService userService;

	@DbFit(when = { "DbFit.paySalary_insert.when.wiki" }, then = "DbFit.paySalary_insert.then.wiki")
	public void paySalary_insert() {
		User user = newUser();
		this.userService.insertUser(user);
	}

	public static User newUser() {
		User user = new User();
		user.setFirst("first name");
		user.setPostcode("320001");
		user.setSarary(23.02d);
		return user;
	}
}

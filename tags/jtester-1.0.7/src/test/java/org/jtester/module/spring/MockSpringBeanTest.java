package org.jtester.module.spring;

import java.util.ArrayList;

import mockit.Mocked;

import org.jtester.annotations.Inject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.Transactional;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserDao;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;
import org.jtester.annotations.Transactional.TransactionMode;

@Test(groups = "jtester")
@SpringApplicationContext( { "org/jtester/fortest/spring/beans.xml", "org/jtester/fortest/spring/data-source.xml" })
public class MockSpringBeanTest extends JTester {
	@SpringBeanByName
	private UserService userService;

	@Mocked
	@Inject(targets = "userService")
	private UserDao userDao;

	public void paySalary() {
		new Expectations() {
			{
				userDao.findUserByPostcode("310000");
				returns(new ArrayList<User>() {
					private static final long serialVersionUID = -2799578129563837839L;
					{
						this.add(new User(1, 1000d));
						this.add(new User(2, 1500d));
						this.add(new User(2, 1800d));
					}
				});
			}
		};

		double total = this.userService.paySalary("310000");
		want.number(total).isEqualTo(4300d);
	}

	@Test(expectedExceptions = RuntimeException.class)
	@Transactional(TransactionMode.DISABLED)
	public void paySalary_exception() {
		new Expectations() {
			{
				userDao.findUserByPostcode("310000");
				throwException(new RuntimeException());
			}
		};

		this.userService.paySalary("310000");
	}
}

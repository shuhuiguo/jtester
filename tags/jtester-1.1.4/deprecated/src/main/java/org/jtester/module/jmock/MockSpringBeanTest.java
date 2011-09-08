package org.jtester.module.jmock;

import java.util.ArrayList;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.Transactional;
import org.jtester.core.Je;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserDao;
import org.jtester.fortest.service.UserService;
import org.jtester.module.core.helper.JmockModuleHelper;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;
import org.jtester.annotations.Transactional.TransactionMode;
import org.jtester.annotations.Mock;

@SuppressWarnings("deprecation")
@Test(groups = "jtester")
@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/beans.xml",
		"org/jtester/module/spring/testedbeans/xml/data-source.xml" })
public class MockSpringBeanTest extends JTester {
	@SpringBeanByName
	private UserService userService;

	@Mock(injectInto = "userService")
	private UserDao userDao;

	public void paySalary() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.one(userDao).findUserByPostcode("310000");
				will.returns.value(new ArrayList<User>() {
					private static final long serialVersionUID = -2799578129563837839L;
					{
						this.add(new User(1, 1000d));
						this.add(new User(2, 1500d));
						this.add(new User(2, 1800d));
					}
				});
			}
		});

		double total = this.userService.paySalary("310000");
		want.number(total).isEqualTo(4300d);
	}

	@Test(expectedExceptions = RuntimeException.class)
	@Transactional(TransactionMode.DISABLED)
	public void paySalary_exception() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.one(userDao).findUserByPostcode("310000");
				will.throwException(new RuntimeException());
			}
		});

		this.userService.paySalary("310000");
	}
}

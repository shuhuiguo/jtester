package org.jtester.module.jmockit;

import java.util.ArrayList;

import mockit.Mocked;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanFrom;
import org.jtester.annotations.Transactional;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserDao;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;
import org.jtester.annotations.Transactional.TransactionMode;

@Test(groups = { "jtester", "mock-demo" })
@SpringApplicationContext({ "org/jtester/fortest/spring/beans.xml", "org/jtester/fortest/spring/data-source.xml" })
public class MockBeanTest extends JTester {
	@SpringBeanByName
	private UserService userService;

	@SpringBeanFrom
	@Mocked
	private UserDao userDao;

	@Transactional(TransactionMode.DISABLED)
	public void paySalary_ThrowRuntimeException_WithSpringWrapped() {
		new Expectations() {
			{
				when(userDao.findUserByPostcode("310000")).thenThrows(new RuntimeException("test"));
			}
		};
		try {
			this.userService.paySalary("310000");
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getMessage();
			want.string(message).contains("test");
		}
	}

	public void paySalary() {
		new Expectations() {
			{
				when(userDao.findUserByPostcode("310000")).thenReturn((new ArrayList<User>() {
					private static final long serialVersionUID = -2799578129563837839L;
					{
						this.add(new User(1, 1000d));
						this.add(new User(2, 1500d));
						this.add(new User(2, 1800d));
					}
				}));
			}
		};
		double total = this.userService.paySalary("310000");
		want.number(total).isEqualTo(4300d);
	}

	public void paySalary2() {
		new Expectations() {
			{
				when(userDao.findUserByPostcode("310000")).thenReturn(new ArrayList<User>() {
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
}

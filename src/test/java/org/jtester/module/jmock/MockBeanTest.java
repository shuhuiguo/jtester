package org.jtester.module.jmock;

import java.util.ArrayList;

import org.jtester.annotations.Transactional;
import org.jtester.core.Je;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserDao;
import org.jtester.fortest.service.UserService;
import org.jtester.module.utils.JmockModuleHelper;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;
import org.jtester.annotations.Transactional.TransactionMode;
import org.jtester.annotations.deprecated.MockBean;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SuppressWarnings("deprecation")
@Test(groups = { "jtester", "mock-demo" })
@SpringApplicationContext( { "org/jtester/fortest/spring/beans.xml", "org/jtester/fortest/spring/data-source.xml" })
public class MockBeanTest extends JTester {
	@SpringBeanByName
	private UserService userService;

	@MockBean
	private UserDao userDao;

	@Transactional(TransactionMode.DISABLED)
	public void paySalary_ThrowRuntimeException_WithSpringWrapped() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.one(userDao).findUserByPostcode("310000");
				will.throwException(new RuntimeException("test"));
			}
		});
		try {
			this.userService.paySalary("310000");
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getMessage();
			want.string(message).contains("test");
		}
	}

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

	public void paySalary2() {
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
}

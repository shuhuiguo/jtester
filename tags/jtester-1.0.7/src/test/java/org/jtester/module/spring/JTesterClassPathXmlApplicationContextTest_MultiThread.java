package org.jtester.module.spring;

import java.util.List;

import mockit.Delegate;
import mockit.NonStrict;

import org.jtester.annotations.Tracer;
import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserDao;
import org.jtester.fortest.service.UserDaoImpl;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.jtester.unitils.spring.AutoBeanInject;
import org.jtester.unitils.spring.SpringBeanFor;
import org.jtester.unitils.spring.AutoBeanInject.BeanMap;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SpringApplicationContext({ "org/jtester/fortest/spring/data-source.xml" })
@AutoBeanInject(maps = @BeanMap(intf = "**.UserAnotherDao", impl = "**.UserAnotherDaoImpl"))
@Test(groups = "jtester")
@SuppressWarnings("unused")
public class JTesterClassPathXmlApplicationContextTest_MultiThread extends JTester {
	@SpringBeanFor
	@NonStrict
	private UserService userService;

	@SpringBeanByName(claz = UserDaoImpl.class)
	private UserDao userDao;

	int count = 0;

	/**
	 * 测试在多线程中获取mocked bean
	 * 
	 * @throws InterruptedException
	 */
	@Test
	@Tracer(spring = false)
	public void testGetBean() throws InterruptedException {

		// Object bean = spring.getBean("userService");
		count = 0;
		new Expectations() {
			{
				userService.findAllUser();
				result = new Delegate() {
					public List<User> findAllUser() {
						count++;
						return null;
					}
				};
			}
		};
		userService.findAllUser();
		for (int loop = 0; loop < 10; loop++) {
			Runnable runnable = new Runnable() {

				public void run() {
					UserService userService = spring.getBean("userService");
					userService.findAllUser();
				}
			};

			new Thread(runnable).start();
		}
		Thread.sleep(100);
		want.number(count).isEqualTo(11);
	}
}

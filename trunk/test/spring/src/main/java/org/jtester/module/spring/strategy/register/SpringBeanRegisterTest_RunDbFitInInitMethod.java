package org.jtester.module.spring.strategy.register;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringInitMethod;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.fortest.service.UserService;
import org.jtester.fortest.service.UserServiceImpl;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
@Test(groups = "jtester")
public class SpringBeanRegisterTest_RunDbFitInInitMethod implements IAssertion {
	@SpringBeanByName(claz = UserServiceImplEx.class)
	UserService userService;

	/**
	 * 测试在init方法中执行dbfit文件
	 */
	public void paySalary() {
		double total = this.userService.paySalary("310010");
		want.number(total).isEqualTo(3100d);
	}

	public static class UserServiceImplEx extends UserServiceImpl {
		@SpringInitMethod
		public void initDbFit() {
			fit.runDbFit(SpringBeanRegisterTest_RunDbFitInInitMethod.class,
					"data/SpringBeanRegisterTest_RunDbFitInInitMethod/paySalary.init.wiki");
		}
	}
}

package org.jtester.module.spring.dynamicinject;

import org.jtester.annotations.SpringInitMethod;
import org.jtester.fortest.service.UserService;
import org.jtester.fortest.service.UserServiceImpl;
import org.jtester.testng.JTester;
import org.jtester.unitils.spring.AutoBeanInject;
import org.jtester.unitils.spring.AutoBeanInject.BeanMap;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SpringApplicationContext({ "org/jtester/fortest/spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
@Test(groups = "jtester")
public class DynamicBeanTest_RunDbFitInInitMethod extends JTester {
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
			fit.runDbFit(DynamicBeanTest_RunDbFitInInitMethod.class,
					"data/DynamicBeanTest_RunDbFitInInitMethod/paySalary.init.wiki");
		}
	}
}

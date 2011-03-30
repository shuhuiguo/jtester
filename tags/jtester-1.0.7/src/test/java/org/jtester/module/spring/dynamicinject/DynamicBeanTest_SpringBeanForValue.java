package org.jtester.module.spring.dynamicinject;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.fortest.service.BeanClazzUserServiceImpl;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SpringApplicationContext({ "org/jtester/fortest/spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
@Test(groups = "jtester")
public class DynamicBeanTest_SpringBeanForValue extends JTester {

	@SpringBeanByName(claz = BeanClazzUserServiceImpl.class)
	private UserService userService1;

	@SpringBeanByName("userService")
	private UserService userService2;

	public void getSpringBean() {
		String serviceName1 = userService1.getServiceName();
		want.string(serviceName1).isEqualTo("BeanClazzUserServiceImpl");

		String serviceName2 = userService2.getServiceName();
		want.string(serviceName2).isEqualTo("UserServiceImpl");
	}
}

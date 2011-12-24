package org.jtester.module.spring.strategy.register;

import org.jtester.IAssertion;
import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fortest.service.BeanClazzUserServiceImpl;
import org.jtester.fortest.service.UserService;
import org.junit.Test;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
public class SpringBeanRegisterTest_SpringBeanForValue implements IAssertion {

	@SpringBeanByName(claz = BeanClazzUserServiceImpl.class)
	private UserService userService1;

	@SpringBeanByName("userService")
	private UserService userService2;

	@Test
	public void getSpringBean() {
		String serviceName1 = userService1.getServiceName();
		want.string(serviceName1).isEqualTo("BeanClazzUserServiceImpl");

		String serviceName2 = userService2.getServiceName();
		want.string(serviceName2).isEqualTo("UserServiceImpl");
	}
}

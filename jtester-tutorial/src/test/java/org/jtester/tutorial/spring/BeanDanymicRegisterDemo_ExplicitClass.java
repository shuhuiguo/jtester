package org.jtester.tutorial.spring;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.jtester.tutorial.biz.service.CustomerDao;
import org.jtester.tutorial.biz.service.CustomerDaoImpl;
import org.jtester.tutorial.biz.service.CustomerService;
import org.jtester.tutorial.biz.service.OrderDao;
import org.jtester.tutorial.biz.service.irregular.CustomerServiceIrregularImpl;
import org.jtester.tutorial.biz.service.irregular.OrderDaoIrregularImpl;
import org.testng.annotations.Test;

@Test(description = "动态注册spring bean演示,显式指定使用哪个实现类")
@SpringApplicationContext({ "spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*", impl = "**.*Impl"),// <br>
		@BeanMap(intf = "**.*", impl = "**.impl.*Impl") })
public class BeanDanymicRegisterDemo_ExplicitClass extends JTester {

	@SpringBeanByName(claz = CustomerServiceIrregularImpl.class)
	CustomerService customerService;

	@SpringBeanByName(claz = OrderDaoIrregularImpl.class)
	OrderDao orderDao;

	@Test(description = "演示动态注册spring bean功能")
	public void testDanymicRegister() {
		want.object(customerService).notNull();
		String result = this.customerService.doNothing();
		want.string(result).start("this is an irregular service");
	}

	@Test(description = "演示显式指定spring bean依赖项实现的情况")
	public void testBeanDependency() {
		OrderDao orderDaoFromBean = reflector.getField(customerService, "orderDao");
		want.object(orderDaoFromBean).notNull().same(orderDao);

		CustomerDao customerDao = reflector.getField(customerService, "customerDao");
		want.object(customerDao).notNull();
		Object target = reflector.getSpringAdvisedTarget(customerDao);
		want.object(target).clazAssignFrom(CustomerDaoImpl.class);
	}
}

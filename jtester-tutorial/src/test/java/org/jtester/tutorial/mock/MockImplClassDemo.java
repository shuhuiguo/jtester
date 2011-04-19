package org.jtester.tutorial.mock;

import mockit.Mock;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.jtester.tutorial.biz.model.Customer;
import org.jtester.tutorial.biz.service.CustomerDaoImpl;
import org.jtester.tutorial.biz.service.CustomerService;
import org.jtester.tutorial.biz.service.irregular.CustomerServiceIrregularImpl;
import org.testng.annotations.Test;

@SpringApplicationContext({ "spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*", impl = "**.*Impl"),// <br>
		@BeanMap(intf = "**.*", impl = "**.impl.*Impl") })
@SuppressWarnings("unused")
public class MockImplClassDemo extends JTester {
	@SpringBeanByName(claz = CustomerServiceIrregularImpl.class)
	CustomerService customerService;

	@Test(description = "演示如何静态的mock一个实现类的方法")
	public void testMockUserDaoImpl() {
		final String name = "darui.wu";
		new MockUp<CustomerDaoImpl>() {
			@Mock
			public Customer findCustomerByName(String name) {
				return new Customer(name, null);
			}
		};

		Customer cust = this.customerService.findCustomerByName(name);
		want.object(cust).notNull().propertyEq("name", name);
	}
}

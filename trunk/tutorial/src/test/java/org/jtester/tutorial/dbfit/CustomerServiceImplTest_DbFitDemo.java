package org.jtester.tutorial.dbfit;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.testng.JTester;
import org.jtester.tutorial.biz.model.Customer;
import org.jtester.tutorial.biz.service.CustomerService;
import org.testng.annotations.Test;

@Test
@SpringApplicationContext({ "spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*", impl = "**.*Impl"),// <br>
		@BeanMap(intf = "**.*", impl = "**.impl.*Impl") })
public class CustomerServiceImplTest_DbFitDemo extends JTester {
	@SpringBeanByName
	CustomerService customerService;

	@DbFit(when = "clean-customer.wiki", then = "new-customer.then.wiki")
	public void testNewCustomer() {
		Customer customer = new Customer("darui.wu", "");
		this.customerService.newCustomer(customer);
	}

	@DbFit(when = "prepare-customer.wiki")
	public void testFindCustomerByName() {
		Customer customer = this.customerService.findCustomerByName("darui.wu");
		want.object(customer).propertyEq("address", "杭州");
	}

	@Test(description = "不存在用户的情况")
	@DbFit(when = "prepare-customer.wiki")
	public void testFindCustomerByName_UnExist() {
		this.customerService.findCustomerByName("job.he");
	}

	@Test(description = "用户同名的情况")
	@DbFit(when = "multi-same-name.wiki")
	public void testFindCustomerByName_UnUnique() {
		this.customerService.findCustomerByName("job.he");
	}
}

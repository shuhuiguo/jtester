package org.jtester.tutorial01.integrated;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fit.fixture.DtoPropertyFixture;
import org.jtester.tutorial.biz.model.Customer;
import org.jtester.tutorial.biz.service.CustomerService;

@SpringApplicationContext({ "spring/beans.xml", "spring/data-source.xml" })
public class SpringUsageFixture extends DtoPropertyFixture {
	@SpringBeanByName
	private CustomerService customerService;

	public void insertUser(Customer customer) {
		customerService.newCustomer(customer);
	}
}

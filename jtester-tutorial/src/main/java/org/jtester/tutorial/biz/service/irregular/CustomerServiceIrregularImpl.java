package org.jtester.tutorial.biz.service.irregular;

import org.jtester.tutorial.biz.model.Customer;
import org.jtester.tutorial.biz.service.CustomerService;

public class CustomerServiceIrregularImpl implements CustomerService {

	public String doNothing() {
		return "this is an irregular service.";
	}

	public Customer findCustomerByName(String name) {
		return null;
	}

}

package org.jtester.tutorial.biz.service;

import org.jtester.tutorial.biz.model.Customer;

public interface CustomerService {
	/**
	 * 不做任何事情
	 * 
	 * @return
	 */
	String doNothing();

	Customer findCustomerByName(String name);
}

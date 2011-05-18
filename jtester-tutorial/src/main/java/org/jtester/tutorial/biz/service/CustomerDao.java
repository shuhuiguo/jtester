package org.jtester.tutorial.biz.service;

import org.jtester.tutorial.biz.model.Customer;

public interface CustomerDao {
	String doNothing();

	Customer findCustomerByName(String name);

	void newCustomer(Customer customer);
}

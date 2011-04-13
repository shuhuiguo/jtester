package org.jtester.tutorial.biz.service;

import org.jtester.tutorial.biz.model.Customer;

public interface CustomerService {
	Customer findCustomerByName(String name);
}

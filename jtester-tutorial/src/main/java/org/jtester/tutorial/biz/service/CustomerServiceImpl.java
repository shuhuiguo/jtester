package org.jtester.tutorial.biz.service;

import javax.annotation.Resource;

import org.jtester.tutorial.biz.model.Customer;

public class CustomerServiceImpl implements CustomerService {
	private OrderDao orderDao;

	@Resource
	private CustomerDao customerDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public Customer findCustomerByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public String doNothing() {
		return "this is service:" + this.getClass().getName();
	}
}

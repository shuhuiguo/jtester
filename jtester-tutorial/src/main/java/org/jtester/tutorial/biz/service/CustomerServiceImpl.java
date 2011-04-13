package org.jtester.tutorial.biz.service;

import javax.annotation.Resource;

public class CustomerServiceImpl {
	private OrderDao orderDao;

	@Resource
	private CustomerDao customerDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
}

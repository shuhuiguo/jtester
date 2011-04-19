package org.jtester.tutorial.biz.service;

import javax.annotation.Resource;

import org.jtester.tutorial.biz.model.Customer;

public class CustomerServiceImpl implements CustomerService {
	protected InvoiceDao invoiceDao;

	@Resource
	protected CustomerDao customerDao;

	public void setInvoiceDao(InvoiceDao orderDao) {
		this.invoiceDao = orderDao;
	}

	public Customer findCustomerByName(String name) {
		Customer cust = this.customerDao.findCustomerByName(name);
		return cust;
	}

	public String doNothing() {
		return "this is a service:" + this.getClass().getName();
	}
}

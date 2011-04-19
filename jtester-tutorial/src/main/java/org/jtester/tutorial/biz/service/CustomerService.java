package org.jtester.tutorial.biz.service;

import java.util.List;

import org.jtester.tutorial.biz.model.Customer;
import org.jtester.tutorial.biz.model.Invoice;

public interface CustomerService {
	/**
	 * 不做任何事情
	 * 
	 * @return
	 */
	String doNothing();

	Customer findCustomerByName(String customerName);

	List<Invoice> getInvoiceByCustomerName(String customerName);
}

package org.jtester.tutorial.biz.service;

import java.util.List;

import org.jtester.tutorial.biz.model.Invoice;

public interface InvoiceDao {
	List<Invoice> getInvoiceByCustomerName(String customerName);
}

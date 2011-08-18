package org.jtester.tutorial.biz.service;

import java.util.List;

import org.jtester.tutorial.biz.model.Invoice;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class InvoiceDaoImpl extends SqlMapClientDaoSupport implements InvoiceDao {

	public List<Invoice> getInvoiceByCustomerName(String customerName) {
		// TODO Auto-generated method stub
		return null;
	}

}

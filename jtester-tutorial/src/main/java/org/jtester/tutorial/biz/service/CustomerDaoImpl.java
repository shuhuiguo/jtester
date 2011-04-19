package org.jtester.tutorial.biz.service;

import org.jtester.tutorial.biz.model.Customer;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CustomerDaoImpl extends SqlMapClientDaoSupport implements CustomerDao {

	public String doNothing() {
		return "this is a dao:" + CustomerDaoImpl.class.getName();
	}

	public Customer findCustomerByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}

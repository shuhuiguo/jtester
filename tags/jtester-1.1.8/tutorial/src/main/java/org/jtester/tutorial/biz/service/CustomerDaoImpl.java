package org.jtester.tutorial.biz.service;

import java.sql.SQLException;

import org.jtester.tutorial.biz.model.Customer;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CustomerDaoImpl extends SqlMapClientDaoSupport implements CustomerDao {

	public String doNothing() {
		return "this is a dao:" + CustomerDaoImpl.class.getName();
	}

	public Customer findCustomerByName(String name) {
		try {
			Customer customer = (Customer) this.getSqlMapClient().queryForObject(
					"jtester-tutorial.find_customer_by_name", name);
			if (customer == null) {
				throw new RuntimeException("can't find phone item by user name:" + name);
			} else {
				return customer;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void newCustomer(Customer customer) {
		try {
			this.getSqlMapClient().insert("jtester-tutorial.insert_customer", customer);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

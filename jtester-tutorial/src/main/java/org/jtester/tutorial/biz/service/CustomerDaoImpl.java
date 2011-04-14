package org.jtester.tutorial.biz.service;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CustomerDaoImpl extends SqlMapClientDaoSupport implements CustomerDao {

	public String doNothing() {
		return "this is a dao:" + CustomerDaoImpl.class.getName();
	}

}

package org.jtester.tutorial.biz.service.irregular;

import org.jtester.tutorial.biz.service.CustomerService;
import org.jtester.tutorial.biz.service.CustomerServiceImpl;

public class CustomerServiceIrregularImpl extends CustomerServiceImpl implements CustomerService {

	public String doNothing() {
		return "this is an irregular service.";
	}

}

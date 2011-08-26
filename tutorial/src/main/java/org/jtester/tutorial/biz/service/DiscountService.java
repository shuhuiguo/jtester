package org.jtester.tutorial.biz.service;

import java.util.HashMap;
import java.util.Map;

import org.jtester.tutorial.biz.model.Customer;
import org.jtester.tutorial.biz.model.CustomerLevel;

public class DiscountService {
	private static Map<CustomerLevel, Double> discounts = new HashMap<CustomerLevel, Double>();

	/**
	 * 返回客户的折扣率
	 * 
	 * @param customer
	 * @return
	 */
	public double getDiscountByCustomer(Customer customer) {
		CustomerLevel level = customer.getLevel();
		Double discount = discounts.get(level);
		return discount == null ? 1.0d : discount;
	}
}

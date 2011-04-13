package org.jtester.tutorial.biz.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户清单
 * 
 * @author darui.wudr
 * 
 */
public class Invoice {
	private Customer customer;

	private List<LineItem> lineItems = null;

	public Invoice(Customer customer) {
		this.lineItems = new ArrayList<LineItem>();
		this.customer = customer;
	}

	public void addItemQuantity(Product product, int quantity) {
		LineItem lineItem = new LineItem();
		lineItem.setInvoice(this);
		lineItem.setProduct(product);
		lineItem.setQuantity(quantity);

		lineItems.add(lineItem);
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public Customer getCustomer() {
		return customer;
	}
}

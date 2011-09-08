package org.jtester.tutorial.biz.model;

import java.math.BigDecimal;

/**
 * 订单行
 * 
 * @author darui.wudr
 * 
 */
public class LineItem {

	private long id;

	private Invoice invoice;

	private Product product;

	private int quantity;

	private double discount;

	private BigDecimal totolPrice;

	public LineItem() {

	}

	public LineItem(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getDiscount() {
		return discount;
	}

	public double getUnitPrice() {
		return this.product.getUnitPrice();
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void caculate() {
		if (totolPrice == null) {
			double d = this.product.getUnitPrice() * this.quantity * (100 - this.discount) / 100;
			String str = String.format("%.2f", d);
			this.totolPrice = new BigDecimal(str);
		}
	}

	public BigDecimal getTotolPrice() {
		caculate();
		return this.totolPrice;
	}
}

package org.jtester.tutorial.biz.model;

/**
 * 产品
 * 
 * @author darui.wudr
 * 
 */
public class Product implements java.io.Serializable {
	private static final long serialVersionUID = 4958956501666121166L;

	private long id;

	private String name;

	private double unitPrice;

	private String description;

	private int stocks;

	public Product(String name, double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStocks() {
		return stocks;
	}

	public void setStocks(int stocks) {
		this.stocks = stocks;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}

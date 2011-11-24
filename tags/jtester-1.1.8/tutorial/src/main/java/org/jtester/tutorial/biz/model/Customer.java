package org.jtester.tutorial.biz.model;

/**
 * 客户
 * 
 * @author darui.wudr
 * 
 */
public class Customer implements java.io.Serializable {
	private static final long serialVersionUID = 3550005344875718952L;

	private long id;

	private String name;

	private String address;

	private CustomerLevel level;

	public Customer() {

	}

	public Customer(String name, String address) {
		this.name = name;
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public CustomerLevel getLevel() {
		return level;
	}

	public void setLevel(CustomerLevel level) {
		this.level = level;
	}
}

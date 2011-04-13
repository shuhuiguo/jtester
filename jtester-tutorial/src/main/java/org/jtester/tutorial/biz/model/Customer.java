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

	private Address address;

	private CustomerLevel level;

	public Customer() {

	}

	public Customer(String name, Address address) {
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public CustomerLevel getLevel() {
		return level;
	}

	public void setLevel(CustomerLevel level) {
		this.level = level;
	}
}

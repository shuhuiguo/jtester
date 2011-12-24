package org.jtester.beans;

import java.util.ArrayList;
import java.util.List;

public class User {
	private Integer id;

	private String name;

	private String first;
	private String last;

	private int age;

	private double salary;

	private boolean isFemale = false;

	private Address address;

	private List<Address> addresses;

	public User() {

	}

	public User(String name) {
		this.name = name;
	}

	public User(String first, String last) {
		this.first = first;
		this.last = last;
	}

	public User(Integer id, String first, String last) {
		this.id = id;
		this.first = first;
		this.last = last;
	}

	public User(String first, String last, Address address) {
		this.first = first;
		this.last = last;
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 构造一个供测试的用的user对象
	 * 
	 * @param name
	 * @return
	 */
	public static User newInstance(int id, String name) {
		User user = new User();
		user.setId(id);
		user.setName(name);

		return user;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public boolean isFemale() {
		return isFemale;
	}

	public void setFemale(boolean isFemale) {
		this.isFemale = isFemale;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}

	public static User mock() {
		User user = new User(1, "wu", "darui");
		user.setAddresses(new ArrayList<Address>() {

			private static final long serialVersionUID = 516532764093459888L;
			{
				this.add(new Address(2, "stree2"));
				this.add(new Address(3, "stree3"));
			}
		});
		return user;
	}
}

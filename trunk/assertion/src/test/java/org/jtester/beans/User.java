package org.jtester.beans;

public class User {
	private Integer id;

	private String name;

	private String first;
	private String last;

	private int age;

	private double salary;

	private boolean isFemale = false;

	public User() {

	}

	public User(String first, String last) {
		this.first = first;
		this.last = last;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
}

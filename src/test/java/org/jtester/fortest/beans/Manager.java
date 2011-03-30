package org.jtester.fortest.beans;

import java.io.Serializable;

public class Manager extends Employee {
	private static final long serialVersionUID = 843725563822394654L;
	private Employee secretary;

	private Serializable phoneNumber;

	public Manager() {
		super();
	}

	public Manager(String name, double sarary) {
		super(name, sarary);
	}

	public Employee getSecretary() {
		return secretary;
	}

	public void setSecretary(Employee secretary) {
		this.secretary = secretary;
	}

	public Serializable getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Serializable phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}

package org.jtester.tutorial.biz.model;

public class Address {

	private String postcode;

	private String detail;

	public Address() {

	}

	public Address(String detail, String postcode) {
		this.postcode = postcode;
		this.detail = detail;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}

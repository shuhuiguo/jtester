package org.jtester.tutorial01.beans;

import org.jtester.utility.DateUtil;

public class PhoneItem implements java.io.Serializable {
	private static final long serialVersionUID = 1145595282412714496L;
	private long id;
	private String username;
	private String mobile;
	private String mobile2;
	private String familyPhone;

	private Boolean gender;
	private String company;
	private String title;
	private String mail;

	private String createdDate;

	private String modifedTime;

	public PhoneItem() {
	}

	public PhoneItem(String username, String mobile) {
		this.username = username;
		this.mobile = mobile;
		this.createdDate = DateUtil.currDateStr();
		this.modifedTime = DateUtil.currDateTimeStr();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getFamilyPhone() {
		return familyPhone;
	}

	public void setFamilyPhone(String familyPhone) {
		this.familyPhone = familyPhone;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifedTime() {
		return modifedTime;
	}

	public void setModifedTime(String modifedTime) {
		this.modifedTime = modifedTime;
	}
}

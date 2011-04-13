package org.jtester.tutorial01.beans;

public class PhoneRecorder implements java.io.Serializable {
	private static final long serialVersionUID = 6482531484126014995L;
	private long id;
	private String phoneNo;
	private String bookId;
	private PhoneStatus status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public PhoneStatus getStatus() {
		return status;
	}

	public void setStatus(PhoneStatus status) {
		this.status = status;
	}

	public static enum PhoneStatus {
		DIAL_UP, MISSED, RECEIVED;
	}
}

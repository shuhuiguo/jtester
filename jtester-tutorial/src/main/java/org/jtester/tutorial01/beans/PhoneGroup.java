package org.jtester.tutorial01.beans;

public class PhoneGroup implements java.io.Serializable {
	private static final long serialVersionUID = 104322259850302278L;

	private long id;
	private String groupName;
	private String description;

	public PhoneGroup() {

	}

	public PhoneGroup(String groupname) {
		this.groupName = groupname;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

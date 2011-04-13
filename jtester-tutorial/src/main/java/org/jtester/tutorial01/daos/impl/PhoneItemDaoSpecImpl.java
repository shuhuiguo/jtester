package org.jtester.tutorial01.daos.impl;

import java.util.List;

import org.jtester.tutorial01.beans.PhoneItem;
import org.jtester.tutorial01.daos.PhoneItemDao;

public class PhoneItemDaoSpecImpl implements PhoneItemDao {
	private List<PhoneItem> phoneItems;

	public List<PhoneItem> findPageBook(int page, int pageSize) {
		return phoneItems;
	}

	public PhoneItem findPhoneByMobile(String mobile) {
		return null;
	}

	public PhoneItem findPhoneByName(String username) {
		return new PhoneItem("darui.wu", "my mobile");
	}

	public long getPhoneItemIdByName(String userName) {
		return 0;
	}

	public long insertPhoneItem(PhoneItem phoneItem) {
		return 0;
	}

	public void setPhoneItems(List<PhoneItem> phoneItems) {
		this.phoneItems = phoneItems;
	}
}

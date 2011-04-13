package org.jtester.tutorial01.daos;

import java.util.List;

import org.jtester.tutorial01.beans.PhoneItem;

public interface PhoneItemDao {
	PhoneItem findPhoneByName(String username);

	PhoneItem findPhoneByMobile(String mobile);

	long insertPhoneItem(PhoneItem phoneItem);

	List<PhoneItem> findPageBook(int page, int pageSize);

	long getPhoneItemIdByName(String userName);
}

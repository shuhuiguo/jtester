package org.jtester.tutorial01.daos;

import java.util.List;

import org.jtester.tutorial01.beans.PhoneGroup;
import org.jtester.tutorial01.beans.PhoneItem;

public interface PhoneGroupDao {
	List<PhoneItem> findPhoneItemsByGroupId(long groupId);

	long getGroupIdByName(String groupName);

	long insertPhoneGroup(PhoneGroup group);

	/**
	 * 把电话加入组
	 * 
	 * @param itemId
	 * @param groupId
	 */
	void addPhoneItemToGroup(long itemId, long groupId);
}

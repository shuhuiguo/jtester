package org.jtester.tutorial01.services.impl;

import java.util.List;

import org.jtester.tutorial01.beans.PhoneGroup;
import org.jtester.tutorial01.beans.PhoneItem;
import org.jtester.tutorial01.daos.PhoneGroupDao;
import org.jtester.tutorial01.daos.PhoneItemDao;
import org.jtester.tutorial01.services.PhoneBookService;

public class PhoneBookServiceImpl implements PhoneBookService {
	private PhoneItemDao phoneItemDao;

	private PhoneGroupDao phoneGroupDao;

	public void setPhoneItemDao(PhoneItemDao phoneItemDao) {
		this.phoneItemDao = phoneItemDao;
	}

	public void setPhoneGroupDao(PhoneGroupDao phoneGroupDao) {
		this.phoneGroupDao = phoneGroupDao;
	}

	public List<PhoneItem> findPageBook(int page, int pageSize) {
		return this.phoneItemDao.findPageBook(page, pageSize);
	}

	public PhoneItem findPhoneByMobile(String mobile) {
		return this.phoneItemDao.findPhoneByMobile(mobile);
	}

	public PhoneItem findPhoneByName(String username) {
		return this.phoneItemDao.findPhoneByName(username);
	}

	public List<PhoneItem> findPhoneItemsByGroupName(String groupName) {
		long groupId = this.phoneGroupDao.getGroupIdByName(groupName);
		return this.phoneGroupDao.findPhoneItemsByGroupId(groupId);
	}

	public void insertPhoneBook(String username, String mobile, String groupname) {
		PhoneItem phoneItem = new PhoneItem();
		phoneItem.setUsername(username);
		phoneItem.setMobile(mobile);

		long itemId = this.phoneItemDao.insertPhoneItem(phoneItem);
		PhoneGroup phoneGroup = new PhoneGroup(groupname);
		long groupId = this.phoneGroupDao.insertPhoneGroup(phoneGroup);
		this.phoneGroupDao.addPhoneItemToGroup(itemId, groupId);
	}
}

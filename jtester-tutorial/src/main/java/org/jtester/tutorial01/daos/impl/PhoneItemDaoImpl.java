package org.jtester.tutorial01.daos.impl;

import java.sql.SQLException;
import java.util.List;

import org.jtester.tutorial01.beans.PhoneItem;
import org.jtester.tutorial01.daos.PhoneItemDao;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class PhoneItemDaoImpl extends SqlMapClientDaoSupport implements PhoneItemDao {

	public PhoneItem findPhoneByMobile(String mobile) {
		try {
			PhoneItem book = (PhoneItem) this.getSqlMapClient().queryForObject("jtester-tutorial.find_phone_bymobile",
					mobile);
			return book;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public PhoneItem findPhoneByName(String username) {
		try {
			PhoneItem book = (PhoneItem) this.getSqlMapClient().queryForObject("jtester-tutorial.find_phone_byname",
					username);
			return book;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<PhoneItem> findPageBook(int page, int pageSize) {
		return null;// TODO
	}

	public long insertPhoneItem(PhoneItem phoneItem) {
		try {
			this.getSqlMapClient().insert("jtester-tutorial.insert_phone_item", phoneItem);
			long id = this.getPhoneItemIdByName(phoneItem.getUsername());
			return id;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public long getPhoneItemIdByName(String userName) {
		try {
			Long id = (Long) this.getSqlMapClient().queryForObject("jtester-tutorial.find_phone_item_id_by_name",
					userName);
			if (id == null) {
				throw new RuntimeException("can't find phone item by user name:" + userName);
			} else {
				return id;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

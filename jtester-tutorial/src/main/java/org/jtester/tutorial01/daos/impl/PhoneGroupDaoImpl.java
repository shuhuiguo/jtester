package org.jtester.tutorial01.daos.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtester.tutorial01.beans.PhoneGroup;
import org.jtester.tutorial01.beans.PhoneItem;
import org.jtester.tutorial01.daos.PhoneGroupDao;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class PhoneGroupDaoImpl extends SqlMapClientDaoSupport implements PhoneGroupDao {

	@SuppressWarnings("unchecked")
	public List<PhoneItem> findPhoneItemsByGroupId(long groupId) {
		try {
			List<?> items = (List<?>) this.getSqlMapClient().queryForList(
					"jtester-tutorial.find_phone_items_id_by_group", groupId);
			return (List<PhoneItem>) items;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public long insertPhoneGroup(PhoneGroup group) {
		try {
			this.getSqlMapClient().insert("jtester-tutorial.insert_phone_group", group);
			long id = this.getGroupIdByName(group.getGroupName());
			return id;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void addPhoneItemToGroup(final long itemId, final long groupId) {
		try {
			Map<String, Long> parameters = new HashMap<String, Long>() {
				private static final long serialVersionUID = 1L;
				{
					put("phoneItemId", itemId);
					put("phoneGroupId", groupId);
				}
			};
			this.getSqlMapClient().insert("jtester-tutorial.add_phone_item_to_group", parameters);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public long getGroupIdByName(String groupName) {
		try {
			Long id = (Long) this.getSqlMapClient().queryForObject("jtester-tutorial.find_group_id_by_name", groupName);
			if (id == null) {
				throw new RuntimeException("can't find phone group by name:" + groupName);
			} else {
				return id;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

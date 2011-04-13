package org.jtester.tutorial01.integrated;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fit.fixture.DtoPropertyFixture;
import org.jtester.tutorial01.beans.PhoneItem;
import org.jtester.tutorial01.daos.PhoneItemDao;

@SpringApplicationContext( { "spring/beans.xml", "spring/data-source.xml" })
public class SpringUsageFixture extends DtoPropertyFixture {
	@SpringBeanByName
	private PhoneItemDao phoneItemDao;

	public void insertUser(PhoneItem item) {
		phoneItemDao.insertPhoneItem(item);
	}
}

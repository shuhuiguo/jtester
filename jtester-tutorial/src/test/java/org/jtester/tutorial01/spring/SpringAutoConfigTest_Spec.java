package org.jtester.tutorial01.spring;

import java.util.ArrayList;
import java.util.List;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanFrom;
import org.jtester.testng.JTester;
import org.jtester.tutorial01.beans.PhoneItem;
import org.jtester.tutorial01.daos.PhoneItemDao;
import org.jtester.tutorial01.daos.impl.PhoneItemDaoSpecImpl;
import org.jtester.tutorial01.services.PhoneBookService;
import org.testng.annotations.Test;

@Test
@SpringApplicationContext({ "spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*", impl = "**.impl.*Impl"), @BeanMap(intf = "**.*", impl = "**.*Impl") })
public class SpringAutoConfigTest_Spec extends JTester {

	@SpringBeanByName
	PhoneBookService phoneBookService;

	@SpringBeanByName(claz = PhoneItemDaoSpecImpl.class)
	PhoneItemDao phoneItemDao;

	@SpringBeanFrom
	List<PhoneItem> phoneItems = new ArrayList<PhoneItem>() {
		private static final long serialVersionUID = 1144178821256035529L;

		{
			add(new PhoneItem("matt", "11112222"));
		}
	};

	@DbFit(when = "testFindPhoneItemByName.when.wiki")
	public void testFindPhoneItemByName() {
		PhoneItem item = phoneBookService.findPhoneByName("darui.wu");
		want.object(item).notNull().propertyEq("mobile", "my mobile");

		List<PhoneItem> items = this.phoneItemDao.findPageBook(1, 1);
		want.collection(items).sizeEq(1)
				.propertyCollectionLenientEq(new String[] { "username" }, new String[][] { { "matt" } });
	}
}

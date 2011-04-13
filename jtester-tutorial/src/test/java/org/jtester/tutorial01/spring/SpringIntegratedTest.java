package org.jtester.tutorial01.spring;

import java.util.List;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.Tracer;
import org.jtester.testng.JTester;
import org.jtester.tutorial01.beans.PhoneItem;
import org.jtester.tutorial01.daos.PhoneItemDao;
import org.jtester.tutorial01.services.PhoneBookService;
import org.testng.annotations.Test;

@Test
@SpringApplicationContext( { "spring/data-source.xml", "spring/beans.xml" })
@Tracer(spring = true, jdbc = true)
public class SpringIntegratedTest extends JTester {
	
	@SpringBeanByName("phoneItemDao")
	private PhoneItemDao dao;

	@SpringBeanByName
	PhoneBookService phoneBookService;

	@DbFit(when = "testFindPhoneItemByName.when.wiki")
	public void testFindPhoneItemByName() {
		PhoneItem item = dao.findPhoneByName("darui.wu");
		want.object(item).notNull().propertyEq("mobile", "159900001111");
	}

	@DbFit(when = "org/jtester/tutorial/debugit/testFindPhoneItemsByGroupName.wiki")
	public void testFindPhoneItemsByGroupName() {
		List<PhoneItem> items = phoneBookService.findPhoneItemsByGroupName("classmate");
		want.collection(items).sizeEq(2).propertyCollectionRefEq(new String[] { "username", "mobile" },
				new String[][] { { "darui.wu", "15900001111" }, { "jobs.he", "13900001111" } });
	}
}

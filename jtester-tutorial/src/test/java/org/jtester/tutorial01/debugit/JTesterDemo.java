package org.jtester.tutorial01.debugit;

import java.util.List;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.jtester.tutorial01.beans.PhoneItem;
import org.jtester.tutorial01.services.PhoneBookService;
import org.testng.annotations.Test;

@Test
@SpringApplicationContext( { "spring/data-source.xml", "spring/beans.xml" })
public class JTesterDemo extends JTester {
	@SpringBeanByName
	PhoneBookService phoneBookService;

	@DbFit(when = "testFindPhoneItemsByGroupName.wiki")
	public void testFindPhoneItemsByGroupName() {
		List<PhoneItem> items = phoneBookService.findPhoneItemsByGroupName("classmate");
		want.collection(items).sizeEq(2).propertyCollectionRefEq(new String[] { "username", "mobile" },
				new String[][] { { "darui.wu", "15900001111" }, { "jobs.he", "13900001111" } });
	}
}

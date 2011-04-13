package org.jtester.tutorial01.spring;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.jtester.tutorial01.beans.PhoneItem;
import org.jtester.tutorial01.services.PhoneBookService;
import org.testng.annotations.Test;

@Test
@SpringApplicationContext( { "spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*", impl = "**.impl.*Impl") })
public class SpringAutoConfigTest extends JTester {
	@SpringBeanByName
	PhoneBookService phoneBookService;

	@DbFit(when = "testFindPhoneItemByName.when.wiki")
	public void testFindPhoneItemByName() {
		PhoneItem item = phoneBookService.findPhoneByName("darui.wu");
		want.object(item).notNull().propertyEq("mobile", "159900001111");
	}
}

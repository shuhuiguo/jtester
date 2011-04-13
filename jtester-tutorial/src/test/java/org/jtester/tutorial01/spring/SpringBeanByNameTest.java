package org.jtester.tutorial01.spring;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.jtester.tutorial01.beans.PhoneItem;
import org.jtester.tutorial01.services.PhoneBookService;
import org.jtester.tutorial01.services.impl.PhoneBookServiceImpl;
import org.testng.annotations.Test;

@Test

@SpringApplicationContext(value = { "spring/beans.xml", "spring/data-source.xml" }, ignoreNoSuchBean = true)
public class SpringBeanByNameTest extends JTester {

	@SpringBeanByName(claz = PhoneBookServiceImplEx.class)
	PhoneBookService phoneBookService;

	public void testSpringBeanForNewInstance() {
		PhoneItem result = this.phoneBookService.findPhoneByName(null);
		want.object(result).notNull();
	}

	public static class PhoneBookServiceImplEx extends PhoneBookServiceImpl {
		@Override
		public PhoneItem findPhoneByName(String username) {
			return new PhoneItem();
		}
	}
}

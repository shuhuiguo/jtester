package org.jtester.tutorial01.spring;

import mockit.Mock;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanFrom;
import org.jtester.annotations.Tracer;
import org.jtester.testng.JTester;
import org.jtester.tutorial01.beans.PhoneItem;
import org.jtester.tutorial01.services.PhoneBookService;
import org.jtester.tutorial01.services.impl.PhoneBookServiceImpl;
import org.testng.annotations.Test;

@Test
@SpringApplicationContext(value = { "spring/beans.xml", "spring/data-source.xml" }, ignoreNoSuchBean = true)
@Tracer(spring = true, jdbc = true)
public class SpringBeanForTest extends JTester {

	@SpringBeanFrom
	PhoneBookService phoneBookService;

	public void testSpringBeanForMockUp() {
		final PhoneItem item = new PhoneItem();
		this.phoneBookService = new MockUp<PhoneBookService>() {
			@Mock
			public PhoneItem findPhoneByName(String username) {
				return item;
			}
		}.getMockInstance();

		PhoneItem result = this.phoneBookService.findPhoneByName(null);
		want.object(result).same(item);
	}

	public void testSpringBeanForNewInstance() {
		final PhoneItem item = new PhoneItem();
		this.phoneBookService = new PhoneBookServiceImpl() {
			@Override
			public PhoneItem findPhoneByName(String username) {
				return item;
			}
		};

		PhoneItem result = this.phoneBookService.findPhoneByName(null);
		want.object(result).same(item);
	}
}

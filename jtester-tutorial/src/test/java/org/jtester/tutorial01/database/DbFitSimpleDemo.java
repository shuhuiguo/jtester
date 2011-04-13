package org.jtester.tutorial01.database;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.jtester.tutorial01.services.PhoneBookService;
import org.testng.annotations.Test;

@Test
@SpringApplicationContext( { "spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*", impl = "**.impl.*Impl") })
public class DbFitSimpleDemo extends JTester {
	@SpringBeanByName
	PhoneBookService phoneBookService;

	@DbFit(when = "simpleDemo.when.wiki", then = "simpleDemo.then.wiki")
	public void simpleDemo() {
		phoneBookService.insertPhoneBook("darui.wu", "15922225555", "classmate");
	}

	@DbFit(when = "simpleDemo.when.wiki", then = "orderQuery.then.wiki")
	public void orderQuery() {
		phoneBookService.insertPhoneBook("darui.wu", "15922225555", "classmate");
	}

	@DbFit(when = "simpleDemo.when.wiki", then = "delete.then.wiki")
	public void delete() {
		phoneBookService.insertPhoneBook("darui.wu", "15922225555", "classmate");
	}

	@DbFit(when = "multiDataSource_url.wiki")
	public void multiDataSource() {
		System.out.println("test");
	}

	@DbFit(when = "connectFromFile.wiki")
	public void connectFromFile() {
		System.out.println("test");
	}
}

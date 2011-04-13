package org.jtester.tutorial01.spring;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.Tracer;
import org.jtester.testng.JTester;
import org.jtester.tutorial01.services.PhoneBookService;
import org.testng.annotations.Test;

@Test
@SpringApplicationContext(value = { "spring/refbeans.xml" }, ignoreNoSuchBean = true)
@Tracer(spring = true, jdbc = true)
public class IgnoreSpringBeanTest extends JTester {

	@SpringBeanByName
	PhoneBookService phoneBookService;

	public void testFindPhoneItemsByGroupName() {

	}
}

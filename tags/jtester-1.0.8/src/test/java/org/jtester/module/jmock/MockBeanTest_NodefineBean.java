package org.jtester.module.jmock;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.deprecated.MockBean;
import org.jtester.fortest.formock.SomeInterface;
import org.jtester.fortest.formock.SpringBeanService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings("deprecation")
@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/springbean service.xml" })
@Test(groups = { "jtester", "mock-demo" })
public class MockBeanTest_NodefineBean extends JTester {
	@SpringBeanByName
	private SpringBeanService springBeanService1;

	@MockBean
	protected SomeInterface dependency1;

	@MockBean
	protected SomeInterface dependency2;

	public void testDependency() {
		want.object(springBeanService1).notNull();
		want.object(springBeanService1.getDependency1()).notNull();
		want.object(springBeanService1.getDependency2()).notNull();
	}
}

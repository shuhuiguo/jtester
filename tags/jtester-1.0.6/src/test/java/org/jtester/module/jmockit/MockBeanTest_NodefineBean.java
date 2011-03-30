package org.jtester.module.jmockit;

import mockit.Mocked;

import org.jtester.fortest.formock.SomeInterface;
import org.jtester.fortest.formock.SpringBeanService;
import org.jtester.testng.JTester;
import org.jtester.unitils.spring.SpringBeanFor;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SpringApplicationContext({ "org/jtester/fortest/spring/springbean service.xml" })
@Test(groups = { "jtester", "mock-demo" })
public class MockBeanTest_NodefineBean extends JTester {
	@SpringBeanByName
	private SpringBeanService springBeanService1;

	@Mocked
	@SpringBeanFor
	protected SomeInterface dependency1;

	@SpringBeanFor
	@Mocked
	protected SomeInterface dependency2;

	public void testDependency() {
		want.object(springBeanService1).notNull();
		want.object(springBeanService1.getDependency1()).notNull();
		want.object(springBeanService1.getDependency2()).notNull();
	}
}

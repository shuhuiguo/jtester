package org.jtester.module.jmock;

import org.jtester.annotations.Mock;
import org.jtester.fortest.formock.SomeInterface;
import org.jtester.fortest.formock.SpringBeanService;
import org.jtester.fortest.formock.SpringBeanService.SpringBeanServiceImpl1;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings("deprecation")
@Test(groups = "jtester")
public class MockTest_ByName2 extends JTester {

	private SpringBeanService springBeanService1 = new SpringBeanServiceImpl1();

	private SpringBeanService springBeanService2 = new SpringBeanServiceImpl1();

	@Mock(injectInto = { "springBeanService1", "springBeanService2" }, byProperty = { "dependency1", "dependency1" })
	private SomeInterface someInterface1;

	@Mock(injectInto = { "springBeanService1", "springBeanService2" }, byProperty = { "dependency2", "dependency2" })
	private SomeInterface someInterface2;

	@Test
	public void testMock_ByName() {
		want.object(springBeanService1.getDependency1()).same(someInterface1);
		want.object(springBeanService1.getDependency2()).same(someInterface2);

		want.object(springBeanService2.getDependency1()).same(someInterface1);
		want.object(springBeanService2.getDependency2()).same(someInterface2);
	}
}

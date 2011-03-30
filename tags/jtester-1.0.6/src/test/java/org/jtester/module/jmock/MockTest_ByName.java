package org.jtester.module.jmock;

import org.jtester.annotations.deprecated.Mock;
import org.jtester.fortest.formock.SomeInterface;
import org.jtester.fortest.formock.SpringBeanService;
import org.jtester.fortest.formock.SpringBeanService.SpringBeanServiceImpl1;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;
@SuppressWarnings("deprecation")
@Test(groups = "jtester")
public class MockTest_ByName extends JTester {

	private SpringBeanService springBeanService = new SpringBeanServiceImpl1();

	@Mock(injectInto = "springBeanService", byProperty = "dependency1")
	private SomeInterface someInterface1;

	@Mock(injectInto = "springBeanService", byProperty = "dependency2")
	private SomeInterface someInterface2;

	@Test
	public void testMock_ByName() {
		want.object(springBeanService.getDependency1()).same(someInterface1);
		want.object(springBeanService.getDependency2()).same(someInterface2);
	}
}

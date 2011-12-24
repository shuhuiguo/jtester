package org.jtester.module.spring;

import mockit.Mocked;

import org.jtester.IAssertion;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fortest.formock.SomeInterface;
import org.jtester.fortest.formock.SpringBeanService;
import org.junit.Test;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/mockbeans-withdependency.xml" })
public class MockBeanTest_MissingDependency implements IAssertion {
	@SpringBeanByName
	private SpringBeanService springBeanService1;

	@Mocked
	protected SomeInterface dependency2;

	@Test
	public void testDependency_MockBean() {
		want.object(springBeanService1.getDependency1()).notNull();
		want.object(springBeanService1.getDependency2()).notNull();
	}

	@Mocked
	private SpringBeanService springBeanService2;

	@Test
	public void testDependency_UnMockBean() {
		new Expectations() {
			{
				when(springBeanService2.getDependency1()).thenReturn(dependency2);
				when(springBeanService2.getDependency2()).thenReturn(dependency2);
			}
		};
		want.object(springBeanService2.getDependency1()).notNull();
		want.object(springBeanService2.getDependency2()).notNull();
	}
}

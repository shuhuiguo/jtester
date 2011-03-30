package org.jtester.module.jmockit;

import mockit.Mocked;

import org.jtester.fortest.formock.SomeInterface;
import org.jtester.fortest.formock.SpringBeanService;
import org.jtester.testng.JTester;
import org.jtester.unitils.spring.SpringBeanFor;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SpringApplicationContext({ "org/jtester/fortest/spring/mockbeans-withdependency.xml" })
@Test(groups = { "jtester", "mock-demo" })
public class MockBeanTest_MissingDependency extends JTester {
	@SpringBeanByName
	private SpringBeanService springBeanService1;

	@SpringBeanFor
	@Mocked
	protected SomeInterface dependency2;

	public void testDependency_MockBean() {
		want.object(springBeanService1.getDependency1()).notNull();
		want.object(springBeanService1.getDependency2()).notNull();
	}

	@SpringBeanFor
	@Mocked
	private SpringBeanService springBeanService2;

	@Test
	public void testDependency_UnMockBean() {
		new Expectations() {
			{
				when(springBeanService2.getDependency1()).thenReturn(
						dependency2);
				when(springBeanService2.getDependency2()).thenReturn(
						dependency2);

			}
		};
		want.object(springBeanService2.getDependency1()).notNull();
		want.object(springBeanService2.getDependency2()).notNull();
	}

	/**
	 * 重复运行一次，看看Mock字段是否会发生测试方法间干扰
	 */
	@Test(dependsOnMethods = "testDependency_UnMockBean")
	public void testDependency_UnMockBean2() {
		new Expectations() {
			{
				when(springBeanService2.getDependency1()).thenReturn(
						dependency2);
				when(springBeanService2.getDependency2()).thenReturn(
						dependency2);
			}
		};
		want.object(springBeanService2.getDependency1()).notNull();
		want.object(springBeanService2.getDependency2()).notNull();
	}
}

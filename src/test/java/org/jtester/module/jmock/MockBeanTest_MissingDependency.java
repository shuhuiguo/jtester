package org.jtester.module.jmock;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.deprecated.MockBean;
import org.jtester.core.Je;
import org.jtester.fortest.formock.SomeInterface;
import org.jtester.fortest.formock.SpringBeanService;
import org.jtester.module.core.helper.JmockModuleHelper;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings("deprecation")
@SpringApplicationContext( { "org/jtester/fortest/spring/mockbeans-withdependency.xml" })
@Test(groups = { "jtester", "mock-demo" })
public class MockBeanTest_MissingDependency extends JTester {
	@SpringBeanByName
	private SpringBeanService springBeanService1;

	@MockBean
	protected SomeInterface dependency2;

	public void testDependency_MockBean() {
		want.object(springBeanService1.getDependency1()).notNull();
		want.object(springBeanService1.getDependency2()).notNull();
	}

	@MockBean
	private SpringBeanService springBeanService2;

	@Test
	public void testDependency_UnMockBean() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.one(springBeanService2).getDependency1();
				will.returns.value(dependency2);
				will.call.one(springBeanService2).getDependency2();
				will.returns.value(dependency2);

			}
		});
		want.object(springBeanService2.getDependency1()).notNull();
		want.object(springBeanService2.getDependency2()).notNull();
	}

	/**
	 * 重复运行一次，看看Mock字段是否会发生测试方法间干扰
	 */
	@Test(dependsOnMethods = "testDependency_UnMockBean")
	public void testDependency_UnMockBean2() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.one(springBeanService2).getDependency1();
				will.returns.value(dependency2);
				will.call.one(springBeanService2).getDependency2();
				will.returns.value(dependency2);

			}
		});
		want.object(springBeanService2.getDependency1()).notNull();
		want.object(springBeanService2.getDependency2()).notNull();
	}
}

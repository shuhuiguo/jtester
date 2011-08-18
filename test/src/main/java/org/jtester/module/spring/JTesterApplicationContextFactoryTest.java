package org.jtester.module.spring;

import org.jtester.fortest.formock.SomeInterface.SomeInterfaceImpl1;
import org.jtester.fortest.formock.SomeInterface.SomeInterfaceImpl2;
import org.jtester.fortest.formock.SpringBeanService;
import org.testng.annotations.Test;

@Test(groups = { "JTester" })
public class JTesterApplicationContextFactoryTest extends org.jtester.testng.JTester {
	private static final String TO_BE_OVERRIDEN_BEAN_NAME = "toBeOverriden";
	private static final String ANOTHER_BEAN_NAME = "springBeanService";

	private JTesterClassPathXmlApplicationContext context;

	@Test
	public void testNoOverride() throws Throwable {
		// MockBeanRegister.cleanRegister();
		context = new JTesterClassPathXmlApplicationContext(this,
				new String[] { "org/jtester/module/spring/testedbeans/xml/mock-spring-beans-test.xml" }, true, null,
				false);

		want.object(context.getBean(TO_BE_OVERRIDEN_BEAN_NAME)).clazIs(SomeInterfaceImpl1.class);

		SpringBeanService anotherBean = (SpringBeanService) context.getBean(ANOTHER_BEAN_NAME);
		want.object(anotherBean).notNull();
		want.object(anotherBean.getDependency1()).clazIs(SomeInterfaceImpl1.class);
		want.object(anotherBean.getDependency2()).clazIs(SomeInterfaceImpl1.class);
	}

	@Test
	public void testOverride() throws Throwable {
		// MockBeanRegister.cleanRegister();
		context = new JTesterClassPathXmlApplicationContext(this,
				new String[] { "org/jtester/module/spring/testedbeans/xml/mock-spring-beans-test.xml" }, true, null,
				false);

		SpringBeanService anotherBean = (SpringBeanService) context.getBean(ANOTHER_BEAN_NAME);
		want.object(anotherBean).notNull();
		want.object(anotherBean.getDependency1()).clazIs(SomeInterfaceImpl1.class);
		want.object(anotherBean.getDependency2()).clazIs(SomeInterfaceImpl1.class);

		want.object(context.getBean(TO_BE_OVERRIDEN_BEAN_NAME)).clazIs(SomeInterfaceImpl1.class);
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void testOverride_failure() throws Throwable {
		// MockBeanRegister.cleanRegister();
		context = new JTesterClassPathXmlApplicationContext(this,
				new String[] { "org/jtester/module/spring/testedbeans/xml/mock-spring-beans-test.xml" }, true, null,
				false);

		SpringBeanService anotherBean = (SpringBeanService) context.getBean(ANOTHER_BEAN_NAME);
		want.object(anotherBean).notNull();
		want.object(anotherBean.getDependency1()).clazIs(SomeInterfaceImpl2.class);
	}
}

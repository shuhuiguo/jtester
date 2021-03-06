package org.jtester.module.jmockit.mockbug;

import mockit.Mock;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringInitMethod;
import org.jtester.testng.JTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("unused")
@Test
@SpringApplicationContext
@AutoBeanInject
public class MethodServiceTest_SpringInject extends JTester {
	@SpringBeanByName
	TestedMethodService service;

	@SpringInitMethod
	protected void mockTestedMethodService() {
		new MockUp<TestedMethodService>() {
			TestedMethodService it;

			@Mock
			public void $init() {
				reflector.setField(it, "name", "init mock");
			}
		};
	}

	public void testBeforeMethodMock() {
		String result = service.sayHello();
		System.out.println(result);
		want.string(result).isEqualTo("hello, init mock");

		String name = reflector.getField(service, "name");
		want.string(name).isEqualTo("init mock");
	}
}

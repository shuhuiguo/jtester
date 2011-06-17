package org.jtester.module.jmockit.mockbug;

import mockit.Mock;
import mockit.MockUp;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SuppressWarnings("unused")
public class JMockitMockManagerTest2 extends JMockitMockManageBaseTest {

	@SpringBeanByName
	private SayHelloImpl sayHello;

	@SpringBeanByName
	private SayHelloImpl2 sayHello2;

	public void sayHello_real1() {
		String str = sayHello.sayHello();
		want.string(str).isEqualTo("say hello:darui.wu");
	}

	public void sayHello_real2() {
		new MockUp<SayHelloImpl>() {
			@Mock
			public String getName() {
				return "test";
			}
		};
		String str = sayHello.sayHello();
		want.string(str).isEqualTo("say hello:test");
	}

	public void sayHello_real3() {
		String str = sayHello2.sayHello();
		want.string(str).isEqualTo("say hello:darui.wu");
	}
}

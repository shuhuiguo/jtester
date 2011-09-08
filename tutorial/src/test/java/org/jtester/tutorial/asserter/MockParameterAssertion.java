package org.jtester.tutorial.asserter;

import mockit.Mock;
import mockit.Mocked;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

/**
 * mock参数断言
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings("unused")
@Test(groups = "tutorial")
public class MockParameterAssertion extends JTester {
	@Mocked
	ISayHello sayHello;

	public void testAssertMockParameter() {
		new Expectations() {
			{
				when(sayHello.sayHello(the.string().eq("welcome").wanted(), the.string().eq("darui.wu").wanted()))
						.thenReturn("welcome,    darui.wu.");
			}
		};
		String result = sayHello.sayHello("welcome", "darui.wu");
		// String result = "welcome,  darui.wu.";
		want.string(result).eqIgnoreSpace("welcome, darui.wu.");
	}

	public void testAssertMockParameter2() {
		ISayHello sayHello = new MockUp<ISayHello>() {
			@Mock
			// (invocations = 1)
			public String sayHello(String greeting, String name) {
				want.string(greeting).eq("welcome");
				want.string(name).eq("darui.wu");
				return "welcome,  darui.wu.";
			}
		}.getMockInstance();

		String result = sayHello.sayHello("welcome", "darui.wu");
		// String result = "welcome,  darui.wu.";
		want.string(result).eqIgnoreSpace("welcome, darui.wu.");
	}
}

interface ISayHello {
	String sayHello(String greeting, String name);
}

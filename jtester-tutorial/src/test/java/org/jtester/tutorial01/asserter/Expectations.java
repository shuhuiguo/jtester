package org.jtester.tutorial01.asserter;

import mockit.Mocked;

import org.jtester.testng.JTester;
import org.jtester.tutorial01.asserter.bean.SayHello;
import org.testng.annotations.Test;

public class Expectations extends JTester {

	@Mocked
	SayHello sayHello;

	@Test
	public void testExpectationsDemo() {
		new Expectations() {
			{
				when(sayHello.sayHello(the.string().contains(".").start("darui").end("wu").wanted())).thenReturn(
						"hello darui.wu!");
			}
		};
		String str = sayHello.sayHello("darui.wu");
		want.string(str).contains("darui.wu").start("hello").end("!");
	}
}

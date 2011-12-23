package org.jtester.tutorial.asserter;

import mockit.Mocked;

import org.jtester.testng.JTester;
import org.jtester.tutorial.asserter.SayHello;
import org.testng.annotations.Test;

public class SayHelloTest_ExpectationsAssert extends JTester {

	@Mocked
	SayHello sayHello;

	@Test
	public void testSayHello_ExpectationsDemo() {
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

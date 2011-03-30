package org.jtester.module.jmockit;

import mockit.NonStrict;

import org.jtester.module.jmockit.JMocketVerifications;
import org.jtester.testng.JTester;
import org.jtester.utility.JTesterLogger;
import org.testng.annotations.Test;

@Test
public class JMocketVerificationsTest extends JTester {
	// @NonStrict

	// @NonStrict

	@NonStrict
	Hello hello1;

	public void testVerifyApi() {
		// new JMockitExpectations() {
		// Hello hello;
		// {
		// hello.sayHello("darui.wu");
		// }
		// };
		Hello hello = new Hello();
		hello.sayHello("darui.wu");

		new JMocketVerifications() {
			// Hello hello1;
			{
				hello1.sayHello(with("1", the.string().contains("wu")));
				times = 1;
			}
		};
	}

	public static class Hello {
		void sayHello(String name) {
			JTesterLogger.info("hello world, " + name);
		}
	}
}

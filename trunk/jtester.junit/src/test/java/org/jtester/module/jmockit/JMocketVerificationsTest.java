package org.jtester.module.jmockit;

import mockit.NonStrict;

import org.jtester.junit.JTester;
import org.jtester.module.core.utility.MessageHelper;
import org.jtester.module.jmockit.extend.JMocketVerifications;
import org.junit.Test;

public class JMocketVerificationsTest extends JTester {

	@NonStrict
	Hello hello1;

	@Test
	public void testVerifyApi() {
		Hello hello = new Hello();
		hello.sayHello("darui.wu");

		new JMocketVerifications() {
			{
				hello1.sayHello(with("1", the.string().contains("wu")));
				times = 1;
			}
		};
	}

	public static class Hello {
		void sayHello(String name) {
			MessageHelper.info("hello world, " + name);
		}
	}
}

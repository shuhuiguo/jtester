package org.jtester.module.jmockit;

import mockit.NonStrict;

import org.apache.log4j.Logger;
import org.jtester.assertion.jmockit.JMocketVerifications;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test
public class JMocketVerificationsTest extends JTester {
	private final static Logger log4j = Logger.getLogger(JMocketVerificationsTest.class);

	@NonStrict
	Hello hello1;

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
			log4j.info("hello world, " + name);
		}
	}
}

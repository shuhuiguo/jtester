package org.jtester.module.jmockit.mockbug;

import mockit.Mocked;

import org.jtester.annotations.DbFit;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "for-test")
public class JMockitExpectationsBugTest extends JTester {

	@Mocked
	SayHello sayHello;

	@DbFit
	public void testHasExpectations() {
		new Expectations() {
			{
				when(sayHello.sayHello("mock")).thenReturn("say hello mock");
			}
		};
	}

	public void testNotExpecations() {

	}
}

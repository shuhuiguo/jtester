package org.jtester.module.jmockit;

import mockit.Mocked;

import org.jtester.annotations.Inject;
import org.jtester.fortest.service.CalledService;
import org.jtester.fortest.service.CallingService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "JTester" })
public class JMockitExpectationsTest extends JTester {
	@Mocked
	@Inject(targets = "callingService")
	private CalledService calledService;

	private CallingService callingService = new CallingService();

	@Test
	public void returnValue() {
		new Expectations() {
			{
				when(calledService.called(the.string().contains("test").wanted())).thenReturn("dddd");
				when(calledService.called(the.string().any().wanted())).callIgnoreTimes();
			}
		};
		callingService.call("i am a test message!");
	}

	@Test
	public void returnValue_ignore() {
		new Expectations() {
			{
				when(calledService.called(the.string().contains("test").wanted())).callIgnoreTimes().thenReturn("dddd");
			}
		};
		callingService.call("i am a test message!");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void returnValue_never() {
		new Expectations() {
			{
				when(calledService.called(anyString)).callNeverOccur();
			}
		};
		callingService.call("i am a test message!");
	}

	@Test(expectedExceptions = { RuntimeException.class })
	public void throwException() {
		new Expectations() {
			{
				when(calledService.called(the.string().contains("test").wanted())).thenThrows(
						new RuntimeException("test exception"));
			}
		};
		callingService.call("i am a test message!");
	}
}

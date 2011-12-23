package org.jtester.module.jmockit.demo2;

import java.util.Observable;
import java.util.concurrent.Callable;

import mockit.Mocked;
import mockit.Verifications;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

public class InternalInstancesDemoTest extends JTester {
	@Mocked(capture = 10)
	Service service;

	@Test
	public void captureAllInternallyCreatedInstances(@Mocked(capture = 1) final Callable<?> callable) throws Exception {
		Service initialMockService = service;

		new NonStrictExpectations() {
			@SuppressWarnings("unused")
			@Mocked(capture = 1)
			Observable observable;
			{
				service.doSomething();
				returns(3, 4);
			}
		};

		InternalInstancesDemo unit = new InternalInstancesDemo();
		int result = unit.businessOperation(true);

		want.object(unit.observable).notNull();
		want.object(initialMockService).not(the.object().same(service));
		want.number(result).isEqualTo(7);

		new Verifications() {
			{
				callable.call();
			}
		};
	}
}

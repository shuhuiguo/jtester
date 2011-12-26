package org.jtester.exception;

import org.jtester.IAssertion;
import org.junit.Test;

public class JTesterExceptionTest implements IAssertion {
	@Test(expected = JTesterException.class)
	public void exception1() {
		throw new JTesterException("message");
	}

	@Test(expected = JTesterException.class)
	public void exception2() {
		throw new JTesterException(new Exception("message"));
	}

	@Test(expected = JTesterException.class)
	public void exception3() {
		throw new JTesterException("message", new Exception("message"));
	}
}

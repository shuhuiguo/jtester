package org.jtester.assertion.jmockit;

public interface JTesterInvocations {
	void returnValue(Object value);

	void returnValue(Object firstValue, Object... remainingValues);
}

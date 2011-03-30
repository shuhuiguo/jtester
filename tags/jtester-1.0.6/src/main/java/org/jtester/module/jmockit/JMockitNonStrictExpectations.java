package org.jtester.module.jmockit;

import mockit.NonStrictExpectations;
import mockit.internal.expectations.transformation.ActiveInvocations;

public class JMockitNonStrictExpectations extends NonStrictExpectations implements JTesterInvocations {
	protected InvokeTimes invoke;

	public JMockitNonStrictExpectations() {
		super();
		ExpectationsUtil.register(this);
	}

	public JMockitNonStrictExpectations(int numberOfIterations, Object... classesOrObjectsToBePartiallyMocked) {
		super(numberOfIterations, classesOrObjectsToBePartiallyMocked);
		ExpectationsUtil.register(this);
	}

	public JMockitNonStrictExpectations(Object... classesOrObjectsToBePartiallyMocked) {
		super(classesOrObjectsToBePartiallyMocked);
		ExpectationsUtil.register(this);
	}

	public <T> ExpectationsResult when(T o) {
		ActiveInvocations.maxTimes(-1);
		return new ExpectationsResult(this);
	}

	public void returnValue(Object value) {
		super.returns(value);
	}

	public void returnValue(Object firstValue, Object... remainingValues) {
		super.returns(firstValue, remainingValues);
	}
}

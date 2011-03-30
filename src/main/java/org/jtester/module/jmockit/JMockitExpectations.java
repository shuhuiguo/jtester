package org.jtester.module.jmockit;

import mockit.Expectations;
import mockit.internal.expectations.transformation.ActiveInvocations;

public class JMockitExpectations extends Expectations implements JTesterInvocations {
	public JMockitExpectations() {
		super();
		ExpectationsUtil.register(this);
	}

	public JMockitExpectations(int numberOfIterations, Object... classesOrObjectsToBePartiallyMocked) {
		super(numberOfIterations, classesOrObjectsToBePartiallyMocked);
		ExpectationsUtil.register(this);
	}

	public JMockitExpectations(Object... classesOrObjectsToBePartiallyMocked) {
		super(classesOrObjectsToBePartiallyMocked);
		ExpectationsUtil.register(this);
	}

	public <T> InvokeTimes when(T o) {
		return new InvokeTimes(this);
	}

	public void returnValue(Object value) {
		super.returns(value);
	}

	public void throwException(Throwable e) {
		ActiveInvocations.addResult(e);
	}

	public void returnValue(Object firstValue, Object... remainingValues) {
		super.returns(firstValue, remainingValues);
	}
}

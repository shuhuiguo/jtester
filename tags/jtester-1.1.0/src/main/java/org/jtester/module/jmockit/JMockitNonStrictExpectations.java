package org.jtester.module.jmockit;

import org.jtester.hamcrest.TheStyleAssertion;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.internal.expectations.transformation.ActiveInvocations;

public class JMockitNonStrictExpectations extends NonStrictExpectations implements JTesterInvocations {

	@Mocked(methods = { "" })
	protected InvokeTimes invokerTimes;

	@Mocked(methods = { "" })
	protected ExpectationsResult expectationsResult;

	@Mocked(methods = { "" })
	protected final TheStyleAssertion the;

	public JMockitNonStrictExpectations() {
		super();
		ExpectationsUtil.register(this);
		this.the = new TheStyleAssertion();
	}

	public JMockitNonStrictExpectations(int numberOfIterations, Object... classesOrObjectsToBePartiallyMocked) {
		super(numberOfIterations, classesOrObjectsToBePartiallyMocked);
		ExpectationsUtil.register(this);
		this.the = new TheStyleAssertion();
	}

	public JMockitNonStrictExpectations(Object... classesOrObjectsToBePartiallyMocked) {
		super(classesOrObjectsToBePartiallyMocked);
		ExpectationsUtil.register(this);
		this.the = new TheStyleAssertion();
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

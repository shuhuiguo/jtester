package org.jtester.module.jmockit;

import mockit.Expectations;
import mockit.Mocked;
import mockit.internal.expectations.transformation.ActiveInvocations;

import org.jtester.hamcrest.TheStyleAssertion;

public class JMockitExpectations extends Expectations implements JTesterInvocations {
	@Mocked(methods = { "" })
	protected InvokeTimes invokerTimes;

	@Mocked(methods = { "" })
	protected ExpectationsResult expectationsResult;

	@Mocked(methods = { "" })
	protected final TheStyleAssertion the;

	public JMockitExpectations() {
		super();
		ExpectationsUtil.register(this);
		this.the = new TheStyleAssertion();
	}

	public JMockitExpectations(int numberOfIterations, Object... classesOrObjectsToBePartiallyMocked) {
		super(numberOfIterations, classesOrObjectsToBePartiallyMocked);
		ExpectationsUtil.register(this);
		this.the = new TheStyleAssertion();
	}

	public JMockitExpectations(Object... classesOrObjectsToBePartiallyMocked) {
		super(classesOrObjectsToBePartiallyMocked);
		ExpectationsUtil.register(this);
		this.the = new TheStyleAssertion();
	}

	public <T> InvokeTimes when(T o) {
		return new InvokeTimes(this);
	}

	/**
	 * @deprecated <br>
	 *             please use thenReturn(value)
	 */
	@Deprecated
	public void returnValue(Object value) {
		super.returns(value);
	}

	public void thenReturn(Object value) {
		super.returns(value);
	}

	/**
	 * deprecated<br>
	 * please use thenThrow(e)
	 * 
	 * @param e
	 */
	@Deprecated
	public void throwException(Throwable e) {
		ActiveInvocations.addResult(e);
	}

	public void thenThrow(Throwable e) {
		ActiveInvocations.addResult(e);
	}

	/**
	 * @deprecated <br>
	 *             please use thenReturn(value...)
	 */
	@Deprecated
	public void returnValue(Object firstValue, Object... remainingValues) {
		super.returns(firstValue, remainingValues);
	}

	public void thenReturn(Object firstValue, Object... remainingValues) {
		super.returns(firstValue, remainingValues);
	}

	public void thenDoing(Delegate delegate) {
		super.returns(delegate);
	}

	public <T> T any(Class<T> claz) {
		T o = the.object().any().wanted(claz);
		return o;
	}

	@SuppressWarnings("unchecked")
	public <T> T is(T value) {
		T o = (T) the.object().reflectionEq(value).wanted();
		return o;
	}

	public static interface Delegate extends mockit.Delegate {
	}
}

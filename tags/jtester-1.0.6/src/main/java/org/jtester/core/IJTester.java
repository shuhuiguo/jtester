package org.jtester.core;

import org.jtester.core.context.JTesterFitnesse;
import org.jtester.core.context.JTesterReflector;
import org.jtester.hamcrest.TheStyleAssertion;
import org.jtester.hamcrest.WantStyleAssertion;

public interface IJTester {

	final WantStyleAssertion want = new WantStyleAssertion();

	final TheStyleAssertion the = new TheStyleAssertion();

	final JTesterFitnesse fit = new JTesterFitnesse();

	final JTesterReflector reflector = new JTesterReflector();

	class Expectations extends org.jtester.module.jmockit.JMockitExpectations {

		public Expectations() {
			super();
		}

		public Expectations(int numberOfIterations, Object... classesOrObjectsToBePartiallyMocked) {
			super(numberOfIterations, classesOrObjectsToBePartiallyMocked);
		}

		public Expectations(Object... classesOrObjectsToBePartiallyMocked) {
			super(classesOrObjectsToBePartiallyMocked);
		}

	}

	class NonStrictExpectations extends org.jtester.module.jmockit.JMockitNonStrictExpectations {

		public NonStrictExpectations() {
			super();
		}

		public NonStrictExpectations(int numberOfIterations, Object... classesOrObjectsToBePartiallyMocked) {
			super(numberOfIterations, classesOrObjectsToBePartiallyMocked);
		}

		public NonStrictExpectations(Object... classesOrObjectsToBePartiallyMocked) {
			super(classesOrObjectsToBePartiallyMocked);
		}
	}

	class MockUp<T> extends mockit.MockUp<T> {

		public MockUp() {
			super();
		}

		public MockUp(Class<?> classToMock) {
			super(classToMock);
		}
	}
}

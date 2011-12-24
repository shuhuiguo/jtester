package org.jtester;

@SuppressWarnings("rawtypes")
public interface IJMockit {
	class Expectations extends org.jtester.jmockit.JMockitExpectations {

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

	class NonStrictExpectations extends org.jtester.jmockit.JMockitNonStrictExpectations {

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

		public MockUp(Class classToMock) {
			super(classToMock);
		}
	}
}

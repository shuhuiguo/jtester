package org.jtester.core.junit;

import org.junit.internal.runners.InitializationError;

@SuppressWarnings({ "deprecation", "rawtypes" })
public class JUnit4Runner extends UnitilsJUnit4TestClassRunner {

	public JUnit4Runner(Class testClass) throws InitializationError {
		super(testClass);
	}
}

package org.jtester.junit.jmockit;

import org.jtester.junit.JTesterRunner;
import org.junit.internal.builders.JUnit4Builder;
import org.junit.runner.Runner;

public class JTesterBuilder extends JUnit4Builder {
	@Override
	public Runner runnerForClass(Class<?> testClass) throws Throwable {
		return new JTesterRunner(testClass);
	}
}

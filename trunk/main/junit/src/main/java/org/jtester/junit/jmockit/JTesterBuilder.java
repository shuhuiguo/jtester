package org.jtester.junit.jmockit;

import org.jtester.junit.JTesterRunner;
import org.jtester.module.CoreModule;
import org.junit.internal.builders.JUnit4Builder;
import org.junit.runner.Runner;

public class JTesterBuilder extends JUnit4Builder {
	static {
		/**
		 * 启动jtester模块功能
		 */
		CoreModule.initSingletonInstance();
	}

	@Override
	public Runner runnerForClass(Class<?> testClass) throws Throwable {
		return new JTesterRunner(testClass);
	}
}
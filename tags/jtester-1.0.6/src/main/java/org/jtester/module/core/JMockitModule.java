package org.jtester.module.core;

import java.util.Properties;

import mockit.Mockit;

import org.jtester.module.TestListener;
import org.jtester.utility.JTesterLogger;

public class JMockitModule implements Module {

	public void init(Properties configuration) {
		JTesterLogger.info("init jmockit in jmockit module.");

		mockit.internal.startup.Startup.initializeIfNeeded();
	}

	public void afterInit() {
	}

	public TestListener getTestListener() {
		return new JMockitTestListener();
	}

	protected class JMockitTestListener extends TestListener {
		@Override
		public void afterTestClass(Object testObject) {
			Mockit.tearDownMocks();
		}
	}
}

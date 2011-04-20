package org.jtester.module.core;

import mockit.Mockit;

import org.apache.log4j.Logger;
import org.jtester.module.TestListener;

public class JMockitModule implements Module {
	private final static Logger log4j = Logger.getLogger(JMockitModule.class);

	public void init() {
		log4j.info("init jmockit in jmockit module.");

		mockit.internal.startup.Startup.initializeIfNeeded();
	}

	public void afterInit() {
	}

	public TestListener getTestListener() {
		return new JMockitTestListener();
	}

	protected class JMockitTestListener extends TestListener {
		@Override
		public void afterTestClass(Object testObject) throws Exception {
			Mockit.tearDownMocks();
		}

		@Override
		protected String getName() {
			return "JMockitTestListener";
		}
	}
}

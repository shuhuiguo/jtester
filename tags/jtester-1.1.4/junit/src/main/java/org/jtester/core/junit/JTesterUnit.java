package org.jtester.core.junit;

import org.jtester.core.IJTester;
import org.jtester.module.core.CoreModule;
import org.junit.runner.RunWith;

@RunWith(JUnit4Runner.class)
public abstract class JTesterUnit implements IJTester {
	static {
		CoreModule.initSingletonInstance();
	}
}

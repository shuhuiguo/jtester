package org.jtester.junit;

import org.jtester.core.CoreModule;
import org.junit.runner.RunWith;

@RunWith(JTesterRunner.class)
public abstract class JTester {// implements IJTester
	static {
		CoreModule.initSingletonInstance();
	}
}

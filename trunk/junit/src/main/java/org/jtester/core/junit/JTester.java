package org.jtester.core.junit;

import org.jtester.core.IJTester;
import org.jtester.module.core.CoreModule;
import org.junit.runner.RunWith;

@RunWith(JTesterRunner.class)
public abstract class JTester implements IJTester {
	static {
		CoreModule.initSingletonInstance();
	}
}

package org.jtester.junit;

import org.jtester.IAssertion;
import org.jtester.module.CoreModule;
import org.junit.runner.RunWith;

@RunWith(JTesterRunner.class)
public abstract class JTester implements IAssertion {
	static {
		CoreModule.initSingletonInstance();
	}
}

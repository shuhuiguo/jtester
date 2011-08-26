package org.jtester.module.core.helper;

import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.api.Expectation;
import org.jmock.internal.ExpectationBuilder;
import org.jtester.exception.JTesterException;
import org.jtester.module.core.JmockModule;

/**
 * please use jmockit framework
 * 
 * @author darui.wudr
 * 
 */
@Deprecated
public class JmockModuleHelper {
	public static void checking(ExpectationBuilder expectations) {
		context().checking(expectations);
	}

	public static void assertIsSatisfied() {
		context().assertIsSatisfied();
	}

	public static States states(String name) {
		return context().states(name);
	}

	public static void addExpectation(Expectation expectation) {
		context().addExpectation(expectation);
	}

	private static JmockModule getJmockModule() {
		JmockModule module = ModulesManager.getModuleInstance(JmockModule.class);
		if (module == null) {
			throw new JTesterException("Unable to find an instance of an JmockModule in the modules repository.");
		}
		return module;
	}

	public static Mockery context() {
		return getJmockModule().getMockery();
	}
}

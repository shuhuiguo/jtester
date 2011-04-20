package org.jtester.core;

@Deprecated
public class Je extends org.jtester.module.jmock.JTesterExpectations {
	/**
	 * replaced by new JMockitExpectations(){{}}<br>
	 * or new JMockitNonStrictExpectations(){{}}
	 * 
	 * @param expectations
	 */
	@Deprecated
	public static void checking(org.jtester.module.jmock.JTesterExpectations expectations) {
		org.jtester.module.core.helper.JmockModuleHelper.checking(expectations);
	}
}

package org.jtester.module.jmock;

import org.jmock.Expectations;
import org.jtester.module.jmockit.ExpectationsUtil;

@Deprecated
public abstract class JTesterExpectations extends Expectations implements
		ICallMethod {
	public JTesterExpectations() {
		ExpectationsUtil.register(this);
	}

	/**
	 * replaced by org.jtester.jmockit.JMockitExpectations usage:<br>
	 * when(mock.api())[.callExactly(3)].thenReturn(...)<br>
	 * 
	 */
	@Deprecated
	public JExpections will = new JExpections(this);

	@Deprecated
	public static class InvokeExpections {
		public InvokeExpections(JTesterExpectations expectations) {
			this.call = expectations;
		}

		public ICallMethod call;
	}
}

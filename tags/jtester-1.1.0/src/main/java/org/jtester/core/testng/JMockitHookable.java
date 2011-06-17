package org.jtester.core.testng;

import mockit.internal.expectations.mocking.SharedFieldTypeRedefinitions;
import mockit.internal.state.TestRun;

import org.jtester.bytecode.reflector.FieldAccessor;
import org.jtester.bytecode.reflector.MethodAccessor;
import org.jtester.bytecode.reflector.impl.FieldAccessorImpl;
import org.jtester.bytecode.reflector.impl.MethodAccessorImpl;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestRunner;
import org.testng.internal.IConfiguration;

public class JMockitHookable {
	private final static MethodAccessor<Void> runMethod = new MethodAccessorImpl<Void>(IHookable.class, "run",
			IHookCallBack.class, ITestResult.class);

	private final static FieldAccessor<IConfiguration> mField = new FieldAccessorImpl<IConfiguration>(TestRunner.class,
			"m_configuration");

	/**
	 * jmockit的hookable<br>
	 * 
	 * @see mockit.integration.testng.internal.TestNGRunnerDecorator
	 */
	private final IHookable hookable;

	public JMockitHookable(final ITestContext context) {
		if (context == null) {
			throw new RuntimeException("the testng conext can't be null.");
		}
		IConfiguration m_configuration = mField.get(context);
		if (m_configuration == null) {
			this.hookable = null;
			// this.savePoint = null;
		} else {
			this.hookable = m_configuration.getHookable();
			if (this.hookable == null) {
				throw new RuntimeException(
						"You have enable jmockit module, but JMockit has not been initialized."
								+ " Check that your Java 5 VM has been started with the -javaagent:jmockit jar command line option.");
			}
			// this.savePoint = reflector.getField(this.hookable, "savePoint");
		}
	}

	public void run(IHookCallBack callBack, ITestResult testResult) {
		if (this.hookable == null) {
			callBack.runTestMethod(testResult);
			return;
		} else {
			runMethod.invoke(this.hookable, new Object[] { callBack, testResult });
		}
	}

	/**
	 * jmockit-0.999.9 bug fixed:<br>
	 * 测试类在 mockit.integration.testng.internal.TestNGRunnerDecorator 方法<br>
	 * run(IHookCallBack callBack, ITestResult testResult) -><br>
	 * executeTestMethod(IHookCallBack callBack, ITestResult testResult) -><br>
	 * cleanUpAfterTestMethodExecution() -><br>
	 * ExecutingTest..void finishExecution()中将全局的 nonStrictMocks
	 * 列表清空，导致后续的Expectations()出错
	 * 
	 * @param testedObject
	 */
	public static void reRegistedMockField(Object testedObject) {
		SharedFieldTypeRedefinitions sharedFieldTypeRedefinitions = TestRun.getSharedFieldTypeRedefinitions();
		sharedFieldTypeRedefinitions.assignNewInstancesToMockFields(testedObject);
	}
}

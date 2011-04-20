package org.jtester.core.testng;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.jtester.exception.ExceptionWrapper;
import org.jtester.exception.JTesterException;
import org.jtester.module.TestListener;
import org.jtester.module.core.CoreModule;
import org.jtester.utility.JTesterLogger;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * Base testNG class that will enable specify features.
 * 
 * @author darui.wudr
 */
public class JTesterTestNG implements IHookable {
	private final static Logger log4j = Logger.getLogger(JTesterTestNG.class);

	static {
		CoreModule.initSingletonInstance();
	}

	private Throwable jtesterBefoeClazzException = null;

	private final static String TEST_CLAZZ_INFO = "%s executing test class[%s] in thread[%d].";

	/**
	 * Called before a test of a test class is run. This is where
	 * {@link TestListener#beforeTestClass(Object)} is called.
	 */
	@BeforeClass(alwaysRun = true)
	public void jtesterBeforeClass() {
		this.jtesterBefoeClazzException = null;
		String hits = String
				.format(TEST_CLAZZ_INFO, "Begin", this.getClass().getName(), Thread.currentThread().getId());
		JTesterLogger.mark("\n\n\n" + hits);
		log4j.warn(hits);

		try {
			getTestListener().beforeTestClass(this);
		} catch (Throwable e) {
			this.jtesterBefoeClazzException = e;
		}
	}

	/**
	 * unitils在跑大量测试的时候, spring context初始化太多导致OOM<br>
	 * 判断springContext是否为null的原因是为了避免框架没有加载spring 模块时不会抛异常
	 */
	@AfterClass(alwaysRun = true)
	public void jtesterAfterClass() {
		try {
			getTestListener().afterTestClass(this);
		} catch (Throwable e) {
			this.jtesterBefoeClazzException = e;
		}
		String hits = String.format(TEST_CLAZZ_INFO, "End", this.getClass().getName(), Thread.currentThread().getId());
		log4j.warn(hits);
		JTesterLogger.mark(hits + "\n");
	}

	private Throwable jtesterBefoeTestSetupException = null;

	private final static String TEST_METHOD_INFO = "%s executing test method[%s . %s ()] in thread[%d].";

	/**
	 * Called before all test setup. This is where
	 * {@link TestListener#beforeTestSetUp} is called.
	 * 
	 * @param testMethod
	 *            The test method, not null
	 */
	@BeforeMethod(alwaysRun = true)
	public void jtesterBeforeTestSetUp(Method testMethod) {
		this.jtesterBefoeTestSetupException = null;

		String hits = String.format(TEST_METHOD_INFO, "Begin", this.getClass().getName(), testMethod.getName(), Thread
				.currentThread().getId());
		JTesterLogger.mark("\n" + hits);
		log4j.warn(hits);
		try {
			getTestListener().beforeTestSetUp(this, testMethod);
		} catch (Throwable e) {
			this.jtesterBefoeTestSetupException = e;
		}
	}

	/**
	 * Called after all test tear down. This is where
	 * {@link TestListener#afterTestTearDown} is called.
	 * <p/>
	 * NOTE: alwaysRun is enabled to be sure that this method is called even
	 * when an exception occurs during {@link #unitilsBeforeTestSetUp}.
	 * 
	 * @param testMethod
	 *            The test method, not null
	 */
	@AfterMethod(alwaysRun = true)
	public void jtesterAfterTestTearDown(Method testMethod) {
		try {
			getTestListener().afterTestTearDown(this, testMethod);
		} catch (Throwable e) {
			ExceptionWrapper.throwRuntimeException(e);
		}
		String hits = String.format(TEST_METHOD_INFO, "End", this.getClass().getName(), testMethod.getName(), Thread
				.currentThread().getId());
		log4j.warn(hits);
		JTesterLogger.mark(hits + "\n");
	}

	/**
	 * Implementation of the hookable interface to be able to call
	 * {@link TestListener#beforeTestMethod} and
	 * {@link TestListener#afterTestMethod}.
	 * 
	 * @param callBack
	 *            the TestNG test callback, not null
	 * @param testResult
	 *            the TestNG test result, not null
	 */
	public void run(IHookCallBack callBack, ITestResult testResult) {
		Method method = testResult.getMethod().getMethod();
		if (this.jtesterBefoeClazzException != null) {
			throw new JTesterException("jtester before test class exception", this.jtesterBefoeClazzException);
		}
		if (this.jtesterBefoeTestSetupException != null) {
			throw new JTesterException("jtester before test method exception", this.jtesterBefoeTestSetupException);
		}
		Throwable beforeTestMethodException = null;
		try {
			getTestListener().beforeTestMethod(this, method);

		} catch (Throwable e) {
			// hold exception until later, first call afterTestMethod
			beforeTestMethodException = e;
		}

		Throwable testMethodException = null;
		if (beforeTestMethodException == null) {
			callBack.runTestMethod(testResult);

			// Since TestNG calls the method using reflection, the exception is
			// wrapped in an InvocationTargetException
			testMethodException = testResult.getThrowable();
			if (testMethodException != null && testMethodException instanceof InvocationTargetException) {
				testMethodException = ((InvocationTargetException) testMethodException).getTargetException();
			}
		}

		Throwable afterTestMethodException = null;
		try {
			getTestListener().afterTestMethod(this, method,
					beforeTestMethodException != null ? beforeTestMethodException : testMethodException);

		} catch (Throwable e) {
			afterTestMethodException = e;
		}

		// if there were exceptions, make sure the exception that occurred first
		// is reported by TestNG
		if (beforeTestMethodException != null) {
			ExceptionWrapper.throwRuntimeException(beforeTestMethodException);
		} else {
			// We don't throw the testMethodException, it is already registered
			// by TestNG and will be reported to the user
			if (testMethodException == null && afterTestMethodException != null) {
				ExceptionWrapper.throwRuntimeException(afterTestMethodException);
			}
		}
	}

	/**
	 * @return The jTester test listener
	 */
	protected TestListener getTestListener() {
		return CoreModule.getTestListener();
	}
}

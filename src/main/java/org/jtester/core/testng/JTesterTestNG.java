package org.jtester.core.testng;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jtester.core.intf.ISpringContextContainer;
import org.jtester.core.intf.ITransaction;
import org.jtester.exception.JTesterException;
import org.jtester.module.TestListener;
import org.jtester.module.database.transaction.TestedObjectTransaction;
import org.jtester.module.utils.ModuleHelper;
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
public class JTesterTestNG implements IHookable, ISpringContextContainer, ITransaction {

	/* True if beforeTestSetUp was called */
	private boolean beforeTestSetUpCalled = false;

	/**
	 * 存放spring application context 上下文<br>
	 * 如果springModule enabled话,变量定义为Object类型是为了兼容没有使用spring容器的测试
	 */
	private Object springContext = null;

	private ThreadLocal<TestedObjectTransaction> testedObjectTransaction = new ThreadLocal<TestedObjectTransaction>();

	public Object getSpringContext() {
		return springContext;
	}

	public void setSpringContext(Object springContext) {
		this.springContext = springContext;
	}

	private Throwable jtesterBefoeClazzException = null;

	/**
	 * Called before a test of a test class is run. This is where
	 * {@link TestListener#afterCreateTestObject(Object)} is called.
	 */
	@BeforeClass(alwaysRun = true)
	protected void jtesterBeforeClass() {
		this.jtesterBefoeClazzException = null;
		try {
			getTestListener().beforeTestClass(this.getClass());
			getTestListener().afterCreateTestObject(this);
		} catch (Throwable e) {
			this.jtesterBefoeClazzException = e;
		}
	}

	/**
	 * unitils在跑大量测试的时候, spring context初始化太多导致OOM<br>
	 * 判断springContext是否为null的原因是为了避免框架没有加载spring 模块时不会抛异常
	 */
	@AfterClass(alwaysRun = true)
	protected void jtesterAfterClass() {
		try {
			getTestListener().afterTestClass(this);
		} catch (Throwable e) {
			this.jtesterBefoeClazzException = e;
		}
	}

	private Throwable jtesterBefoeTestSetupException = null;

	/**
	 * Called before all test setup. This is where
	 * {@link TestListener#beforeTestSetUp} is called.
	 * 
	 * @param testMethod
	 *            The test method, not null
	 */
	@BeforeMethod(alwaysRun = true)
	protected void jtesterBeforeTestSetUp(Method testMethod) {
		this.beforeTestSetUpCalled = true;
		this.jtesterBefoeTestSetupException = null;
		this.testedObjectTransaction.set(new TestedObjectTransaction());
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
	protected void jtesterAfterTestTearDown(Method testMethod) {
		if (beforeTestSetUpCalled) {
			beforeTestSetUpCalled = false;
			getTestListener().afterTestTearDown(this, testMethod);
		}
		this.testedObjectTransaction.remove();
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
		JTesterLogger.info("Execute test method[" + this.getClass().getName() + ":" + method.getName() + "]");

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
			throwException(beforeTestMethodException);
		} else {
			// We don't throw the testMethodException, it is already registered
			// by TestNG and will be reported to the user
			if (testMethodException == null && afterTestMethodException != null) {
				throwException(afterTestMethodException);
			}
		}
	}

	/**
	 * Throws an unchecked excepton for the given throwable.
	 * 
	 * @param throwable
	 *            The throwable, not null
	 */
	protected void throwException(Throwable throwable) {
		if (throwable instanceof RuntimeException) {
			throw (RuntimeException) throwable;
		} else if (throwable instanceof Error) {
			throw (Error) throwable;
		} else {
			throw new RuntimeException(throwable);
		}
	}

	/**
	 * @return The jTester test listener
	 */
	protected TestListener getTestListener() {
		return ModuleHelper.getTestListener();
	}

	/**
	 * @return The tested object transaction of current thread
	 */
	public TestedObjectTransaction getTestedObjectTransaction() {
		TestedObjectTransaction transaction = testedObjectTransaction.get();
		if (transaction == null) {
			transaction = new TestedObjectTransaction();
			testedObjectTransaction.set(transaction);
		}
		return transaction;
	}
}

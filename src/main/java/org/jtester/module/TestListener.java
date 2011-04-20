package org.jtester.module;

import java.lang.reflect.Method;

/**
 * Listener for test events. The events must follow following ordering:
 * <ul>
 * <li>[jTester] beforeTestClass - TestClass1</li>
 * <li>[Test] testBeforeClass - TestClass1 (not for JUnit3)</li>
 * <li>[jTester] afterCreateTestObject - TestClass1</li>
 * <li>[jTester] beforeTestSetUp - TestClass1</li>
 * <li>[Test] testSetUp - TestClass1</li>
 * <li>[jTester] beforeTestMethod - TestClass1 - test1</li>
 * <li>[Test] testMethod - TestClass1 - test1</li>
 * <li>[jTester] afterTestMethod - TestClass1 - test1</li>
 * <li>[Test] testTearDown - TestClass1</li>
 * <li>[jTester] afterTestTearDown - TestClass1</li>
 * <li>[jTester] afterCreateTestObject - TestClass1 (not for TestNG)</li>
 * <li>[jTester] beforeTestSetUp - TestClass1</li>
 * <li>[Test] testSetUp - TestClass1</li>
 * <li>[jTester] beforeTestMethod - TestClass1 - test2</li>
 * <li>[Test] testMethod - TestClass1 - test2</li>
 * <li>[jTester] afterTestMethod - TestClass1 - test2</li>
 * <li>[Test] testTearDown - TestClass1</li>
 * <li>[jTester] afterTestTearDown - TestClass1</li>
 * <li>[Test] testAfterClass - TestClass1 (not for JUnit3)</li>
 * </ul>
 * <p/>
 * The after methods will always when the before counterpart has run (or begun).
 * For example if an exception occurs during the beforeTestSetup method, the
 * afterTestTearDown method will still be called.
 * <p/>
 * Is implemented as an abstract class with empty methods instead of an
 * interface, since most implementations only need to implement a small subset
 * of the provided callback methods.
 */
public abstract class TestListener {

	/**
	 * Invoked before any of the test in a test class are run. This can be
	 * overridden to for example add test-class initialization.<br>
	 * Phase<br>
	 * o <b>beforeTestClass</b>&nbsp;(Object testObject) <br>
	 * o beforeTestSetUp&nbsp;(Object testObject, Method testMethod)<br>
	 * o beforeTestMethod&nbsp;(Object testObject, Method testMethod)<br>
	 * o afterTestMethod&nbsp;(Object testObject, Method testMethod, Throwable
	 * testThrowable)<br>
	 * o afterTestTearDown&nbsp;(Object testObject, Method testMethod)<br>
	 * o afterTestClass&nbsp;(Object testObject)<br>
	 * <br>
	 * 
	 * @param testObject
	 *            The test class, not null
	 * @throws Exception
	 */
	public void beforeTestClass(Object testObject) throws Exception {
		// empty
	}

	/**
	 * Invoked before the test setup (eg @Before) is run. This can be overridden
	 * to for example initialize the test-fixture.<br>
	 * Phase<br>
	 * o beforeTestClass&nbsp;(Object testObject) <br>
	 * o <b>beforeTestSetUp</b>&nbsp;(Object testObject, Method testMethod)<br>
	 * o beforeTestMethod&nbsp;(Object testObject, Method testMethod)<br>
	 * o afterTestMethod&nbsp;(Object testObject, Method testMethod, Throwable
	 * testThrowable)<br>
	 * o afterTestTearDown&nbsp;(Object testObject, Method testMethod)<br>
	 * o afterTestClass&nbsp;(Object testObject)<br>
	 * <br>
	 * 
	 * @param testObject
	 *            The test instance, not null
	 * @param testMethod
	 *            The test method, not null
	 * @throws Exception
	 */
	public void beforeTestSetUp(Object testObject, Method testMethod) throws Exception {
		// empty
	}

	/**
	 * Invoked before the test but after the test setup (eg @Before) is run.
	 * This can be overridden to for example further initialize the test-fixture
	 * using values that were set during the test setup.<br>
	 * Phase<br>
	 * o beforeTestClass&nbsp;(Object testObject) <br>
	 * o beforeTestSetUp&nbsp;(Object testObject, Method testMethod)<br>
	 * o <b>beforeTestMethod</b>&nbsp;(Object testObject, Method testMethod)<br>
	 * o afterTestMethod&nbsp;(Object testObject, Method testMethod, Throwable
	 * testThrowable)<br>
	 * o afterTestTearDown&nbsp;(Object testObject, Method testMethod)<br>
	 * o afterTestClass&nbsp;(Object testObject)<br>
	 * <br>
	 * 
	 * @param testObject
	 *            The test instance, not null
	 * @param testMethod
	 *            The test method, not null
	 * @throws Exception
	 */
	public void beforeTestMethod(Object testObject, Method testMethod) throws Exception {
		// empty
	}

	/**
	 * Invoked after the test run but before the test tear down (e.g. @After).
	 * This can be overridden to for example add assertions for testing the
	 * result of the test. It the before method or the test raised an exception,
	 * this exception will be passed to the method.<br>
	 * Phase<br>
	 * o beforeTestClass&nbsp;(Object testObject) <br>
	 * o beforeTestSetUp&nbsp;(Object testObject, Method testMethod)<br>
	 * o beforeTestMethod&nbsp;(Object testObject, Method testMethod)<br>
	 * o <b>afterTestMethod</b>&nbsp;(Object testObject, Method testMethod,
	 * Throwable testThrowable)<br>
	 * o afterTestTearDown&nbsp;(Object testObject, Method testMethod)<br>
	 * o afterTestClass&nbsp;(Object testObject)<br>
	 * <br>
	 * 
	 * @param testObject
	 *            The test instance, not null
	 * @param testMethod
	 *            The test method, not null
	 * @param testThrowable
	 *            The throwable thrown during the test or beforeTestMethod, null
	 *            if none was thrown
	 * @throws Exception
	 */
	public void afterTestMethod(Object testObject, Method testMethod, Throwable testThrowable) throws Exception {
		// empty
	}

	/**
	 * Invoked after the test tear down (eg @After). This can be overridden to
	 * for example perform extra cleanup after the test.<br>
	 * Phase<br>
	 * o beforeTestClass&nbsp;(Object testObject) <br>
	 * o beforeTestSetUp&nbsp;(Object testObject, Method testMethod)<br>
	 * o beforeTestMethod&nbsp;(Object testObject, Method testMethod)<br>
	 * o afterTestMethod&nbsp;(Object testObject, Method testMethod, Throwable
	 * testThrowable)<br>
	 * o <b>afterTestTearDown</b>&nbsp;(Object testObject, Method testMethod)<br>
	 * o afterTestClass&nbsp;(Object testObject)<br>
	 * <br>
	 * 
	 * @param testObject
	 *            The test instance, not null
	 * @param testMethod
	 *            The test method, not null
	 * @throws Exception
	 */
	public void afterTestTearDown(Object testObject, Method testMethod) throws Exception {
		// empty
	}

	/**
	 * Invoked after the test class tear down (eg @AfterClass).<br>
	 * Phase<br>
	 * o beforeTestClass&nbsp;(Object testObject) <br>
	 * o beforeTestSetUp&nbsp;(Object testObject, Method testMethod)<br>
	 * o beforeTestMethod&nbsp;(Object testObject, Method testMethod)<br>
	 * o afterTestMethod&nbsp;(Object testObject, Method testMethod, Throwable
	 * testThrowable)<br>
	 * o afterTestTearDown&nbsp;(Object testObject, Method testMethod)<br>
	 * o <b>afterTestClass</b>&nbsp;(Object testObject)<br>
	 * <br>
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	public void afterTestClass(Object testObject) throws Exception {
		// empty
	}

	public String toString() {
		return getName();
	}

	/**
	 * 监听器名称
	 * 
	 * @return
	 */
	protected abstract String getName();
}

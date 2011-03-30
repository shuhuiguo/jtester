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
	 * Invoked before the generic class setup (e.g. @BeforeClass) is performed.
	 * 
	 * @param testClass
	 *            The class whose test methods are about to be executed, not
	 *            null
	 */
	public void beforeTestClass(Class<?> testClass) {
		// empty
	}

	/**
	 * Invoked before any of the test in a test class are run. This can be
	 * overridden to for example add test-class initialization.
	 * 
	 * @param testObject
	 *            The test class, not null
	 */
	public void afterCreateTestObject(Object testObject) {
		// empty
	}

	/**
	 * Invoked before the test setup (eg @Before) is run. This can be overridden
	 * to for example initialize the test-fixture.
	 * 
	 * @param testObject
	 *            The test instance, not null
	 * @param testMethod
	 *            The test method, not null
	 */
	public void beforeTestSetUp(Object testObject, Method testMethod) {
		// empty
	}

	/**
	 * Invoked before the test but after the test setup (eg @Before) is run.
	 * This can be overridden to for example further initialize the test-fixture
	 * using values that were set during the test setup.
	 * 
	 * @param testObject
	 *            The test instance, not null
	 * @param testMethod
	 *            The test method, not null
	 */
	public void beforeTestMethod(Object testObject, Method testMethod) {
		// empty
	}

	/**
	 * Invoked after the test run but before the test tear down (e.g. @After).
	 * This can be overridden to for example add assertions for testing the
	 * result of the test. It the before method or the test raised an exception,
	 * this exception will be passed to the method.
	 * 
	 * @param testObject
	 *            The test instance, not null
	 * @param testMethod
	 *            The test method, not null
	 * @param testThrowable
	 *            The throwable thrown during the test or beforeTestMethod, null
	 *            if none was thrown
	 */
	public void afterTestMethod(Object testObject, Method testMethod, Throwable testThrowable) {
		// empty
	}

	/**
	 * Invoked after the test tear down (eg @After). This can be overridden to
	 * for example perform extra cleanup after the test.
	 * 
	 * @param testObject
	 *            The test instance, not null
	 * @param testMethod
	 *            The test method, not null
	 */
	public void afterTestTearDown(Object testObject, Method testMethod) {
		// empty
	}

	/**
	 * Invoked after the test class tear down (eg @AfterClass).
	 * 
	 * @param testObject
	 */
	public void afterTestClass(Object testObject) {
		// empty
	}
}

/*
 * Copyright (c) 2006-2011 Rogorio Liesenfeld
 * This file is subject to the terms of the MIT license (see LICENSE.txt).
 */
package mockit.integration.junit4.internal;

import java.lang.reflect.*;
import java.util.*;

import org.jtester.core.junit.DataFrom;
import org.junit.*;
import org.junit.runners.Suite.*;
import org.junit.runners.model.*;

import mockit.*;
import mockit.integration.*;
import mockit.internal.expectations.*;
import mockit.internal.state.*;
import mockit.internal.util.*;

/**
 * Startup mock that modifies the JUnit 4.5+ test runner so that it calls back
 * to JMockit immediately after every test executes. When that happens, JMockit
 * will assert any expectations set during the test, including expectations
 * specified through {@link Mock} as well as in {@link Expectations} subclasses.
 * <p/>
 * This class is not supposed to be accessed from user code. JMockit will
 * automatically load it at startup.
 */
@MockClass(realClass = FrameworkMethod.class, instantiation = Instantiation.PerMockedInstance)
public final class JUnit4TestRunnerDecorator extends TestRunnerDecorator {
	public FrameworkMethod it;
	private static volatile boolean shouldPrepareForNextTest = true;

	@Mock(reentrant = true)
	public Object invokeExplosively(Object target, Object... params) throws Throwable {
		Method method = it.getMethod();
		Class<?> testClass = target == null ? method.getDeclaringClass() : target.getClass();

		handleMockingOutsideTestMethods(target, testClass);

		// In case it isn't a test method, but a before/after method:
		if (it.getAnnotation(Test.class) == null) {
			if (shouldPrepareForNextTest && it.getAnnotation(Before.class) != null) {
				prepareForNextTest();
				shouldPrepareForNextTest = false;
			}

			TestRun.setRunningIndividualTest(target);
			TestRun.setRunningTestMethod(null);

			try {
				return it.invokeExplosively(target, params);
			} catch (Throwable t) {
				RecordAndReplayExecution.endCurrentReplayIfAny();
				Utilities.filterStackTrace(t);
				throw t;
			} finally {
				if (it.getAnnotation(After.class) != null) {
					TestRun.getExecutingTest().setRecordAndReplay(null);
				}
			}
		}

		if (shouldPrepareForNextTest) {
			prepareForNextTest();
		}

		shouldPrepareForNextTest = true;
		TestRun.setRunningTestMethod(method);

		try {
			executeTestMethod(target, params);
			return null; // it's a test method, therefore has void return type
		} catch (Throwable t) {
			Utilities.filterStackTrace(t);
			throw t;
		} finally {
			TestRun.finishCurrentTestExecution(true);
		}
	}

	private void handleMockingOutsideTestMethods(Object target, Class<?> testClass) {
		TestRun.enterNoMockingZone();

		try {
			if (testClass.isAnnotationPresent(SuiteClasses.class)) {
				setUpClassLevelMocksAndStubs(testClass);
			} else {
				updateTestClassState(target, testClass);
			}
		} finally {
			TestRun.exitNoMockingZone();
		}
	}

	private void executeTestMethod(Object target, Object... parameters) throws Throwable {
		SavePoint savePoint = new SavePoint();

		try {
			createInstancesForTestedFields(target);
			Method testMethod = it.getMethod();
			DataFrom dataFrom = testMethod.getAnnotation(DataFrom.class);
			Object[] mockParameters = null;
			if (dataFrom == null) {
				mockParameters = createInstancesForMockParameters(target, testMethod);
			}
			TestRun.setRunningIndividualTest(target);
			it.invokeExplosively(target, mockParameters == null ? parameters : mockParameters);
		} finally {
			concludeTestMethodExecution(savePoint);
		}
	}

	@Mock(reentrant = true)
	public void validatePublicVoidNoArg(boolean isStatic, List<Throwable> errors) {
		if (!isStatic && it.getMethod().getParameterTypes().length > 0) {
			it.validatePublicVoid(false, errors);
			return;
		}

		it.validatePublicVoidNoArg(isStatic, errors);
	}
}

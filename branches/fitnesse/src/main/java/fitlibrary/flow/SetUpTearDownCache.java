/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.flow;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import fitlibrary.flow.SetUpTearDownReferenceCounter.MethodCaller;
import fitlibrary.log.FitLibraryLogger;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Row;
import fitlibrary.table.TableFactory;
import fitlibrary.typed.TypedObject;

public class SetUpTearDownCache implements SetUpTearDown {
	private static Logger logger = FitLibraryLogger.getLogger(SetUpTearDownCache.class);
	private final SetUpTearDownReferenceCounter referenceCounter = new SetUpTearDownReferenceCounter();

	// @Override
	public void callSetUpOnSutChain(Object sutInitially, final Row row, final TestResults testResults) {
		Object sut = sutInitially;
		if (sut instanceof TypedObject)
			sut = ((TypedObject) sut).getSubject();
		referenceCounter.callSetUpOnNewReferences(sut, methodCaller(row, testResults));
	}

	// @Override
	public void callTearDownOnSutChain(Object sut, Row row, TestResults testResults) {
		referenceCounter.callTearDownOnReferencesThatAreCountedDown(sut, methodCaller(row, testResults));
	}

	// @Override
	public void callSuiteSetUp(Object suiteFixture, Row row, TestResults testResults) {
		callMethod(suiteFixture, "suiteSetUp", row, testResults);
	}

	// @Override
	public void callSuiteTearDown(Object suiteFixture, TestResults testResults) {
		callMethod(suiteFixture, "suiteTearDown", TableFactory.row("a"), testResults);
	}

	private MethodCaller methodCaller(final Row row, final TestResults testResults) {
		return new MethodCaller() {
			// @Override
			public void setUp(Object object) {
				callMethod(object, "setUp", row, testResults);
			}

			// @Override
			public void tearDown(Object object) {
				if (testResults.problems()) {
					Object result = callMethod(object, "onFailure", row, testResults);
					if (result != null)
						row.addCell(result.toString()).shown();
				}
				callMethod(object, "tearDown", row, testResults);
			}
		};
	}

	protected Object callMethod(Object object, String methodName, Row row, TestResults testResults) {
		try {
			Method method = object.getClass().getMethod(methodName, new Class[] {});
			logger.trace("Calling " + methodName + "() on " + object);
			return method.invoke(object, new Object[] {});
		} catch (NoSuchMethodException e) {
			//
		} catch (Exception e) {
			row.error(testResults, e);
		}
		return null;
	}
}

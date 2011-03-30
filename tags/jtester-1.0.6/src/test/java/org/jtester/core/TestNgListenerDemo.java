package org.jtester.core;

import org.jtester.testng.JTester;
import org.jtester.utility.JTesterLogger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.internal.IResultListener;

public class TestNgListenerDemo {
	public static void main(String[] args) {
		TestNgListenerDemo.runTests(new Class<?>[] { DemoTest.class });
	}

	public static void runTests(Class<?>[] clazzes) {
		TestNG tng = new TestNG();
		tng.addListener(new TestNgRunListener());

		tng.setTestClasses(clazzes);
		tng.run();
	}

	protected static class TestNgRunListener implements IResultListener {

		public void onStart(ITestContext context) {
		}

		public void onTestFailure(ITestResult itr) {
			JTesterLogger.info("fail " + getDisplayName(itr));
			itr.getThrowable().printStackTrace();
			JTesterLogger.info("=========================");
			trace();
		}

		public void onTestSkipped(ITestResult itr) {
			JTesterLogger.info("skip " + getDisplayName(itr));
		}

		public void onTestStart(ITestResult result) {
		}

		public void onTestSuccess(ITestResult itr) {
			String displayName = getDisplayName(itr);
			JTesterLogger.info("pass " + displayName);
		}

		public void onFinish(ITestContext context) {
			JTesterLogger.info("end testng test");
		}

		public void onTestFailedButWithinSuccessPercentage(ITestResult itr) {
			JTesterLogger.info("onTestFailedButWithinSuccessPercentage");
		}

		public void onConfigurationFailure(ITestResult itr) {
			JTesterLogger.info("configuraion failure " + getDisplayName(itr));
		}

		public void onConfigurationSkip(ITestResult itr) {
		}

		public void onConfigurationSuccess(ITestResult itr) {
			JTesterLogger.info(getDisplayName(itr));
		}
	}

	private final static String getDisplayName(ITestResult result) {
		String clazz = result.getTestClass().getName();
		String method = result.getMethod().getMethodName();
		return method + "(" + clazz + ")";
	}

	private final static void trace() {
		for (StackTraceElement tr : Thread.currentThread().getStackTrace()) {
			JTesterLogger.info(tr.toString());
		}
	}

	protected class DemoTest extends JTester {
		public DemoTest() {

		}

		@Test(expectedExceptions = { AssertionError.class })
		public void wantAssert_Failure() {
			want.fail("error message");
			// want.bool(true).isEqualTo(false);
			trace();
		}
	}
}

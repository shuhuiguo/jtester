package org.jtester.module.jmockit;

import mockit.external.hamcrest.Description;
import mockit.internal.expectations.RecordAndReplayExecution;
import mockit.internal.expectations.TestOnlyPhase;
import mockit.internal.state.TestRun;

import org.jtester.exception.JTesterException;

/**
 * 用于注册或获取当前线程下当前使用的org.jmock.Expectations实例<br>
 * 用于expectation参数断言调用org.jtester.hamcrest.iassert.common.intf.IAssert.wanted( )
 * 时,向当前的expectation实例注册期望发生的方法等
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings({ "rawtypes" })
public class ExpectationsUtil {
	private static ThreadLocal<Object> jes = new ThreadLocal<Object>();

	/**
	 * 注册当前线程下当前使用的org.jmock.Expectations实例
	 * 
	 * @param ex
	 */
	public static void register(org.jmock.Expectations ex) {
		jes.set(ex);
	}

	/**
	 * 注册当前线程下的mockit.Expectations实例
	 * 
	 * @param ex
	 */
	public static void register(mockit.Expectations ex) {
		jes.set(ex);
	}

	/**
	 * 注册当前线程下的mockit.Verifications实例
	 * 
	 * @param ex
	 */
	public static void register(mockit.Verifications ve) {
		jes.set(ve);
	}

	/**
	 * 获取当前线程下当前使用的org.jmock.Expectations实例
	 * 
	 * @param id
	 * @return
	 */
	public static org.jmock.Expectations getExpectations() {
		Object ex = jes.get();
		if (ex == null) {
			throw new JTesterException("no expectation has been defined by Thread");
		} else if (ex instanceof org.jmock.Expectations) {
			return (org.jmock.Expectations) ex;
		} else {
			throw new RuntimeException("the registed object class is:" + ex.getClass());
		}
	}

	public static boolean isJmockitExpectations() {
		Object ex = jes.get();
		return ex == null ? false : ex instanceof mockit.Expectations;
	}

	public static boolean isJmockitVerifications() {
		Object ex = jes.get();
		return ex == null ? false : ex instanceof mockit.Verifications;
	}

	/**
	 * 往mockit.Expectations中增加参数断言
	 * 
	 * @param matcher
	 */
	public static void addArgMatcher(org.hamcrest.Matcher matcher) {
		RecordAndReplayExecution instance = TestRun.getRecordAndReplayForRunningTest(false);

		if (instance == null) {
			return;
		}
		TestOnlyPhase currentPhase = instance.getCurrentTestOnlyPhase();
		if (currentPhase != null) {
			mockit.external.hamcrest.Matcher _matcher = convert(matcher);
			currentPhase.addArgMatcher(_matcher);
		}
	}

	/**
	 * 把官方的org.hamcrest.Matcher类转换为mockit.external.hamcrest.Matcher类
	 * 
	 * @param matcher
	 * @return
	 */
	protected static mockit.external.hamcrest.Matcher convert(final org.hamcrest.Matcher matcher) {
		return new mockit.external.hamcrest.BaseMatcher() {
			public boolean matches(Object item) {
				return matcher.matches(item);
			}

			public void describeTo(Description description) {
				org.hamcrest.Description message = new org.hamcrest.StringDescription();
				matcher.describeTo(message);
				description.appendText(message.toString());
			}
		};
	}
}

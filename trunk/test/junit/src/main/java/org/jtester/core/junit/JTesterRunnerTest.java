package org.jtester.core.junit;

import java.lang.reflect.Method;

import mockit.Mock;
import mockit.MockUp;

import org.jtester.IAssertion;
import org.jtester.core.TestListener;
import org.jtester.junit.JTesterRunner;
import org.jtester.matcher.string.StringMode;
import org.jtester.module.CoreModule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 整个类用于验证jTester的生命周期
 * 
 * @author darui.wudr
 * 
 */
@RunWith(JTesterRunner.class)
@SuppressWarnings("unused")
public class JTesterRunnerTest implements IAssertion {
	private static StringBuffer buff = new StringBuffer();

	@BeforeClass
	public static void mockJTesterRunner() {
		new MockUp<CoreModule>() {
			@Mock
			public TestListener getTestListener() {
				return new MockListener(buff);
			}
		};
		buff.append("@B");
		System.out.println("@BeforeClass");
	}

	@Test
	public void test1() {
		buff.append("TEST");
		System.out.println("test1");
	}

	@Test
	public void test2() {
		buff.append("TEST");
		System.out.println("test2");
	}

	static final String METHOD_BUFF_CONST = "bM bR TEST aR aM";

	@AfterClass
	public static void teardowClass() {
		buff.append("@A");
		System.out.println("@AfterClass");
		String result = buff.toString();
		want.string(result)
				.isEqualTo("@B bC" + METHOD_BUFF_CONST + METHOD_BUFF_CONST + "aC @A", StringMode.IgnoreSpace);
	}
}

class MockListener extends TestListener {
	static StringBuffer buff;

	public MockListener(StringBuffer buff) {
		MockListener.buff = buff;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void beforeClass(Class testClazz) {
		buff.append("bC");
		System.out.println("setupClass");
	}

	@Override
	public void beforeMethod(Object testObject, Method testMethod) {
		buff.append("bM");
		System.out.println("setupMethod");
	}

	@Override
	public void beforeRunning(Object testObject, Method testMethod) {
		buff.append("bR");
		System.out.println("beforeMethodRunning");
	}

	@Override
	public void afterRunned(Object testObject, Method testMethod, Throwable testThrowable) {
		buff.append("aR");
		System.out.println("afterMethodRunned");
	}

	@Override
	public void afterMethod(Object testObject, Method testMethod) {
		buff.append("aM");
		System.out.println("teardownMethod");
	}

	@Override
	public void afterClass(Object testObject) {
		buff.append("aC");
		System.out.println("teardownClass");
	}

	@Override
	protected String getName() {
		return "mock test listener";
	}
}

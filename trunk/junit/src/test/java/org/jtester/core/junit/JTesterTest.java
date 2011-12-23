package org.jtester.core.junit;

import mockit.Mock;

import org.jtester.matcher.string.StringMode;
import org.jtester.module.TestListener;
import org.jtester.module.core.CoreModule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 整个类用于验证jTester的生命周期
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings("unused")
public class JTesterTest extends JTester {
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
		want.string(buff.toString()).isEqualTo("@B bC" + METHOD_BUFF_CONST + METHOD_BUFF_CONST + "aC @A",
				StringMode.IgnoreSpace);
	}
}

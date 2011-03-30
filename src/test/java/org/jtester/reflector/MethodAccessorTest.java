package org.jtester.reflector;

import org.jtester.reflector.model.TestObject;
import org.jtester.testng.JTester;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings({ "rawtypes" })
@Test(groups = "jtester")
public class MethodAccessorTest extends JTester {

	private MethodAccessor<Integer> getPrivate;

	private MethodAccessor<Integer> setPrivate;

	@BeforeMethod
	public void setUp() throws Exception {
		TestObject test = new TestObject();
		getPrivate = new MethodAccessor<Integer>(test, "getPrivate", new Class[0]);
		setPrivate = new MethodAccessor<Integer>(test, "setPrivate", new Class[] { int.class });
	}

	@AfterMethod
	public void tearDown() throws Exception {
		getPrivate = null;
		setPrivate = null;
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testMethodAccessor1() {
		new MethodAccessor<Void>(new Object(), "missing");
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testMethodAccessor2() {
		new MethodAccessor<Void>(new TestObject(), "missing");
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void testMethodAccessor3() {
		new MethodAccessor(null, "missing");
	}

	/**
	 * Test method for
	 * {@link com.j2speed.accessor.AbstractMethodAccessor#invokeBase(java.lang.Object[])}
	 * .
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testInvoke() {
		int expected = 26071973;
		want.number(getPrivate.invoke().intValue()).isEqualTo(expected);

		int newValue = 26072007;
		want.number(setPrivate.invoke(Integer.valueOf(newValue)).intValue()).isEqualTo(expected);
		want.number(getPrivate.invoke().intValue()).isEqualTo(newValue);
	}
}

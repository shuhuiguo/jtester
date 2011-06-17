package org.jtester.bytecode.reflector.impl;

import org.jtester.bytecode.reflector.MethodAccessor;
import org.jtester.bytecode.reflector.model.TestException;
import org.jtester.bytecode.reflector.model.TestObject;
import org.jtester.testng.JTester;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class VoidMethodAccessorTest extends JTester {

	private MethodAccessor<Void> throwingMethod;

	@BeforeMethod
	public void setUp() {
		throwingMethod = new MethodAccessorImpl<Void>(TestObject.class, "throwingMethod");
	}

	@AfterMethod
	public void tearDown() {
		throwingMethod = null;
	}

	/**
	 * Test method for
	 * {@link com.j2speed.accessor.AbstractMethodAccessor#invokeBase(java.lang.Object[])}
	 * .
	 */
	@Test
	public void testInvoke() {
		Object target = new TestObject();
		new MethodAccessorImpl<Void>(target, "nonThrowingMethod").invoke(target, new Object[0]);
		try {
			throwingMethod.invoke(target, new Object[0]);
			want.fail("Expected test exception");
		} catch (RuntimeException e) {
			Throwable th = e.getCause();
			want.object(th).clazIs(TestException.class);
		}
		try {
			throwingMethod.invoke(target, new Object[] { "wrong" });
			want.fail("Expected test exception");
		} catch (Throwable e) {
			want.object(e).clazIs(IllegalArgumentException.class);
		}
	}
}
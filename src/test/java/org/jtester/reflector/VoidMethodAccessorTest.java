package org.jtester.reflector;

import org.jtester.reflector.model.TestException;
import org.jtester.reflector.model.TestObject;
import org.jtester.testng.JTester;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Test(groups = "jtester")
public class VoidMethodAccessorTest extends JTester {

	private VoidMethodAccessor throwingMethod;

	@BeforeMethod
	public void setUp() {
		throwingMethod = new VoidMethodAccessor("throwingMethod", new TestObject());
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
		new VoidMethodAccessor("nonThrowingMethod", new TestObject()).invoke();
		try {
			throwingMethod.invoke();
			want.fail("Expected test exception");
		} catch (RuntimeException e) {
			Throwable th = e.getCause();
			want.object(th).clazIs(TestException.class);
		}
		try {
			throwingMethod.invoke("wrong");
			want.fail("Expected test exception");
		} catch (Throwable e) {
			want.object(e).clazIs(IllegalArgumentException.class);
		}
	}
}
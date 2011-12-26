package org.jtester.reflector;

import org.jtester.IAssertion;
import org.jtester.reflector.model.YourTestedException;
import org.jtester.reflector.model.YourTestedObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VoidMethodAccessorTest implements IAssertion {

	private MethodAccessor<Void> throwingMethod;

	@Before
	public void setUp() {
		throwingMethod = new MethodAccessor<Void>(YourTestedObject.class, "throwingMethod");
	}

	@After
	public void tearDown() {
		throwingMethod = null;
	}

	/**
	 * Test method for
	 * {@link com.j2speed.accessor.AbstractMethodAccessor#invokeBase(java.lang.Object[])}
	 * .
	 */
	@Test
	public void testInvoke() throws Exception {
		Object target = new YourTestedObject();
		new MethodAccessor<Void>(target, "nonThrowingMethod").invoke(target, new Object[0]);
		try {
			throwingMethod.invoke(target, new Object[0]);
			want.fail("Expected test exception");
		} catch (Exception e) {
			want.object(e).clazIs(YourTestedException.class);
		}
		try {
			throwingMethod.invoke(target, new Object[] { "wrong" });
			want.fail("Expected test exception");
		} catch (Throwable e) {
			want.object(e).clazIs(IllegalArgumentException.class);
		}
	}
}
package org.jtester.reflector;

import org.jtester.IAssertion;
import org.jtester.reflector.model.YourTestedObject;
import org.junit.Before;
import org.junit.Test;

public class StaticFieldAccessorTest implements IAssertion {

	private FieldAccessor<Integer> aStaticPrivate;

	@Before
	public void setUp() throws Exception {
		aStaticPrivate = new FieldAccessor<Integer>(YourTestedObject.class, "aStaticPrivate");
	}

	@Test(expected = RuntimeException.class)
	public void testStaticFieldAccessor() {
		new FieldAccessor<Integer>(Object.class, "missing");
	}

	@Test(expected = NullPointerException.class)
	public void testStaticFieldAccessor2() {
		new FieldAccessor<Integer>(null, "missing");
	}

	@Test(expected = RuntimeException.class)
	public void testStaticFieldAccessor3() {
		FieldAccessor<Integer> accessor = new FieldAccessor<Integer>(YourTestedObject.class, "aPrivate");
		accessor.setStatic(1);
		want.fail("The field should not be static: StaticFieldAccessor only accepts static fields");
	}

	@Test
	// (expected = RuntimeException.class)
	public void testStaticFieldAccessor4() {
		FieldAccessor<Integer> accessor = new FieldAccessor<Integer>(YourTestedObject.class, "aSuperStaticPrivate");
		accessor.setStatic(1);
		// want.fail("The field should not be declared in the specified class: StaticFieldAccessor only accepts static fields declared in the specified class");
	}

	/**
	 * Test method for {@link com.j2speed.accessor.FieldAccessor#get()}.
	 */
	@Test
	public void testGet() {
		reflector.setStaticField(YourTestedObject.class, "aStaticPrivate", 27022009);
		want.number(aStaticPrivate.getStatic().intValue()).isEqualTo(27022009);
	}

	/**
	 * Test method for
	 * {@link com.j2speed.accessor.FieldAccessor#set(java.lang.Object)}.
	 */
	@Test
	public void testSet() {
		int newValue = 26072007;

		aStaticPrivate.setStatic(Integer.valueOf(newValue));
		want.number(aStaticPrivate.getStatic().intValue()).isEqualTo(newValue);
		try {
			aStaticPrivate.setStatic(null);
			want.fail();
		} catch (Throwable e) {
			String info = e.getMessage();
			want.string(info).contains("to set field").contains("into target").contains("error");
		}
	}
}

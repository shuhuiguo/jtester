package org.jtester.bytecode.reflector.impl;

import org.jtester.bytecode.reflector.FieldAccessor;
import org.jtester.bytecode.reflector.model.TestObject;
import org.jtester.testng.JTester;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class FieldAccessorImplTest extends JTester {

	private FieldAccessorImpl<Integer> aPrivate;

	@BeforeMethod
	public void setUp() throws Exception {
		aPrivate = new FieldAccessorImpl<Integer>(TestObject.class, "aPrivate");
	}

	@AfterMethod
	public void tearDown() throws Exception {
		aPrivate = null;
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testFieldAccessor1() {
		new FieldAccessorImpl<Integer>(Object.class, "missing");
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void testFieldAccessor2() {
		new FieldAccessorImpl<Integer>("missing", null);
	}

	@Test
	public void testFieldAccessor3() {
		FieldAccessor<Integer> accessor = new FieldAccessorImpl<Integer>(TestObject.class, "aSuperStaticPrivate");
		accessor.setStatic(1);
	}

	/**
	 * Test method for {@link com.j2speed.accessor.FieldAccessor#get()}.
	 */
	@Test
	public void testGet() {
		TestObject toTest = new TestObject();
		int actual = aPrivate.get(toTest).intValue();
		want.number(actual).isEqualTo(26071973);
	}

	/**
	 * Test method for
	 * {@link com.j2speed.accessor.FieldAccessor#set(java.lang.Object)}.
	 */
	@Test
	public void testSet() {
		TestObject toTest = new TestObject();
		int newValue = 26072007;
		aPrivate.set(toTest, Integer.valueOf(newValue));
		int actual = aPrivate.get(toTest).intValue();
		want.number(actual).isEqualTo(newValue);
		try {
			aPrivate.set(toTest, null);
			want.fail();
		} catch (Throwable e) {
			String info = e.getMessage();
			want.string(info).contains("to set field").contains("into target").contains("error");
		}
	}
}

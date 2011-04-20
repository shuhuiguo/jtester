package org.jtester.reflector;

import org.jtester.reflector.model.TestObject;
import org.jtester.testng.JTester;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Test(groups = "jtester")
public class FieldAccessorTest extends JTester {

	private FieldAccessor<TestObject, Integer> aPrivate;

	@BeforeMethod
	public void setUp() throws Exception {
		aPrivate = new FieldAccessor<TestObject, Integer>("aPrivate", TestObject.class);
	}

	@AfterMethod
	public void tearDown() throws Exception {
		aPrivate = null;
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testFieldAccessor1() {
		new FieldAccessor<Object, Void>("missing", Object.class);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void testFieldAccessor2() {
		new FieldAccessor<Object, Void>("missing", null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testFieldAccessor3() {
		new FieldAccessor<TestObject, Void>("aSuperStaticPrivate", TestObject.class);
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
		} catch (Throwable e) {
			want.object(e).clazIs(IllegalArgumentException.class);
		}
	}
}

package org.jtester.reflector;

import org.jtester.reflector.model.TestObject;
import org.jtester.testng.JTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Test(groups = "jtester")
public class StaticFieldAccessorTest extends JTester {

	private StaticFieldAccessor<TestObject, Integer> aStaticPrivate;

	@BeforeMethod
	public void setUp() throws Exception {
		aStaticPrivate = new StaticFieldAccessor<TestObject, Integer>("aStaticPrivate", TestObject.class);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testStaticFieldAccessor() {
		new StaticFieldAccessor<Object, Void>("missing", Object.class);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void testStaticFieldAccessor2() {
		new StaticFieldAccessor<Object, Void>("missing", null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testStaticFieldAccessor3() {
		new StaticFieldAccessor<TestObject, Void>("aPrivate", TestObject.class);
		want.fail("The field should not be static: StaticFieldAccessor only accepts static fields");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testStaticFieldAccessor4() {
		new StaticFieldAccessor<TestObject, Void>("aSuperStaticPrivate", TestObject.class);
		want.fail("The field should not be declared in the specified class: StaticFieldAccessor only accepts static fields declared in the specified class");
	}

	/**
	 * Test method for {@link com.j2speed.accessor.FieldAccessor#get()}.
	 */
	@Test
	public void testGet() {
		want.number(aStaticPrivate.get().intValue()).isEqualTo(27022008);
	}

	/**
	 * Test method for
	 * {@link com.j2speed.accessor.FieldAccessor#set(java.lang.Object)}.
	 */
	@Test(dependsOnMethods = "testGet")
	public void testSet() {
		int newValue = 26072007;

		aStaticPrivate.set(Integer.valueOf(newValue));
		want.number(aStaticPrivate.get().intValue()).isEqualTo(newValue);
		try {
			aStaticPrivate.set(null);
		} catch (Throwable e) {
			want.object(e).clazIs(IllegalArgumentException.class);
		}
	}
}

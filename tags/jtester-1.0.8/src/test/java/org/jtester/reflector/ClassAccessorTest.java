package org.jtester.reflector;

import java.util.Date;

import org.jtester.reflector.ClassAccessorTest.AccessPackageProtectedObject.AccessInner;
import org.jtester.reflector.model.TestObject;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class ClassAccessorTest extends JTester {

	@Test(expectedExceptions = RuntimeException.class)
	public void testCreateAccessorStringClassNotFound() {
		ClassAccessor.create("ext.jtester.com.j2speed.accessor.ThisClassDoesNotExist");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testCreateAccessorForInterface() {
		ClassAccessor.create(AccessPackageProtectedObject.class.getName());
	}

	@Test
	public void testCreateAccessorString() {
		ClassAccessor accessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject");
		String expectedName = "Alex";
		AccessPackageProtectedObject object = accessor.constructor(String.class).newProxy(
				AccessPackageProtectedObject.class, expectedName);
		want.bool(object.isItYou()).isEqualTo(true);
		want.object(object.getName()).same(expectedName);
	}

	@Test
	public void testForInnerStringDotNotation() {
		ClassAccessor innerAccessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject")
				.forInner("Inner.Nested");
		want.object(innerAccessor).notNull();
	}

	@Test
	public void testForInnerString() {
		ClassAccessor accessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject");
		Object object = accessor.constructor(String.class).newInstance("Alex");
		ClassAccessor innerAccessor = accessor.forInner("Inner");
		Date expectedDate = new Date();
		AccessInner inner = innerAccessor.constructor(object, Date.class).newProxy(AccessInner.class, expectedDate);
		want.date(inner.getDate()).same(expectedDate);
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void testInnerConstructorWithoutEnclosing() {
		ClassAccessor accessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject");
		accessor.forInner("Inner").constructor(Date.class);
	}

	@Test
	public void testCreateAccessorStringClassLoader() {
		ClassAccessor accessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject", getClass()
				.getClassLoader());
		String expectedName = "Alex";
		AccessPackageProtectedObject object = accessor.constructor(String.class).newProxy(
				AccessPackageProtectedObject.class, expectedName);
		want.bool(object.isItYou()).isEqualTo(true);
		want.string(object.getName()).isEqualTo(expectedName);
	}

	@Test
	public void testCreateAccessorClassOfQString() {
		ClassAccessor accessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject");
		Object object = accessor.constructor(String.class).newInstance("Alex");
		ClassAccessor innerAccessor = ClassAccessor.create(object.getClass(), "Inner");
		Date expectedDate = new Date();
		AccessInner inner = innerAccessor.constructor(object, Date.class).newProxy(AccessInner.class, expectedDate);
		want.date(inner.getDate()).isEqualTo(expectedDate);
	}

	@Test
	public void testCreateAccessorClassOfQStringDotNotation() {
		ClassAccessor accessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject");
		ClassAccessor innerAccessor = ClassAccessor.create(accessor.getAccessedClass(), "Inner.Nested");
		want.object(innerAccessor).notNull();
	}

	@Test
	public void testCreateAccessorClassOfQStringClassLoader() {
		ClassAccessor accessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject", getClass()
				.getClassLoader());
		Object object = accessor.constructor(String.class).newInstance("Alex");
		ClassAccessor innerAccessor = ClassAccessor.create(object.getClass(), "Inner", getClass().getClassLoader());
		Date expectedDate = new Date();
		AccessInner inner = innerAccessor.constructor(object, Date.class).newProxy(AccessInner.class, expectedDate);
		want.date(inner.getDate()).same(expectedDate);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testConstructorClassOfQArrayNotExisting() {
		ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject").constructor(Date.class);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testConstructorClassOfQArrayWrongParameterValue() {
		ClassAccessor accessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject");
		accessor.constructor(String.class).newInstance(new Date());
	}

	@Test
	public void testConstructorClassOfQArray() {
		ClassAccessor accessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject");
		want.object(accessor.constructor(String.class)).notNull();
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testConstructorObjectClassOfQArrayNotExisting() {
		ClassAccessor accessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject", getClass()
				.getClassLoader());
		Object object = accessor.constructor(String.class).newInstance("Alex");
		ClassAccessor innerAccessor = ClassAccessor.create(object.getClass(), "Inner", getClass().getClassLoader());
		innerAccessor.constructor(object, String.class);
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void testConstructorObjectClassOfQArrayNotInner() {
		ClassAccessor accessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject");
		accessor.constructor(new Object(), String.class);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void testConstructorObjectClassOfQArrayNullEnclosing() {
		ClassAccessor accessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject");
		ClassAccessor innerAccessor = accessor.forInner("Inner");
		innerAccessor.constructor((Object) null, Date.class);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testConstructorObjectClassOfQArrayMisnatchingEnclosing() {
		ClassAccessor accessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject");
		ClassAccessor innerAccessor = accessor.forInner("Inner");
		innerAccessor.constructor(new Object(), Date.class);
	}

	@Test(expectedExceptions = Throwable.class)
	public void testConstructorObjectClassOfQArrayThrowsThrowable() throws Throwable {
		ClassAccessor innerAccessor = ClassAccessor.create(TestObject.class, "InnerThrowsThrowable");
		try {
			innerAccessor.constructor(new TestObject()).newInstance();
		} catch (RuntimeException e) {
			throw e.getCause();
		}
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testConstructorObjectClassOfQArrayThrowsRuntime() {
		ClassAccessor innerAccessor = ClassAccessor.create(TestObject.class, "InnerThrowsRuntimeException");
		innerAccessor.constructor(new TestObject()).newInstance();
	}

	@Test(expectedExceptions = Error.class)
	public void testConstructorObjectClassOfQArrayThrowsError() {
		ClassAccessor innerAccessor = ClassAccessor.create(TestObject.class, "InnerThrowsError");
		innerAccessor.constructor(new TestObject()).newInstance();
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testConstructorObjectClassOfQArrayWrongParameterValue() {
		ClassAccessor accessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject");
		Object object = accessor.constructor(String.class).newInstance("Alex");
		ClassAccessor innerAccessor = accessor.forInner("Inner");
		innerAccessor.constructor(object, Date.class).newInstance("Alex");
	}

	@Test
	public void testConstructorObjectClassOfQArray() {
		ClassAccessor accessor = ClassAccessor.create("org.jtester.reflector.model.PackagePrivateObject");
		Object object = accessor.constructor(String.class).newInstance("Alex");
		ClassAccessor innerAccessor = accessor.forInner("Inner");
		want.object(innerAccessor.constructor(object, Date.class)).notNull();
	}

	interface AccessPackageProtectedObject {

		boolean isItYou();

		String getName();

		interface AccessInner {

			Date getDate();
		}
	}
}

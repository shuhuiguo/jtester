package org.jtester.reflector;

import java.lang.reflect.Field;

import org.jtester.exception.NoSuchFieldRuntimeException;

/**
 * Base implementation to allow access to a private field of a class.
 * 
 * @param <T>
 *            the type of the target type which hierarchy contains the field.
 * @param <V>
 *            the type of the field.
 */
@SuppressWarnings({ "rawtypes" })
abstract class BaseFieldAccessor<T, V> {

	/**
	 * The field to access.
	 */
	protected final Field field;

	/**
	 * Constructor.
	 * 
	 * @param fieldName
	 *            the field name
	 * @param type
	 *            the class from which to start searching the field
	 */
	public BaseFieldAccessor(String fieldName, Class<? extends T> type) {
		if (fieldName == null || type == null)
			throw new NullPointerException();
		try {
			field = getField(type, fieldName);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	/**
	 * jTester method to search for a field starting from the specified class
	 * and going up the hierarchy until the field is found or the {@link Object}
	 * class is reached.
	 * 
	 * @param cls
	 *            the class from which to start
	 * @param name
	 *            the field name
	 * @return the {@link Field} found
	 * @throws NoSuchFieldException
	 *             if the field cannot be found within the specified class
	 *             hierarchy.
	 */

	public static Field getField(Class cls, String name) {
		while (cls != Object.class) {
			try {
				Field field = cls.getDeclaredField(name);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {
				cls = cls.getSuperclass();
			}
		}
		throw new NoSuchFieldRuntimeException("No such field: " + name);
	}

	/**
	 * 返回字段的类型
	 * 
	 * @return
	 */
	public Class getFieldType() {
		return this.field.getType();
	}
}

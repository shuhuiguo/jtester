package org.jtester.reflector;

import java.lang.reflect.Modifier;

/**
 * Allow to access a private static field of a class.
 * 
 * 
 * @param <T>
 *            the type of the class containing the field.
 * @param <V>
 *            the type of the field.
 */
public class StaticFieldAccessor<T, V> extends BaseFieldAccessor<T, V> {

	/**
	 * Constructor.
	 * 
	 * @param fieldName
	 *            the field name
	 * @param type
	 *            the target from which to get the value
	 */
	public StaticFieldAccessor(String fieldName, Class<? extends T> type) {
		super(fieldName, type);
		if (!Modifier.isStatic(field.getModifiers())) {
			throw new IllegalArgumentException("Field " + fieldName + " is not static");
		}
		if (!field.getDeclaringClass().equals(type)) {
			throw new IllegalArgumentException("Field " + fieldName + " is not declared by the class " + type);
		}
	}

	/**
	 * Gets the value of the field.
	 * 
	 * @return the current field value
	 */
	@SuppressWarnings("unchecked")
	public final V get() {
		try {
			return (V) field.get(null);
		} catch (RuntimeException e) {
			throw e;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sets the value for the field.
	 * 
	 * @param value
	 */
	public final void set(V value) {
		try {
			field.set(null, value);
		} catch (RuntimeException e) {
			throw e;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}

package org.jtester.bytecode.reflector;

public interface FieldAccessor<T> {
	/**
	 * Gets the value of the field.
	 * 
	 * @param <T>
	 * @param <V>
	 * @param target
	 * @return
	 */
	T get(Object target);

	/**
	 * gets the value of static field
	 * 
	 * @param <V>
	 * @return
	 */
	T getStatic();

	/**
	 * Sets the value for the field.
	 * 
	 * @param target
	 * @param value
	 */
	void set(Object target, T value);

	/**
	 * sets the value for the static field.
	 * 
	 * @param value
	 */
	void setStatic(T value);

	/**
	 * 返回字段的类型
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Class getFieldType();
}

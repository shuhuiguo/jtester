package org.jtester.bytecode.reflector;

import java.lang.reflect.Method;

public interface MethodAccessor<T> {
	/**
	 * get the method of accessor
	 * 
	 * @return
	 */
	Method getMethod();

	/**
	 * Invoke the target object's method using the specified parameters.
	 * 
	 * @param target
	 *            target object
	 * @param args
	 *            method arguments
	 * @return
	 */
	T invoke(Object target, Object[] args);

	/**
	 * Invoke the static method using the specified parameters.
	 * 
	 * @param args
	 *            method arguments
	 * @return
	 */
	T invokeStatic(Object[] args);
}

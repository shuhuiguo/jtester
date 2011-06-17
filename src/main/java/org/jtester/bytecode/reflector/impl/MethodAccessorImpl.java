package org.jtester.bytecode.reflector.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jtester.bytecode.reflector.MethodAccessor;
import org.jtester.bytecode.reflector.helper.MethodHelper;
import org.jtester.exception.JTesterException;

/**
 * An accessor to a method that returns a value.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MethodAccessorImpl<T> implements MethodAccessor<T> {
	private final Method method;
	private final Class targetClaz;

	// private final String methodName;

	public MethodAccessorImpl(Class targetClaz, String methodName, Class... parametersType) {
		this.targetClaz = targetClaz;
		this.method = MethodHelper.getMethod(targetClaz, methodName, parametersType);
		// this.methodName = methodName;
	}

	/**
	 * 
	 * @param targetObj
	 * @param targetClazz
	 * @param methodName
	 * @param parametersType
	 */
	public MethodAccessorImpl(Object target, String methodName, Class... parametersType) {
		this.targetClaz = target.getClass();
		this.method = MethodHelper.getMethod(targetClaz, methodName, parametersType);
		// this.methodName = methodName;
	}

	public MethodAccessorImpl(Method method) {
		this.method = method;
		// this.methodName = method.getName();
		this.targetClaz = method.getDeclaringClass();
	}

	public Method getMethod() {
		return this.method;
	}

	public T invoke(Object target, Object[] args) {
		boolean isAccessible = this.method.isAccessible();
		try {
			return (T) method.invoke(target, args);
		} catch (InvocationTargetException e) {
			Throwable e1 = e.getTargetException();
			if (e1 instanceof RuntimeException) {
				throw (RuntimeException) e1;
			} else {
				throw new RuntimeException(e1);
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			this.method.setAccessible(isAccessible);
		}
	}

	public T invokeStatic(Object[] args) {
		return invoke(null, args);
	}

	/**
	 * Invokes the given method with the given parameters on the given target
	 * object
	 * 
	 * @param target
	 *            The object containing the method, not null
	 * @param method
	 *            The method, not null
	 * @param arguments
	 *            The method arguments
	 * @return The result of the invocation, null if void
	 * @throws JTesterException
	 *             if the method could not be invoked
	 */
	public static <T> T invokeMethod(Object target, Method method, Object... arguments) {
		boolean isAccessible = method.isAccessible();
		try {
			method.setAccessible(true);
			return (T) method.invoke(target, arguments);
		} catch (ClassCastException e) {
			throw new JTesterException("Unable to invoke method. Unexpected return type " + method, e);
		} catch (IllegalArgumentException e) {
			throw new JTesterException("Error while invoking method " + method, e);
		} catch (IllegalAccessException e) {
			throw new JTesterException("Error while invoking method " + method, e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Unable to invoke method: " + target.getClass().getSimpleName() + "."
					+ method.getName() + ". Method has thrown an exception.", e);
		} finally {
			method.setAccessible(isAccessible);
		}
	}

}

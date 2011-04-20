package org.jtester.reflector;

import static java.lang.reflect.Modifier.isStatic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jtester.exception.JTesterException;
import org.jtester.exception.JTesterReflectionException;
import org.jtester.reflector.helper.ClazzHelper;

/**
 * An accessor to a method that returns a value.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MethodAccessor<T> extends AbstractMethodAccessor<T> {

	public MethodAccessor(Object target, String methodName, Class... parametersType) {
		super(methodName, target, parametersType);
	}

	/**
	 * 
	 * @param targetObj
	 * @param targetClazz
	 * @param methodName
	 * @param parametersType
	 */
	public MethodAccessor(Object targetObj, Class targetClazz, String methodName, Class... parametersType) {
		super(targetObj, targetClazz, methodName, parametersType);
	}

	MethodAccessor(Object target, Method method) {
		super(target, method);
	}

	/**
	 * Invoke the method using the specified parameters.
	 * 
	 * @param args
	 *            the arguments for the method.
	 * @return the result of the specified type.
	 */
	public T invoke(Object... args) {
		return super.invokeBase(args);
	}

	/**
	 * 根据方法的名称和参数个数查找方法访问器，如果有多于1个同名且参数个数一样的方法，那么抛出异常
	 * 
	 * @param target
	 * @param methodName
	 * @param args
	 * @return
	 */
	public static <T> MethodAccessor<T> findMethodByArgCount(Object target, Class targetClazz, String methodName,
			int args) {
		List<Method> methods = getMethod(targetClazz, methodName, args);
		if (methods.size() == 0) {
			throw new JTesterReflectionException("No such method: " + methodName + ",parameter count:" + args);
		}
		if (methods.size() > 1) {
			throw new JTesterReflectionException("More then one method: " + methodName + ",parameter count:" + args);
		}
		Method method = methods.get(0);
		return new MethodAccessor<T>(target, method);
	}

	/**
	 * 根据方法的名称和参数个数查找方法访问器，如果有多于1个同名且参数个数一样的方法，那么抛出异常
	 * 
	 * @param <T>
	 * @param target
	 * @param methodName
	 * @param args
	 * @return
	 */
	public static <T> MethodAccessor<T> findMethodByArgCount(Object target, String methodName, int args) {
		return findMethodByArgCount(target, target.getClass(), methodName, args);
	}

	/**
	 * 调用类为clazz,名称为method的方法
	 * 
	 * @param methodName
	 * @param paras
	 * @return
	 */
	public static <T> T invoke(Class clazz, Object target, String method, Object... paras) {
		List<Class> paraClazz = new ArrayList<Class>();
		if (paras != null) {
			for (Object para : paras) {
				paraClazz.add(para == null ? null : para.getClass());
			}
		}
		MethodAccessor accessor = new MethodAccessor(target, clazz, method, paraClazz.toArray(new Class[0]));
		return (T) accessor.invoke(paras);
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
		}
	}

	/**
	 * Returns all declared setter methods of fields of the given class that are
	 * assignable from the given type.
	 * 
	 * @param clazz
	 *            The class to get setters from, not null
	 * @param type
	 *            The type, not null
	 * @param isStatic
	 *            True if static setters are to be returned, false for
	 *            non-static
	 * @return A list of Methods, empty list if none found
	 */
	public static Set<Method> getSettersAssignableFrom(Class clazz, Type type, boolean isStatic) {
		Set<Method> settersAssignableFrom = new HashSet<Method>();

		Set<Method> allMethods = getAllMethods(clazz);
		for (Method method : allMethods) {
			if (isSetter(method) && ClazzHelper.isAssignable(type, method.getGenericParameterTypes()[0])
					&& (isStatic == isStatic(method.getModifiers()))) {
				settersAssignableFrom.add(method);
			}
		}
		return settersAssignableFrom;
	}

	/**
	 * Gets all methods of the given class and all its super-classes.
	 * 
	 * @param clazz
	 *            The class
	 * @return The methods, not null
	 */
	public static Set<Method> getAllMethods(Class clazz) {
		Set<Method> result = new HashSet<Method>();
		if (clazz == null || clazz.equals(Object.class)) {
			return result;
		}

		// add all methods of this class
		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (Method declaredMethod : declaredMethods) {
			if (declaredMethod.isSynthetic() || declaredMethod.isBridge()) {
				// skip methods that were added by the compiler
				continue;
			}
			result.add(declaredMethod);
		}
		// add all methods of the super-classes
		result.addAll(getAllMethods(clazz.getSuperclass()));
		return result;
	}

	/**
	 * Returns the setter methods in the given class that have an argument with
	 * the exact given type. The class's superclasses are also investigated.
	 * 
	 * @param clazz
	 *            The class to get the setter from, not null
	 * @param type
	 *            The type, not null
	 * @param isStatic
	 *            True if static setters are to be returned, false for
	 *            non-static
	 * @return All setters for an object of the given type
	 */
	public static Set<Method> getSettersOfType(Class clazz, Type type) {
		Set<Method> settersOfType = new HashSet<Method>();
		Set<Method> allMethods = getAllMethods(clazz);
		for (Method method : allMethods) {
			if (isSetter(method) && method.getGenericParameterTypes()[0].equals(type)) {
				settersOfType.add(method);
			}
		}
		return settersOfType;
	}

	/**
	 * 判断方法是否是setter方法<br>
	 * For each method, check if it can be a setter for an object of the given
	 * type. A setter is a method with the following properties:
	 * <ul>
	 * <li>Method name is > 3 characters long and starts with set</li>
	 * <li>The fourth character is in uppercase</li>
	 * <li>The method has one parameter, with the type of the property to set</li>
	 * </ul>
	 * 
	 * @param method
	 *            The method to check, not null
	 * @return True if the given method is a setter, false otherwise
	 */
	public static boolean isSetter(Method method) {
		String methodName = method.getName();
		if (methodName.length() > 3 && methodName.startsWith("set") && method.getParameterTypes().length == 1) {
			String fourthLetter = methodName.substring(3, 4);
			if (fourthLetter.toUpperCase().equals(fourthLetter)) {
				return true;
			}
		}
		return false;
	}
}

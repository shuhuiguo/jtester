package org.jtester.reflector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A generator of proxies to create instances of objects with public access on
 * an object's private methods.
 * <p>
 * For example, given the class:
 * 
 * <pre>
 * public class TestObject {
 * 
 * 	private int aPrivate = 26071973;
 * 
 * 	private void throwingMethod() throws TestException {
 * 		throw new TestException(&quot;from throwingMethod&quot;);
 * 	}
 * }
 * </pre>
 * 
 * If you wanted to access or test directly the private methods, you need to
 * define an interface like:
 * 
 * <pre>
 * interface TestObjectAccess {
 * 
 * 	// Using JavaBean matching style to map a field to a standard accessor
 * 	// method.
 * 	public int getAPrivate();
 * 
 * 	public void throwingMethod() throws TestException;
 * }
 * </pre>
 * 
 * then, to generate the accessor proxy:
 * 
 * <pre>
 * TestObject test = new TestObject();
 * 
 * TestObjectAccess access = ProxyMethodsAccessor.createAccessor(TestObjectAccess.class, test);
 * </pre>
 * 
 * </p>
 * <p>
 * Note that, if the object declare a method with throwing exceptions, you can
 * declare the same exceptions in the interface method so that you can test for
 * them as well.
 * </p>
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class ProxyAccessor {

	private ProxyAccessor() {
		// static factory
	}

	/**
	 * Creates a proxy that give access to the methods in the target that match
	 * the ones defined in the provided interface.
	 * <p>
	 * Note that the target must match all methods in the provided interface,
	 * regardless the access level, they can even be private that is.<br>
	 * Note also that to match, the return type can be a superclass of the
	 * target method return type.
	 * </p>
	 * <p>
	 * If you need to access static methods, the target must be the Class
	 * instance in which those methods are defined.
	 * </p>
	 * 
	 * @param <T>
	 *            The return type for this method. T must be an interface.
	 * @param type
	 *            The Class object of the interface.
	 * @param target
	 *            The object that matches the interface's methods.
	 * @return A proxy instance that implements T
	 * @throws NoSuchMethodException
	 *             if the target doesn't match one of the interface's methods.
	 * @throws NoSuchFieldException
	 */

	public static <T> T createAccessor(Class<T> type, Object target) {
		return createAccessor(type, target, null);
	}

	/**
	 * Creates a proxy that give access to the methods in the target that match
	 * the ones defined in the provided interface.
	 * <p>
	 * This variant allows to map methods in the interface to methods in the
	 * target instance so that they don't have to match natively. For example,
	 * if the interface defines a method getSomething() but the target instance
	 * defines something() instead, we could provide a Map that contains the
	 * key,value pair <"getSomething","something"> and the accessor would map
	 * those two methods.
	 * </p>
	 * <p>
	 * If you need to access only static methods, the target must be the Class
	 * instance in which those methods are defined.
	 * </p>
	 * 
	 * @param <T>
	 *            The return type for this method. T must be an interface.
	 * @param type
	 *            The Class object of the interface.
	 * @param target
	 *            The object that matches the interface's methods.
	 * @param namesMapping
	 *            a mapping of names between a method in the interface to the
	 *            actual method name in the target instance or between a
	 *            standard JavaBean field name and the actual field name in the
	 *            target instance.
	 * @return A proxy instance that implements T
	 * @throws NoSuchMethodException
	 *             if the target doesn't match one of the interface's methods.
	 * @throws NoSuchFieldException
	 */
	public static <T> T createAccessor(Class<T> type, Object target, Map<String, String> namesMapping) {
		if (namesMapping == null) {
			namesMapping = Collections.emptyMap();
		}
		checkInterface(type);
		Map<Method, Invoker> methodsMap = new HashMap<Method, Invoker>();
		Class targetClass = target instanceof Class ? (Class) target : target.getClass();
		// We require an interface so all the methods are public
		for (Method method : type.getMethods()) {
			Invoker invoker = getInvoker(method, targetClass, namesMapping);
			methodsMap.put(method, invoker);
		}
		return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[] { type }, new DynamicInvocationHandler(
				target, methodsMap));
	}

	/**
	 * Tries to find a suitable {@link Invoker} for the specified {@link Method}
	 * in the target class.
	 * 
	 * @param method
	 * @param targetClass
	 * @param namesMapping
	 * @return
	 */

	private static Invoker getInvoker(Method method, Class targetClass, Map<String, String> namesMapping) {
		String name = namesMapping.get(method.getName());
		name = name == null ? method.getName() : name;
		try {
			Method targetMethod = AbstractMethodAccessor.getMethod(targetClass, name, method.getParameterTypes());
			// Check that the return type of the interface method is compatible
			// with
			// the found method.
			if (targetMethod.getReturnType().isAssignableFrom(method.getReturnType())) {
				return new MethodInvoker(targetMethod);
			}
			// It is not compatible, so let's try a JavaBean style mapping
			return getJavaBeanStyleInvoker(name, method, targetClass, namesMapping);
		} catch (RuntimeException e) {
			return getJavaBeanStyleInvoker(name, method, targetClass, namesMapping);
		}
	}

	/**
	 * Tries to find a suitable field in the target class to map with the
	 * method.
	 */
	private static Invoker getJavaBeanStyleInvoker(String name, Method method, Class targetClass,
			Map<String, String> namesMapping) {
		// try to find a java bean style compatible field
		int paramCount = method.getParameterTypes().length;
		if (paramCount == 0) {
			// could be a bean getter
			if (name.startsWith("is"))
				return getBooleanGetter(name, method, targetClass, namesMapping);
			else if (name.startsWith("get"))
				return getGetter(name, method, targetClass, namesMapping);
		} else if (paramCount == 1 && name.startsWith("set")) {
			// could be a setter
			return getSetter(name, method, targetClass, namesMapping);
		}

		// not JavaBean compatible as well
		throw new RuntimeException();
	}

	/**
	 * Tries to map an "is" style getter to a boolean field in the target class.
	 */
	private static Invoker getBooleanGetter(String name, Method method, Class targetClass,
			Map<String, String> namesMapping) {
		Field field = BaseFieldAccessor.getField(targetClass, getFieldName(2, name, namesMapping));
		Class fieldType = field.getType();
		Class returnType = method.getReturnType();
		if ((returnType == boolean.class || returnType == Boolean.class)
				&& (fieldType == boolean.class || fieldType == Boolean.class)) {
			return new FieldGetter(field);
		}
		throw new RuntimeException("No bean field for method: " + method);
	}

	/**
	 * Tries to map a "get" style getter to a boolean field in the target class.
	 */
	private static Invoker getGetter(String name, Method method, Class targetClass, Map<String, String> namesMapping) {
		Field field = BaseFieldAccessor.getField(targetClass, getFieldName(3, name, namesMapping));
		Class returnType = method.getReturnType();
		if ((returnType == Object.class || returnType.equals(field.getType())) && returnType != void.class
				&& returnType != Void.class) {
			return new FieldGetter(field);
		}
		throw new RuntimeException("No bean field for method: " + method);
	}

	/**
	 * Tries to mat a "set"ter to a field in the target class.
	 */
	private static Invoker getSetter(String name, Method method, Class targetClass, Map<String, String> namesMapping) {
		Field field = BaseFieldAccessor.getField(targetClass, getFieldName(3, name, namesMapping));
		Class[] paramTypes = method.getParameterTypes();
		Class returnType = method.getReturnType();
		if (paramTypes.length == 1 && paramTypes[0].equals(field.getType())
				&& (returnType == void.class || returnType == Void.class)) {
			return new FieldSetter(field);
		}
		throw new RuntimeException("No bean field for method: " + method);
	}

	/**
	 * Extracts and maps the field name from the method name.
	 */
	private static String getFieldName(int prefixLength, String name, Map<String, String> namesMapping) {
		String fieldName = Character.toLowerCase(name.charAt(prefixLength++)) + name.substring(prefixLength);
		String mappedName = namesMapping.get(fieldName);
		return mappedName == null ? fieldName : mappedName;
	}

	/**
	 * Check if the class is an interface.
	 * 
	 * @param type
	 *            the Class to test.
	 */
	private static void checkInterface(Class type) {
		if (!type.isInterface())
			throw new IllegalArgumentException("type can only be an interface.");
	}

	/**
	 * Invocation handler for the mapped methods.
	 * 
	 * @version 1.0
	 * @since 0.8
	 * @author Alessandro Nistico
	 */
	private static class DynamicInvocationHandler implements InvocationHandler {

		/**
		 * The target object on which make the call.
		 */

		private final Object target;
		/**
		 * The methods mapping interface-&gt;target.
		 */

		private final Map<Method, Invoker> methodsMap;

		/**
		 * The constructor.
		 * 
		 * @param target
		 *            the object on which the methods are invoked.
		 * @param methodsMap
		 *            the mapped methods.
		 */
		private DynamicInvocationHandler(Object target, Map<Method, Invoker> methodsMap) {
			this.target = target;
			this.methodsMap = methodsMap;
		}

		/**
		 * {@inheritDoc}
		 */
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			try {
				return methodsMap.get(method).invoke(target, args);
			} catch (InvocationTargetException ie) {
				throw ie.getTargetException();
			}
		}
	}

	/**
	 * Invocation abstraction for accessing methods and fields.
	 * 
	 * @author Alessandro Nistico
	 */
	interface Invoker {
		Object invoke(Object obj, Object... args) throws IllegalAccessException, IllegalArgumentException,
				InvocationTargetException;
	}

	/**
	 * An {@link Invoker} to invoke a method.
	 * 
	 * @author Alessandro Nistico
	 */
	private static class MethodInvoker implements Invoker {

		private final Method delegate;

		private MethodInvoker(Method delegate) {
			this.delegate = delegate;
		}

		public Object invoke(Object obj, Object... args) throws IllegalAccessException, IllegalArgumentException,
				InvocationTargetException {
			return delegate.invoke(obj, args);
		}
	}

	/**
	 * An {@link Invoker} to get the value of a field.
	 * 
	 * @author Alessandro Nistico
	 */
	private static class FieldGetter implements Invoker {

		private final Field accessor;

		private FieldGetter(Field accessor) {
			this.accessor = accessor;
		}

		public Object invoke(Object obj, Object... args) throws IllegalAccessException, IllegalArgumentException,
				InvocationTargetException {
			return accessor.get(obj);
		}
	}

	/**
	 * An {@link Invoker} to set the value of a field.
	 * 
	 * @author Alessandro Nistico
	 */
	private static class FieldSetter implements Invoker {

		private final Field accessor;

		private FieldSetter(Field accessor) {
			this.accessor = accessor;
		}

		public Object invoke(Object obj, Object... args) throws IllegalAccessException, IllegalArgumentException,
				InvocationTargetException {
			accessor.set(obj, args[0]);
			return null;
		}
	}
}

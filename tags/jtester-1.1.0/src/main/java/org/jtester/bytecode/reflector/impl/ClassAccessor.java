package org.jtester.bytecode.reflector.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * An accessor for classes that are package private or for private inner
 * classes.
 * <p>
 * With this object it is possible to create instances of otherwise non
 * accessible classes.
 * <p>
 * Created instances can be proxied using a known interface to make their use
 * more convenient.
 */
@SuppressWarnings({ "rawtypes" })
public class ClassAccessor {

	/**
	 * The accessed class
	 */

	private final Class accessedClass;

	/**
	 * The enclosing class of the accessed one if that is an inner class.
	 */
	private final Class enclosingClass;

	/**
	 * Creates a {@link ClassAccessor} for the class with the specified name.
	 * 
	 * @param name
	 *            the fully qualified class name.
	 * @return an instance of {@link ClassAccessor}.
	 */

	public static final ClassAccessor create(String name) {
		return create(name, ClassAccessor.class.getClassLoader());
	}

	/**
	 * Creates a {@link ClassAccessor} for the inner class with the specified
	 * name within the specified class.
	 * <p>
	 * The name for the class accepts the dot notation so that a "Nested" class
	 * that is an inner class of the inner class "Inner" can be represented as
	 * "Inner.Nested".
	 * 
	 * @param enclosing
	 *            the {@link Class} enclosing the class with the specified name.
	 * @param name
	 *            the inner class name.
	 * @return an instance of {@link ClassAccessor}.
	 */

	public static final ClassAccessor create(Class enclosing, String name) {
		return create(enclosing, name, ClassAccessor.class.getClassLoader());
	}

	/**
	 * Creates a {@link ClassAccessor} for the class with the specified name.
	 * 
	 * @param name
	 *            the fully qualified class name.
	 * @param loader
	 *            the class loader to use to load the class.
	 * @return an instance of the {@link ClassAccessor} for the specified class.
	 */

	public static final ClassAccessor create(String name, ClassLoader loader) {
		try {
			return new ClassAccessor(loader.loadClass(name));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates a {@link ClassAccessor} for the inner class with the specified
	 * name within the specified class.
	 * <p>
	 * The name for the class accepts the dot notation so that a "Nested" class
	 * that is an inner class of the inner class "Inner" can be represented as
	 * "Inner.Nested".
	 * 
	 * @param enclosing
	 *            the {@link Class} enclosing the class with the specified name.
	 * @param name
	 *            the inner class name.
	 * @param loader
	 *            the class loader to use to load the class.
	 * @return an instance of {@link ClassAccessor}.
	 */

	public static final ClassAccessor create(Class enclosing, String name, ClassLoader loader) {
		return create(enclosing.getName() + "$" + name.replace('.', '$'), loader);
	}

	/**
	 * Constructor.
	 * 
	 * @param accessedClass
	 *            the class to access
	 */
	private ClassAccessor(Class accessedClass) {
		if (accessedClass.isInterface()) {
			throw new IllegalArgumentException("The specified class is an interface");
		}
		this.enclosingClass = accessedClass.getEnclosingClass();
		this.accessedClass = accessedClass;
	}

	/**
	 * Returns the accessed {@link Class} instance.
	 * 
	 * @return the the accessed {@link Class} instance.
	 */

	public Class getAccessedClass() {
		return accessedClass;
	}

	/**
	 * Creates a {@link ClassAccessor} for the inner class with the specified
	 * name within the accessed class.
	 * <p>
	 * The name for the class accepts the dot notation so that a "Nested" class
	 * that is an inner class of the inner class "Inner" can be represented as
	 * "Inner.Nested".
	 * 
	 * @param name
	 *            the inner class name.
	 * @return an instance of {@link ClassAccessor}.
	 */

	public ClassAccessor forInner(String name) {
		return create(accessedClass, name, accessedClass.getClassLoader());
	}

	/**
	 * Creates a {@link ClassConstructor} that can be used to create instances
	 * of the accessed {@link Class}.
	 * 
	 * @param parameterTypes
	 *            the parameters types for the constructor to build.
	 * @return an instance of the specified {@link ClassConstructor}.
	 */

	public ClassConstructor constructor(Class... parameterTypes) {
		if (enclosingClass != null) {
			throw new IllegalStateException("The class " + accessedClass + " is an inner class");
		}
		try {
			Constructor<?> constructor = (Constructor<?>) accessedClass.getDeclaredConstructor(parameterTypes);
			return new ClassConstructor(constructor);
		} catch (RuntimeException e) {
			throw e;
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates a {@link ClassConstructor} that can be used to create instances
	 * of the accessed {@link Class}.
	 * 
	 * @param enclosing
	 *            the instance of the enclosing the class the the
	 *            {@link ClassConstructor} will use to build an instance of the
	 *            inner class.
	 * @param parameterTypes
	 *            the parameters types for the constructor to build.
	 * @return an instance of the specified {@link ClassConstructor}.
	 */

	public ClassConstructor constructor(Object enclosing, Class... parameterTypes) {
		if (enclosingClass == null) {
			throw new IllegalStateException("The class " + accessedClass + " is not an inner class");
		}
		if (!enclosingClass.isInstance(enclosing)) {
			throw new IllegalArgumentException("Enclosing instance of type " + enclosing.getClass()
					+ " is not an instance of " + enclosingClass);
		}
		try {
			Class[] newParameterTypes = new Class[parameterTypes.length + 1];
			System.arraycopy(parameterTypes, 0, newParameterTypes, 1, parameterTypes.length);
			newParameterTypes[0] = enclosingClass;
			Constructor<?> constructor = (Constructor<?>) accessedClass.getDeclaredConstructor(newParameterTypes);
			return new InnerClassConstructor(enclosing, constructor);
		} catch (RuntimeException e) {
			throw e;
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * A constructor for the accessed {@link Class} that allows to create
	 * instances.
	 * 
	 * @author Alessandro Nistico
	 */
	public static class ClassConstructor {

		private final Constructor<?> constructor;

		private ClassConstructor(Constructor<?> constructor) {
			constructor.setAccessible(true);
			this.constructor = constructor;
		}

		/**
		 * Creates a new instance of the accessed class.
		 * 
		 * @param initargs
		 *            the parameters to use to build the instance.
		 * @return the instance.
		 */

		public Object newInstance(Object... initargs) {
			try {
				return constructor.newInstance(initargs);
			} catch (RuntimeException e) {
				throw e;
			} catch (InvocationTargetException e) {
				Throwable targetException = e.getTargetException();
				if (targetException instanceof RuntimeException) {
					throw (RuntimeException) targetException;
				}
				if (targetException instanceof Error) {
					throw (Error) targetException;
				}
				throw new RuntimeException(targetException);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * Creates a {@link ProxyAccessor} for a newly created instance of the
		 * accessed class.
		 * 
		 * @param <I>
		 *            the type used to proxy the instance.
		 * @param type
		 *            the interface to use to proxy the instance.
		 * @param initargs
		 *            the parameters to use to build the instance.
		 * @return the proxied instance.
		 */

		public final <I> I newProxy(Class<I> type, Object... initargs) {
			return ProxyAccessor.createAccessor(type, newInstance(initargs));
		}

		/**
		 * Creates a {@link ProxyAccessor} for a newly created instance of the
		 * accessed class.
		 * 
		 * @param <I>
		 *            the type used to proxy the instance.
		 * @param type
		 *            the interface to use to proxy the instance.
		 * @param namesMapping
		 *            a mapping of names between a method in the interface to
		 *            the actual method name in the target instance or between a
		 *            standard JavaBean field name and the actual field name in
		 *            the target instance.
		 * @param initargs
		 *            the parameters to use to build the instance.
		 * @return the proxied instance.
		 */

		public final <I> I newProxy(Class<I> type, Map<String, String> namesMapping, Object... initargs) {
			return ProxyAccessor.createAccessor(type, newInstance(initargs), namesMapping);
		}
	}

	/**
	 * A {@link ClassConstructor} for an inner class.
	 * 
	 * @author Alessandro Nistico
	 * 
	 * @param <T>
	 */
	private static class InnerClassConstructor extends ClassConstructor {

		private final Object enclosing;

		private InnerClassConstructor(Object enclosing, Constructor<?> constructor) {
			super(constructor);
			this.enclosing = enclosing;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object newInstance(Object... initargs) {
			Object[] newInitargs = new Object[initargs.length + 1];
			System.arraycopy(initargs, 0, newInitargs, 1, initargs.length);
			newInitargs[0] = enclosing;

			return super.newInstance(newInitargs);
		}
	}
}

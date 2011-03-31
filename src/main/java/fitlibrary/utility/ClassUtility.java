/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import fitlibrary.CompareFilesFixture;
import fitlibrary.closure.Closure;
import fitlibrary.exception.FitLibraryException;
import fitlibrary.flow.GlobalActionScope;
import fitlibrary.global.PlugBoard;
import fitlibrary.log.FitLibraryLogger;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Row;
import fitlibrary.traverse.CompareFilesTraverse;
import fitlibrary.traverse.Evaluator;

public class ClassUtility {
	private static Logger logger = FitLibraryLogger.getLogger(ClassUtility.class);

	public static String classList(Class<?> firstClass, List<Class<?>> classes) {
		if (classes.isEmpty()) {
			return "" + firstClass;
		}
		String result = "" + classes.get(0);
		for (int i = 1; i < classes.size(); i++)
			result += "</li><li>" + classes.get(i);
		return result;
	}

	public static boolean aFitLibraryClass(Class<?> declaringClass) {
		if (declaringClass == GlobalActionScope.class || declaringClass == CompareFilesFixture.class
				|| declaringClass == CompareFilesTraverse.class)
			return false;
		Package thePackage = declaringClass.getPackage();
		if (thePackage == null)
			return false;
		String packageName = thePackage.getName();
		return packageName.equals("fit") || packageName.equals("fitlibrary")
				|| packageName.startsWith("fitlibrary.traverse") || packageName.startsWith("fitlibrary.object")
				|| packageName.startsWith("fitlibrary.collection");
	}

	public static boolean fitLibrarySystemMethod(Method method) {
		Class<?> declaringClass = method.getDeclaringClass();
		if (aFitLibraryClass(declaringClass))
			return true;
		Class<?>[] parameterTypes = method.getParameterTypes();
		return parameterTypes.length == 2 && (isSpecial(parameterTypes, 0, 1) || isSpecial(parameterTypes, 1, 0));
	}

	private static boolean isSpecial(Class<?>[] parameterTypes, int p0, int p1) {
		return parameterTypes[p0] == Row.class && parameterTypes[p1] == TestResults.class;
	}

	public static String allElementClassNames(Collection<Object> actuals) {
		List<String> results = new ArrayList<String>();
		for (Iterator<Object> it = actuals.iterator(); it.hasNext();) {
			Object element = it.next();
			if (element == null)
				throw new FitLibraryException("An element of the collection is null");
			String name = element.getClass().getName();
			if (!results.contains(name))
				results.add(name);
		}
		String toString = results.toString();
		return toString.substring(1, toString.length() - 1);
	}

	public static String simpleClassName(Class<?> type) { // use
															// Class.getSimpleName()
															// in jdk1.5
		String className = type.getName();
		int dot = className.lastIndexOf(".");
		if (dot >= 0)
			className = className.substring(dot + 1);
		dot = className.lastIndexOf("$");
		if (dot >= 0)
			className = className.substring(dot + 1);
		return className;
	}

	public static boolean isEffectivelyPrimitive(Class<?> componentType) {
		return componentType.isPrimitive() || componentType == Boolean.class || componentType == Character.class
				|| componentType == Byte.class || componentType == Short.class || componentType == Integer.class
				|| componentType == Long.class || componentType == Float.class || componentType == Double.class
				|| componentType == String.class;
	}

	public static List<String> methodSignatures(String name, List<String> methodArgs, String returnType) {
		List<String> signatures = new ArrayList<String>();
		if (methodArgs.isEmpty())
			signatures.add("public " + returnType + " get" + name.substring(0, 1).toUpperCase() + name.substring(1)
					+ "() { }");
		String signature = "public " + returnType + " " + name + "(";
		Iterator<String> iterator = methodArgs.iterator();
		for (int i = 0; i < methodArgs.size(); i++) {
			if (i > 0)
				signature += ", ";
			signature += "Type" + (i + 1) + " " + iterator.next();
		}
		signature += ") { }";
		signatures.add(signature);
		return signatures;
	}

	public static Object newInstance(Class<?> sutClass) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Constructor<?> declaredConstructor = sutClass.getDeclaredConstructor(new Class[] {});
		declaredConstructor.setAccessible(true);
		Object object = declaredConstructor.newInstance(new Object[] {});
		logger.trace("Created " + object);
		return object;
	}

	public static Object newInstance(String className) throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException,
			ClassNotFoundException {
		Class<?> theClass = Class.forName(className);
		return newInstance(theClass);
	}

	public static Object createElement(Class<?> type, Evaluator evaluator) throws Exception {
		Object element = null;
		Closure fixturingMethod = PlugBoard.lookupTarget.findNewInstancePluginMethod(evaluator);
		if (fixturingMethod != null)
			element = fixturingMethod.invoke(new Object[] { type });
		if (element == null)
			element = ClassUtility.newInstance(type);
		return element;
	}
}

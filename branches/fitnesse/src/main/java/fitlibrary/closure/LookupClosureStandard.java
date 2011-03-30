/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.closure;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fitlibrary.exception.method.AmbiguousNameException;
import fitlibrary.special.DoAction;
import fitlibrary.traverse.workflow.DoEvaluator;
import fitlibrary.typed.TypedObject;
import fitlibrary.utility.ClassUtility;

public class LookupClosureStandard implements LookupClosure {
	private Map<MethodSignature, Object> mapMethodSignatureToMethod = new ConcurrentHashMap<MethodSignature, Object>(
			5000);
	private final Object NOT_FOUND = "";

	// @Override
	public void mustBeThreadSafe() {
		//
	}

	// @Override
	public Closure findMethodClosure(TypedObject typedObject, String methodName, int argCount) {
		if (typedObject.isNull())
			return null;
		Method chosenMethod = findMethod(typedObject.classType(), methodName, argCount, typedObject.getSubject());
		if (chosenMethod == null && aGetter(methodName, argCount))
			return findField(typedObject, extractFieldName(methodName));
		if (chosenMethod == null)
			return null;
		return new MethodClosure(typedObject, chosenMethod);
	}

	// @Override
	public Closure findPublicMethodClosure(TypedObject typedObject, String name, Class<?>[] argTypes) {
		if (typedObject.isNull())
			return null;
		try {
			return new MethodClosure(typedObject, typedObject.classType().getMethod(name, argTypes));
		} catch (Exception e) {
			return null;
		}
	}

	// @Override
	public boolean fitLibrarySystemMethod(Method method, int argCount, Object subject) {
		if (!ClassUtility.fitLibrarySystemMethod(method))
			return false;
		if (subject instanceof DoEvaluator)
			return !((DoEvaluator) subject).methodsThatAreVisible().contains(method.getName() + "/" + argCount);
		return true;
	}

	protected Closure findField(TypedObject typedObject, String fieldName) {
		try {
			Class<?> type = typedObject.classType();
			return new FieldClosure(typedObject, type.getField(fieldName));
		} catch (Exception e) {
			return findPrivateField(typedObject, fieldName);
		}
	}

	protected Closure findPrivateField(TypedObject typedObject, String fieldName) {
		Class<?> type = typedObject.classType();
		Field[] declaredFields = type.getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			Field field = declaredFields[i];
			if (fieldName.equals(field.getName())) {
				field.setAccessible(true);
				return new FieldClosure(typedObject, field);
			}
		}
		return null;
	}

	protected Method findMethod(Class<?> type, String name, int argCount, Object subject) {
		MethodSignature methodSignature = new MethodSignature(type, name, argCount);
		Object result = mapMethodSignatureToMethod.get(methodSignature);
		if (result != null) {
			if (result == NOT_FOUND)
				return null;
			return (Method) result;
		}
		Method chosenMethod = findSpecificMethod(type, name, argCount, subject);
		if (chosenMethod == null && (aGetter(name, argCount) || aSetter(name, argCount)))
			chosenMethod = findPrivateMethod(type, name, argCount, subject);
		if (chosenMethod == null)
			mapMethodSignatureToMethod.put(methodSignature, NOT_FOUND);
		else
			mapMethodSignatureToMethod.put(methodSignature, chosenMethod);
		return chosenMethod;
	}

	protected Method findSpecificMethod(Class<?> type, String name, int argCount, Object subject) {
		Method[] methods = type.getMethods();
		Method chosenMethod = null;
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (name.equals(method.getName()) && method.getParameterTypes().length == argCount
					&& !doActionMethod(method.getParameterTypes()) &&
					// !method.isSynthetic() &&
					!fitLibrarySystemMethod(method, argCount, subject)) {
				if (chosenMethod == null)
					chosenMethod = method;
				else
					throw new AmbiguousNameException(name);
			}
		}
		return chosenMethod;
	}

	private boolean doActionMethod(Class<?>[] parameterTypes) {
		for (Class<?> t : parameterTypes)
			if (t == DoAction.class)
				return true;
		return false;
	}

	protected String extractFieldName(String methodName) {
		String fieldName = "";
		if (methodName.startsWith("is"))
			fieldName = methodName.substring(2);
		else
			fieldName = methodName.substring(3);
		fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
		return fieldName;
	}

	protected boolean aGetter(String name, int argCount) {
		boolean getter = name.startsWith("get") && name.length() > 3 && isUpper(name.charAt(3));
		boolean isa = name.startsWith("is") && name.length() > 2 && isUpper(name.charAt(2));
		return argCount == 0 && (getter || isa);
	}

	protected boolean isUpper(char ch) {
		return Character.isUpperCase(ch);
	}

	protected boolean aSetter(String name, int argCount) {
		return argCount == 1 && name.startsWith("set");
	}

	protected Method findPrivateMethod(Class<?> type, String name, int args, Object subject) {
		Method chosenMethod = findMethod(type.getDeclaredMethods(), name, args, subject);
		if (chosenMethod != null) {
			chosenMethod.setAccessible(true);
			return chosenMethod;
		}
		if (type.getSuperclass() != null)
			return findPrivateMethod(type.getSuperclass(), name, args, subject);
		return null;
	}

	protected Method findMethod(Method[] methods, String name, int args, Object subject) {
		Method chosenMethod = null;
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (name.equals(method.getName()) && method.getParameterTypes().length == args
					&& !doActionMethod(method.getParameterTypes()) && !fitLibrarySystemMethod(method, args, subject))
				if (chosenMethod == null)
					chosenMethod = method;
				else
					throw new AmbiguousNameException(name);
		}
		return chosenMethod;
	}

	protected static class MethodSignature {
		private Class<?> type;
		private String name;
		private int args;

		public MethodSignature(Class<?> type, String name, int args) {
			this.type = type;
			this.name = name;
			this.args = args;
		}

		@Override
		public boolean equals(Object object) {
			if (!(object instanceof MethodSignature))
				return false;
			MethodSignature signature = (MethodSignature) object;
			return type == signature.type && name.equals(signature.name) && args == signature.args;
		}

		@Override
		public int hashCode() {
			return type.hashCode() + name.hashCode() + args;
		}

		@Override
		public String toString() {
			return type + "." + name + "(" + args + ")";
		}
	}
}

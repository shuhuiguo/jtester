/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.closure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import fitlibrary.exception.method.WrongTypeForMethodException;
import fitlibrary.log.FitLibraryLogger;
import fitlibrary.parser.Parser;
import fitlibrary.parser.lookup.ResultParser;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.Traverse;
import fitlibrary.typed.TypedObject;

public class MethodClosure implements Closure {
	private static Logger logger = FitLibraryLogger.getLogger(MethodClosure.class);
	public static int MAX_RUNS_SHOWN = 20;
	private TypedObject typedObject;
	private Method method;
	private int runAlready = 0;

	public MethodClosure(TypedObject typedObject, Method method) {
		if (typedObject == null || typedObject.isNull() || method == null)
			throw new RuntimeException("MethodClosure requires non-null args");
		this.typedObject = typedObject;
		this.method = method;
	}

	// @Override
	public TypedObject invokeTyped(Object[] args) throws IllegalAccessException, InvocationTargetException {
		return typedObject.asReturnTypedObject(invoke(args), method);
	}

	// @Override
	public Object invoke() throws IllegalAccessException, InvocationTargetException {
		return invoke(new Object[] {});
	}

	// @Override
	public Object invoke(Object[] args) throws IllegalAccessException, InvocationTargetException {
		if (runAlready < MAX_RUNS_SHOWN)
			logger.trace("Calling " + this); // Avoid blowing the heap with lots
												// of logs
		else if (runAlready == MethodClosure.MAX_RUNS_SHOWN)
			logger.trace("Calling " + this + "(etc)");
		runAlready++;
		try {
			return method.invoke(typedObject.getSubject(), args);
		} catch (IllegalArgumentException e) {
			Class<?>[] parameterTypes = method.getParameterTypes();
			String expectedType = "(";
			for (int i = 0; i < parameterTypes.length; i++)
				expectedType += parameterTypes[i].getName() + ", ";
			expectedType = expectedType.substring(0, expectedType.length() - 2);
			expectedType += ")";
			String actualType = "(";
			for (int i = 0; i < args.length; i++) {
				if (args[i] == null)
					actualType += "null, ";
				else
					actualType += args[i].getClass().getName() + ", ";
			}
			if (args.length > 0)
				actualType = actualType.substring(0, actualType.length() - 2);
			actualType += ")";
			throw new WrongTypeForMethodException(method, expectedType, actualType);
		}
	}

	public void setSubject(Object subject) {
		this.typedObject = Traverse.asTypedObject(subject);
	}

	// @Override
	public void setTypedSubject(TypedObject typedObject) {
		this.typedObject = typedObject;
	}

	@Override
	public String toString() {
		return method.getName() + "(" + args() + ") on " + typedObject.getSubject();
	}

	private String args() {
		Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes.length == 0)
			return "";
		String s = "";
		for (Class<?> t : parameterTypes)
			s += "," + t.getSimpleName();
		return s.substring(1);
	}

	// @Override
	public ResultParser resultParser(Evaluator evaluator) {
		return typedObject.resultParser(evaluator, method);
	}

	// @Override
	public Parser[] parameterParsers(Evaluator evaluator) {
		return typedObject.parameterParsers(evaluator, method);
	}

	// @Override
	public ResultParser specialisedResultParser(ResultParser resultParser, Object result, Evaluator evaluator) {
		if (result == null || result.getClass() == method.getReturnType())
			return resultParser;
		return typedObject.resultParser(evaluator, method, result.getClass());
	}

	// @Override
	public Class<?> getReturnType() {
		return method.getReturnType();
	}

	// @Override
	public Class<?>[] getParameterTypes() {
		return method.getParameterTypes();
	}

	// @Override
	public Class<?> getOwningClass() {
		return typedObject.getSubject().getClass();
	}
}

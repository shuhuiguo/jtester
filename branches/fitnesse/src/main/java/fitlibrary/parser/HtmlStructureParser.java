/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;

/**
 * Subclasses don't interpret the contents of a cell as text, but in some other
 * way. For example, A TreeParser interprets the contents as an HTML list and
 * converts it into a ListTree.
 */
public abstract class HtmlStructureParser implements HtmlParser {
	private Typed typed;

	protected HtmlStructureParser(Typed typed) {
		this.typed = typed;
	}

	protected Object callReflectively(String methodName, Object[] args, Class<?>[] argTypes, Object object) {
		try {
			Method reflectiveMethod = typed.asClass().getMethod(methodName, argTypes);
			return reflectiveMethod.invoke(object, args);
		} catch (SecurityException e) {
			error(methodName, argTypes, e);
		} catch (NoSuchMethodException e) {
			error(methodName, argTypes, e);
		} catch (IllegalArgumentException e) {
			error(methodName, argTypes, e);
		} catch (IllegalAccessException e) {
			error(methodName, argTypes, e);
		} catch (InvocationTargetException e) {
			error(methodName, argTypes, e.getTargetException());
		}
		return null; // satisfy compiler, as unreachable
	}

	private void error(String methodName, Class<?>[] argTypes, Throwable ex) {
		String args = Arrays.asList(argTypes).toString();
		args = "(" + args.substring(1, args.length() - 1) + ")";
		String problem = "Problem with accessing " + methodName + args + " of class " + typed.asClass().getName()
				+ ": " + ex;
		throw new FitLibraryException(problem);
	}

	// @Override
	public TypedObject parseTyped(Cell cell, TestResults testResults) throws Exception {
		return typed.typedObject(parse(cell, testResults));
	}

	protected abstract Object parse(Cell cell, TestResults testResults) throws Exception;

	// @Override
	public boolean matches(Cell cell, Object result, TestResults testResults) throws Exception {
		return areEqual(parseTyped(cell, testResults).getSubject(), result);
	}

	// Overridden
	protected boolean areEqual(Object a, Object b) {
		if (a == null)
			return b == null;
		return a.equals(b);
	}
}

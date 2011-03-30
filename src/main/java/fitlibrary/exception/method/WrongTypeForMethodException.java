/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.exception.method;

import java.lang.reflect.Method;

import fitlibrary.exception.FitLibraryException;

public class WrongTypeForMethodException extends FitLibraryException {
	private static final long serialVersionUID = 1L;

	public WrongTypeForMethodException(Method method, String expectedTypes, String actualTypes) {
		super("Call to " + method.getName() + expectedTypes + " in " + method.getDeclaringClass() + " failed with "
				+ actualTypes);
	}
}

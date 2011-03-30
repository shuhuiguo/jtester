/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.exception.classes;

import java.lang.reflect.Modifier;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.utility.ExtendedCamelCase;

public class NoNullaryConstructor extends FitLibraryException {
	private static final long serialVersionUID = 1L;

	public NoNullaryConstructor(String className, RuntimeContextInternal runtime) {
		super("Class has no default constructor: "
				+ ExtendedCamelCase.camelClassName(className, runtime.getConfiguration().keepingUniCode()));
	}

	public NoNullaryConstructor(Class<?> type) {
		super((Modifier.isAbstract(type.getModifiers()) ? "Class is abstract: " : "Class has no default constructor: ")
				+ type.getName());
	}
}

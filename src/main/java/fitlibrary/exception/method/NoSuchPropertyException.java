/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.exception.method;

import fitlibrary.exception.FitLibraryException;

public class NoSuchPropertyException extends FitLibraryException {
	private static final long serialVersionUID = 1L;

	public NoSuchPropertyException(String propertyName, String classNames) {
		super("Could not find property " + propertyName + " in " + classNames);
	}
}

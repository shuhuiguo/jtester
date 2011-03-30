/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.exception.method;

import fitlibrary.exception.FitLibraryExceptionWithHelp;

public class VoidMethodException extends FitLibraryExceptionWithHelp {
	private static final long serialVersionUID = 1L;

	public VoidMethodException(String name, String link) {
		super("Method " + name + " is void.", "VoidMethod." + link);
	}
}

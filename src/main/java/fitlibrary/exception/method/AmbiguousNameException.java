/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.exception.method;

import fitlibrary.exception.FitLibraryExceptionWithHelp;

public class AmbiguousNameException extends FitLibraryExceptionWithHelp {
	private static final long serialVersionUID = 1L;

	public AmbiguousNameException(String name) {
		super("\"" + name + "\" is ambiguous", "AmbiguousMethodName");
	}
}

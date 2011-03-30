/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 2/09/2006
 */

package fitlibrary.exception.parse;

import fitlibrary.exception.FitLibraryException;

public abstract class ParseException extends FitLibraryException {
	private static final long serialVersionUID = 1L;

	public ParseException(String s) {
		super(s);
	}
}

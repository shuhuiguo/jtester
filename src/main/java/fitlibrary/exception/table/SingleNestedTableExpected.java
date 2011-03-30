/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.exception.table;

import fitlibrary.exception.FitLibraryException;

public class SingleNestedTableExpected extends FitLibraryException {
	private static final long serialVersionUID = 1L;

	public SingleNestedTableExpected() {
		super("Single nested table expected");
	}
}

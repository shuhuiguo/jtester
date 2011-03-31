/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fitlibrary.traverse.FitLibrarySelector;

public class ArrayFixture extends CollectionFixture {
	public ArrayFixture() {
		super(FitLibrarySelector.selectOrderedList());
	}

	public ArrayFixture(Object actuals) {
		super(FitLibrarySelector.selectOrderedList(actuals));
	}
}

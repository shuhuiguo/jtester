/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fitlibrary.traverse.FitLibrarySelector;

public class SetFixture extends CollectionFixture {
	public SetFixture() {
		super(FitLibrarySelector.selectSet());
	}

	public SetFixture(Object actuals) {
		super(FitLibrarySelector.selectSet(actuals));
	}
}

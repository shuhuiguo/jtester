/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fitlibrary.traverse.FitLibrarySelector;

/**
 * Like SetFixture, except that it ignores any surplus, unmatched elements in
 * the actual collection. That is, the table specifies an expected subset.
 */
public class SubsetFixture extends CollectionFixture {
	public SubsetFixture() {
		super(FitLibrarySelector.selectSubset());
	}

	public SubsetFixture(Object actuals) {
		super(FitLibrarySelector.selectSubset(actuals));
	}
}

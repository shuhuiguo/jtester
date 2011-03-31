/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fitlibrary.traverse.GridTraverse;

/**
 * Checks the values in the table against the values in the 2D array
 */
public class GridFixture extends FitLibraryFixture {
	private GridTraverse gridTraverse = new GridTraverse(this);

	protected GridFixture() {
		setTraverse(gridTraverse);
	}

	public GridFixture(Object[][] grid) {
		this();
		setGrid(grid);
	}

	public void setGrid(Object[][] grid) {
		gridTraverse.setGrid(grid);
	}
}

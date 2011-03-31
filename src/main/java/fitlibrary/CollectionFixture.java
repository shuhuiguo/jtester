/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fitlibrary.collection.CollectionTraverse;

public abstract class CollectionFixture extends FitLibraryFixture {
	private CollectionTraverse listTraverse;

	public void setActualCollection(Object actuals) {
		listTraverse.setActualCollection(actuals);
	}

	protected CollectionFixture(CollectionTraverse listTraverse) {
		this.listTraverse = listTraverse;
		setTraverse(listTraverse);
		listTraverse.setSystemUnderTest(this);
	}
}

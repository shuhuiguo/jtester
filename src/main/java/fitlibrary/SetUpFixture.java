/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fitlibrary.collection.CollectionSetUpTraverse;

public class SetUpFixture extends DoFixture {
	private CollectionSetUpTraverse setUpTraverse = new CollectionSetUpTraverse(this);

	public SetUpFixture() {
		super();
		setTraverse(setUpTraverse);
	}

	public SetUpFixture(Object sut) {
		this();
		setSystemUnderTest(sut);
	}

	public CollectionSetUpTraverse getSetUpTraverse() {
		return setUpTraverse;
	}
}

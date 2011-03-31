/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 24/08/2006
*/

package fitlibrary.specify.collectionSetUp;

import fitlibrary.traverse.FitLibrarySelector;

public class MissingObjectFactoryMethod {
	public Object missing() {
		return FitLibrarySelector.selectCollectionSetUp();
	}
}

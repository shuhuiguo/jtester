/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 24/08/2006
 */

package fitlibrary.specify.collectionSetUp;

import fitlibrary.specify.exception.ForcedException;
import fitlibrary.traverse.FitLibrarySelector;

public class ExceptionInObjectFactoryMethod {
	public Object create() {
		return FitLibrarySelector.selectCollectionSetUp();
	}

	public void badMethod(int i, int j) {
		throw new ForcedException();
	}
}

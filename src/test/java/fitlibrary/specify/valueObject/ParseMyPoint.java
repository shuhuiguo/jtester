/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 17/08/2006
*/

package fitlibrary.specify.valueObject;

import fitlibrary.specify.eg.MyPoint;
import fitlibrary.traverse.FitLibrarySelector;
import fitlibrary.traverse.Traverse;

public class ParseMyPoint {
	public MyPoint aPoint(MyPoint point) {
		return point;
	}
	public Traverse aPointAsDomainObject(MyPoint point) {
		return FitLibrarySelector.selectDomainCheck(point);
	}
}

/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fitlibrary.parser.collection.ArrayParser;

/**
 * A row-type fixture for checking on the toString() values of objects. Written
 * by Jeff Nielsen
 */
public class PrimitiveArrayFixture extends FitLibraryFixture {
	public PrimitiveArrayFixture(Object actuals) {
		setTraverse(ArrayParser.selectPrimitiveArray(actuals));
	}
}

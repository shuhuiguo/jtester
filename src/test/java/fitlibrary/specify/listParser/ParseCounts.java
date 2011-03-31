/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.listParser;

import java.util.Iterator;
import java.util.List;

import fitlibrary.specify.eg.Count;

@SuppressWarnings("rawtypes")
public class ParseCounts {
	public List givenCounts(List list) {
		return list;
	}
	public Iterator givenIterator(Iterator iterator) {
		return iterator;
	}
	public Count count(int count) {
		return new Count(count);
	}
	public List givenColours(List colours) {
		return colours;
	}
}

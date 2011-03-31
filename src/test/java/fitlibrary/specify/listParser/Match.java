/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.listParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fitlibrary.specify.eg.Count;

@SuppressWarnings("rawtypes")
public class Match {
	public List stringAbc() {
		return Arrays.asList(new String[] {"a","b","c"});
	}
	public List list123() {
		return Arrays.asList(new Integer[] {
				new Integer(1),new Integer(2),new Integer(3)});
	}
	public Iterator iterator23() {
		return counts23().iterator();
	}
	public List listEmpty() {
		return new ArrayList();
	}
	public List counts23() {
		return Arrays.asList(new Count[] {
				new Count(2), new Count(3)});
	}
}

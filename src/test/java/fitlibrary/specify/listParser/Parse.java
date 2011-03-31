/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.listParser;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Parse {
	public List givenList(List list) {
		return list;
	}
	public String[] givenStrings(List list) {
		String[] result = new String[list.size()];
		int i = 0;
		for (Iterator it = list.iterator(); it.hasNext(); i++)
				result[i] = (String)it.next();
		return result;
	}
	public Integer[] givenIntegers(List list) {
		Integer[] result = new Integer[list.size()];
		int i = 0;
		for (Iterator it = list.iterator(); it.hasNext(); i++)
				result[i] = (Integer)it.next();
		return result;
	}
}

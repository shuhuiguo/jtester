/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 23/09/2006
 */

package fitlibrary.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListCreator {
	public static <T extends Object> List<T> list(T... ts) {
		List<T> list = new ArrayList<T>();
		for (T t : ts)
			list.add(t);
		return list;
	}

	public static <T extends Object> Set<T> set(T... ts) {
		return new HashSet<T>(list(ts));
	}
}

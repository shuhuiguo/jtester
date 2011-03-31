/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 5/09/2006
 */

package fitlibrary.utility;

import java.util.Iterator;
import java.util.List;

public class CollectionUtility {
	public static boolean equalsIterator(Iterator<?> it, Iterator<?> it2) {
		if (it == it2)
			return true;
		while (it.hasNext() && it2.hasNext()) {
			if (!it.next().equals(it2.next()))
				return false;
		}
		return !it.hasNext() && !it2.hasNext();
	}

	public static <T> List<T> list(T... ss) {
		return ListCreator.list(ss);
	}

	public static <T> String mkString(String separator, List<T> ss) {
		return mkString(separator, ss.toArray());
	}

	public static <T> String mkString(String separator, T... ss) {
		if (ss.length == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		sb.append(ss[0]);
		for (int i = 1; i < ss.length; i++)
			sb.append(separator).append(ss[i]);
		return sb.toString();
	}
}

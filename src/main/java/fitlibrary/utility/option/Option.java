/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.utility.option;

public interface Option<T> {
	boolean isNone();

	boolean isSome();

	T get();
}

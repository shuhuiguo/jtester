/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.utility;

import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;

public class StringTablesPair extends Pair<String, Tables> {
	public StringTablesPair(String s) {
		super(s, TableFactory.tables());
	}

	public StringTablesPair(String s, Tables tables) {
		super(s, tables);
	}
}

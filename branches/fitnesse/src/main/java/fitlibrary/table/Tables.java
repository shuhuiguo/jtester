/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.table;

import fit.Parse;

public interface Tables extends TableElement<Tables, Table> {
	String report();

	void addTables(Tables tables);

	void print(String heading);

	Parse asParse();
}

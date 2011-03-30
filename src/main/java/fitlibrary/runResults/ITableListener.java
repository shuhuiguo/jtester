/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.runResults;

import fitlibrary.table.Table;

public interface ITableListener {
	void tableFinished(Table table);

	void storytestFinished();

	TestResults getTestResults();
}

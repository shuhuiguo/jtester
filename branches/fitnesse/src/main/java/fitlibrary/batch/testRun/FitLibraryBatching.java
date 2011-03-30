/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.batch.testRun;

import fitlibrary.runResults.TableListener;
import fitlibrary.table.Tables;

public interface FitLibraryBatching {
	void doTables(Tables tables, TableListener listener);

	void setCurrentPageName(String name);
}

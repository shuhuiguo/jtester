/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.suite;

import fitlibrary.runResults.TestResults;
import fitlibrary.table.Tables;

public interface StorytestRunner {
	TestResults doStorytest(Tables actualTables);
}

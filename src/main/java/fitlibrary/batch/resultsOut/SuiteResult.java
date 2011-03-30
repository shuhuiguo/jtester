/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.batch.resultsOut;

import fitlibrary.batch.trinidad.TestResult;

public interface SuiteResult extends TestResult {
	void append(TestResult result);
}

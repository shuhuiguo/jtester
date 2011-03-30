/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.flow;

import fitlibrary.runResults.TestResults;
import fitlibrary.table.Row;

public interface SetUpTearDown {
	void callSetUpOnSutChain(Object subject, Row row, TestResults testResults);

	void callTearDownOnSutChain(Object subject, Row row, TestResults testResults);

	void callSuiteSetUp(Object subject, Row row, TestResults testResults);

	void callSuiteTearDown(Object subject, TestResults testResults);
}

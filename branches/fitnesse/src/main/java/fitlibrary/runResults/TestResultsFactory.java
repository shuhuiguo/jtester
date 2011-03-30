/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.runResults;

import fit.Counts;

public class TestResultsFactory {
	public static TestResults testResults() {
		return new TestResultsOnCounts();
	}

	public static TestResults testResults(Counts counts) {
		return new TestResultsOnCounts(counts);
	}
}

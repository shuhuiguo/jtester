/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.batch.resultsOut;

import fitlibrary.batch.trinidad.TestResultRepository;

public interface ParallelTestResultRepository extends TestResultRepository {
	void closeAndWaitForCompletion() throws InterruptedException;
}

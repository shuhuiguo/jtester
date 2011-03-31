/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse.workflow;

import java.util.List;

import fitlibrary.runResults.TestResults;
import fitlibrary.table.Table;
import fitlibrary.traverse.Evaluator;

public interface DoEvaluator extends Evaluator {
	Object interpretInFlow(Table table, TestResults testResults);

	List<String> methodsThatAreVisible();
}

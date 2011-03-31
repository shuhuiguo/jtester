/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.traverse.workflow;

import fitlibrary.runResults.TestResults;
import fitlibrary.table.Row;
import fitlibrary.traverse.FitHandler;
import fitlibrary.typed.TypedObject;

public interface FlowEvaluator extends DoEvaluator {
	TypedObject interpretRow(Row row, TestResults testResults);

	FitHandler fitHandler();

	void setTypedSystemUnderTest(TypedObject typedResult);
}

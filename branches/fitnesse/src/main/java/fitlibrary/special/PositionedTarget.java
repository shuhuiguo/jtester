/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.special;

import fitlibrary.runResults.TestResults;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Row;
import fitlibrary.typed.TypedObject;

public interface PositionedTarget {
	boolean isFound();

	String ambiguityErrorMessage();

	TypedObject run(Row row, TestResults testResults, RuntimeContextInternal runtimeContextInternal);

	boolean partiallyValid();

	String getPartialErrorMessage();
}

/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.closure;

import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.typed.TypedObject;

public interface MethodTarget {
	boolean matches(Cell cell, TestResults testResults);

	String getResult() throws Exception;

	boolean invokeAndCheckCell(Cell expectedCell, boolean matchedAlready, TestResults testResults);

	void setTypedSubject(TypedObject typedObject);
}

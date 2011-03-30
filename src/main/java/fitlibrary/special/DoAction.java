/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.special;

import fitlibrary.runtime.RuntimeContext;
import fitlibrary.tableProxy.CellProxy;

public interface DoAction {
	Object run() throws Exception;

	Object runWithNoColouring() throws Exception;

	RuntimeContext getRuntime();

	CellProxy cellAt(int i);

	void showResult(Object result) throws Exception;

	void show(String htmlString);

	void showAfter(Object result) throws Exception;

	void showAfterAs(String title, Object result) throws Exception;
	// boolean equals(Object result, Object expected) throws Exception;
}

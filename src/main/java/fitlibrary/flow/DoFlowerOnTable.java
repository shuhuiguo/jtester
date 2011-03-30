/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.flow;

import fitlibrary.runResults.ITableListener;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Table;

public interface DoFlowerOnTable {
	void runTable(Table table, ITableListener tableListener, RuntimeContextInternal runtime);
}

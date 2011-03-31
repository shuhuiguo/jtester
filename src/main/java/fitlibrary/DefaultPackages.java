/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary;

import fitlibrary.runResults.TestResults;
import fitlibrary.table.Table;
import fitlibrary.traverse.Traverse;
import fitlibrary.traverse.workflow.caller.CreateFromClassNameCaller;

public class DefaultPackages extends Traverse {
	// Called by String name from CreateFromClassNameCaller
	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		for (int r = 1; r < table.size(); r++) {
			CreateFromClassNameCaller.addDefaultPackage(table.at(r).text(0, this));
		}
		return null;
	}
}

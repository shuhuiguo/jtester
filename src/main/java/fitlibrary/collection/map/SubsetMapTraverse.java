/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.collection.map;

import java.util.Map;

import fitlibrary.runResults.TestResults;
import fitlibrary.table.Table;

public class SubsetMapTraverse extends MapTraverse {

	public SubsetMapTraverse(Map<Object, Object> map) {
		super(map);
	}

	@Override
	protected void addSurplusRows(Table table, Map<Object, Object> surplus, TestResults testResults) {
		// Do nothing with surplus
	}
}

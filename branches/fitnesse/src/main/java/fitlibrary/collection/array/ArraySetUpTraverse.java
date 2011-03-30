/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.collection.array;

import java.lang.reflect.Array;

import fitlibrary.exception.table.RowWrongWidthException;
import fitlibrary.parser.Parser;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.traverse.Traverse;

public class ArraySetUpTraverse extends Traverse {
	private Parser valueAdapter;
	private Object array;
	private Class<?> componentType;

	public ArraySetUpTraverse(Class<?> componentType, Parser valueAdapter) {
		this.componentType = componentType;
		this.valueAdapter = valueAdapter;
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		array = Array.newInstance(componentType, table.size());
		for (int rowNo = 0; rowNo < table.size(); rowNo++) {
			Row row = table.at(rowNo);
			try {
				if (row.size() != 1)
					throw new RowWrongWidthException(1);
				Cell cell = row.at(0);
				Array.set(array, rowNo, valueAdapter.parseTyped(cell, testResults).getSubject());
			} catch (Exception e) {
				row.error(testResults, e);
			}
		}
		return array;
	}

	public Object getResults() {
		return array;
	}
}

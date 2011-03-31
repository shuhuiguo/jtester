/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibraryGeneric.list;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import fitlibrary.exception.table.RowWrongWidthException;
import fitlibrary.parser.Parser;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.traverse.workflow.DoTraverse;
import fitlibraryGeneric.typed.GenericTyped;

public class NestingListSetUpTraverse extends DoTraverse {
	private GenericTyped subComponentTyped;
	private List<Object> list = new ArrayList<Object>();

	public NestingListSetUpTraverse(GenericTyped subComponentTyped) {
		this.subComponentTyped = subComponentTyped;
	}

	public NestingListSetUpTraverse(ParameterizedType subComponentTType) {
		this(new GenericTyped(subComponentTType));
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		Parser parser = subComponentTyped.parser(this);
		for (int rowNo = 0; rowNo < table.size(); rowNo++) {
			Row row = table.at(rowNo);
			try {
				if (row.size() != 1)
					throw new RowWrongWidthException(1);
				Cell cell = row.at(0);
				list.add(parser.parseTyped(cell, testResults).getSubject());
			} catch (Exception e) {
				row.error(testResults, e);
			}
		}
		return list;
	}

	public List<Object> getResults() {
		return list;
	}
}

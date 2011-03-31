/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibraryGeneric.list;

import java.util.List;

import fitlibrary.exception.table.RowWrongWidthException;
import fitlibrary.parser.Parser;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.traverse.Traverse;
import fitlibraryGeneric.typed.GenericTyped;

public class NestingListTraverse extends Traverse {
	private List<Object> list;
	private GenericTyped typed;

	public NestingListTraverse(List<Object> list, GenericTyped type) {
		this.list = list;
		this.typed = type;
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		Parser parser = typed.parser(this);
		for (int rowNo = 0; rowNo < table.size(); rowNo++) {
			Row row = table.at(rowNo);
			try {
				if (row.size() != 1)
					throw new RowWrongWidthException(1);
				Cell cell = row.at(0);
				if (list.size() > rowNo)
					parser.matches(cell, list.get(rowNo), testResults);
				else
					cell.actualElementMissing(testResults);
			} catch (Exception e) {
				row.error(testResults, e);
			}
		}
		for (int i = table.size(); i < list.size(); i++) {
			Row row = table.newRow();
			Cell cell = row.addCell();
			try {
				cell.actualElementMissing(testResults, parser.show(list.get(i)));
			} catch (Exception e) {
				cell.error(testResults, e);
			}
		}
		return list;
	}
}

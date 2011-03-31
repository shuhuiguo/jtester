/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibraryGeneric.set;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fitlibrary.exception.table.RowWrongWidthException;
import fitlibrary.parser.Parser;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.traverse.Traverse;
import fitlibraryGeneric.typed.GenericTyped;

public class NestingSetTraverse extends Traverse {
	private Set<Object> set;
	private GenericTyped typed;

	public NestingSetTraverse(Set<Object> set, GenericTyped type) {
		this.set = set;
		this.typed = type;
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		Parser parser = typed.parser(this);
		List<Object> listOfActuals = new ArrayList<Object>(set);
		for (int rowNo = 0; rowNo < table.size(); rowNo++) {
			Row row = table.at(rowNo);
			try {
				if (row.size() != 1)
					throw new RowWrongWidthException(1);
				matchActual(row.at(0), parser, listOfActuals, table.size(), testResults);
			} catch (Exception e) {
				row.error(testResults, e);
			}
		}
		for (int i = 0; i < listOfActuals.size(); i++) {
			Row row = table.newRow();
			Cell cell = row.addCell();
			try {
				cell.actualElementMissing(testResults, parser.show(listOfActuals.get(i)));
			} catch (Exception e) {
				cell.error(testResults, e);
			}
		}
		return set;
	}

	private void matchActual(Cell cell, Parser adapter, List<Object> listOfActuals, int tableSize,
			TestResults testResults) throws Exception {
		if (tableSize == 1 && listOfActuals.size() == 1) {
			adapter.matches(cell, listOfActuals.get(0), testResults);
			listOfActuals.remove(0);
			return;
		}
		for (int i = 0; i < listOfActuals.size(); i++) {
			Object element = listOfActuals.get(i);
			if (adapter.matches((Cell) cell.deepCopy(), element, testResults)) {
				adapter.matches(cell, element, testResults);
				listOfActuals.remove(i);
				return;
			}
		}
		cell.expectedElementMissing(testResults);
	}
}

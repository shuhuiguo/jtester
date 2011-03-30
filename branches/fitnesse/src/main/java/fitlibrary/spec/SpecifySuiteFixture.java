/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.spec;

import fitlibrary.exception.table.NestedTableExpectedException;
import fitlibrary.exception.table.RowWrongWidthException;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.table.Tables;

/**
 * Like SpecifyFixture, except that: o It handles multiple rows, where each row
 * corresponds to a storytest o The first row will usually hold the SuiteSetUp
 * tables, which will register a new FixtureSupplier o It uses BatchFitLibrary
 * to doTables()
 */
public class SpecifySuiteFixture extends SpecifyFixture {
	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		for (int rowNo = 1; rowNo < table.size(); rowNo++) {
			Row row = table.at(rowNo);
			if (row.size() < 2)
				row.error(testResults, new RowWrongWidthException(2));
			Cell testCell = row.at(0);
			Cell reportCell = row.at(1);
			if (!testCell.hasEmbeddedTables(this)) {
				row.error(testResults, new NestedTableExpectedException());
				return null;
			}
			Tables actualTables = testCell.getEmbeddedTables();
			Tables expectedTables = reportCell.getEmbeddedTables();

			runner.doStorytest(actualTables);
			if (tablesCompare.tablesEqual("", actualTables, expectedTables)) {
				reportCell.pass(testResults);
				testResults.addRights(cellCount(actualTables) - 1);
			} else {
				reportCell.fail(testResults);
				errorReport.actualResult(actualTables);
			}
		}
		return null;
	}
}

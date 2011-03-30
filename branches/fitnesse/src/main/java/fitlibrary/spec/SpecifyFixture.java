/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.spec;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.runResults.TestResults;
import fitlibrary.suite.BatchFitLibrary;
import fitlibrary.suite.StorytestRunner;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.table.Tables;
import fitlibrary.traverse.Traverse;

public class SpecifyFixture extends Traverse {
	protected final StorytestRunner runner;
	protected final SpecifyErrorReport errorReport;
	protected final TablesCompare tablesCompare;

	public SpecifyFixture() {
		this.runner = new BatchFitLibrary();
		errorReport = new SpecifyErrorReported(this);
		tablesCompare = new TablesCompare(errorReport, this);
	}

	public SpecifyFixture(StorytestRunner runner, SpecifyErrorReport errorReport) {
		this.runner = runner;
		this.errorReport = errorReport;
		tablesCompare = new TablesCompare(errorReport, this);
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		try {
			Cell actualCell = table.at(1).at(0);
			if (actualCell.isEmpty())
				throw new FitLibraryException("Missing nested tables to be run");
			Cell expectedCell = expectedOf(table);
			Tables expectedTables = expectedCell.getEmbeddedTables();
			Tables actualTables = actualCell.getEmbeddedTables();
			runner.doStorytest(actualTables);
			if (tablesCompare.tablesEqual("", actualTables, expectedTables)) {
				expectedCell.pass(testResults);
				testResults.addRights(cellCount(actualTables) - 1);
			} else {
				expectedCell.fail(testResults);
				errorReport.actualResult(actualTables);
			}
		} catch (Exception e) {
			table.error(testResults, e);
		}
		return null;
	}

	private Cell expectedOf(Table table) {
		if (table.size() == 2 && table.at(1).size() == 2)
			return table.at(1).at(1);
		if (table.size() == 3 && table.at(1).size() == 1 && table.at(2).size() == 1)
			return table.at(2).at(0);
		throw new FitLibraryException("Table must have one row with two cells or two rows with one cell");
	}

	protected int cellCount(Tables tables) {
		int count = 0;
		for (Table table : tables)
			for (Row row : table)
				for (Cell cell : row) {
					count++;
					count += cellCount(cell);
				}
		return count;
	}
}

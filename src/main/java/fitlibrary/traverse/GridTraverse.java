/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse;

import fitlibrary.parser.Parser;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;

public class GridTraverse extends Traverse {
	private Object[][] grid;
	private Parser parser;

	public GridTraverse(Object sut) {
		super(sut);
	}

	public void setGrid(Object[][] grid) {
		this.grid = grid;
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		parser = asTyped(grid).getComponentTyped().getComponentTyped().parser(this);
		if (grid.length == 0 && table.size() == 1)
			table.pass(testResults);
		else if (!rowsMatch(grid, table, testResults))
			addActualRows(table, grid);
		return grid;
	}

	private boolean rowsMatch(Object[][] actual, Table table, TestResults testResults) {
		boolean matched = true;
		for (int rowNo = 0; rowNo < actual.length; rowNo++) {
			if (!table.atExists(rowNo + 1))
				return false;
			Row row = table.at(rowNo + 1);
			if (!cellsMatch(actual[rowNo], row, testResults))
				matched = false;
		}
		for (int rowNo = actual.length + 1; rowNo < table.size(); rowNo++) {
			matched = false;
			table.at(rowNo).fail(testResults);
		}
		return matched;
	}

	private boolean cellsMatch(Object[] actual, Row row, TestResults testResults) {
		boolean matched = true;
		for (int i = 0; i < actual.length; i++) {
			if (!row.atExists(i))
				return false;
			if (!cellMatches(actual[i], row.at(i), testResults))
				matched = false;
		}
		for (int cellNo = actual.length; cellNo < row.size(); cellNo++) {
			matched = false;
			row.at(cellNo).fail(testResults);
		}
		return matched;
	}

	private boolean cellMatches(Object actual, Cell cell, TestResults testResults) {
		boolean matches = false;
		try {
			matches = parser.matches(cell, actual, testResults);
		} catch (Exception e) {
			// Doesn't match
		}
		if (matches)
			cell.pass(testResults);
		else
			cell.fail(testResults);
		return matches;
	}

	private void addActualRows(Table table, Object[][] actual) {
		int cols = 0;
		for (Row row : table)
			cols = Math.max(cols, row.size());
		for (int i = 0; i < actual.length; i++)
			cols = Math.max(cols, actual[i].length);
		table.newRow().addCell("<i>Actuals:</i>", cols);
		for (int i = 0; i < actual.length; i++) {
			makeRow(table.newRow(), actual[i]);
		}
	}

	private void makeRow(Row row, Object[] actuals) {
		if (actuals.length == 0)
			throw new RuntimeException("Actuals row empty");
		for (int i = 0; i < actuals.length; i++)
			row.add(cellWithValue(actuals[i]));
	}

	private Cell cellWithValue(Object object) {
		Cell cell = TableFactory.cell("");
		try {
			cell.setUnvisitedText(parser.show(object));
		} catch (Exception e) {
			cell.setUnvisitedText(e.toString());
		}
		return cell;
	}
}

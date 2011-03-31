/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse;

import fit.Fixture;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;

public class FitHandler {
	public void doTable(Fixture fixture, Table table, TestResults testResults, Evaluator evaluator) {
		fixture.counts = testResults.getCounts();
		substituteDynamicVariables(table, evaluator);
		// System.out.println("FitHandler before: "+table.getClass()+" "+table);
		fixture.doTable(table.asParse());
		// System.out.println("FitHandler after: "+table.getClass()+" "+table);
	}

	private void substituteDynamicVariables(Table table, Evaluator evaluator) {
		for (int r = 0; r < table.size(); r++) {
			Row row = table.at(r);
			for (int c = 0; c < row.size(); c++) {
				Cell cell = row.at(c);
				String text = cell.text(evaluator);
				if (!text.equals(cell.text()))
					cell.setText(text);
			}
		}
	}
}

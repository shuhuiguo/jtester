/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.collection.map;

import java.util.List;
import java.util.Map;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.parser.Parser;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.traverse.Traverse;

public class ListOfMapsTraverse extends Traverse {
	private final List<Map<String, Object>> maps;

	public ListOfMapsTraverse(List<Map<String, Object>> maps) {
		this.maps = maps;
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		Row labelRow = table.at(1);
		for (int r = 2; r < table.size(); r++) {
			Row row = table.at(r);
			try {
				if (row.size() != labelRow.size()) {
					row.error(testResults, new FitLibraryException("Row is wrong length"));
					break;
				}
				int mapNo = r - 2;
				if (mapNo >= maps.size())
					throw new FitLibraryException("Extra");
				Map<String, Object> map = maps.get(mapNo);
				processRow(labelRow, row, map, testResults);
			} catch (Exception e) {
				row.error(testResults, e);
			}
		}
		return null;
	}

	private void processRow(Row labelRow, Row row, Map<String, Object> map, TestResults testResults) {
		for (int c = 0; c < labelRow.size(); c++) {
			Cell cell = row.at(c);
			try {
				String key = labelRow.text(c, this);
				Object value = map.get(key);
				if (value != null) {
					Parser parser = asTyped(value).parser(this);
					Object actual = parser.parseTyped(cell, testResults).getSubject();
					if (parser.matches(cell, value, testResults))
						cell.pass(testResults);
					else
						cell.fail(testResults, parser.show(actual), this);
				} else {
					if ("".equals(cell.text(this)))
						cell.pass(testResults);
					else
						cell.fail(testResults, "", this);
				}
			} catch (Exception e) {
				cell.error(testResults, e);
			}
		}
	}
}

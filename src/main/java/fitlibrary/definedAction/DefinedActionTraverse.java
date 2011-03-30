/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
/*
 * Includes extension by Jacques Morel to allow for tables to be passed as parameters
 */
package fitlibrary.definedAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.runResults.TableListener;
import fitlibrary.runResults.TestResults;
import fitlibrary.suite.BatchFitLibrary;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;
import fitlibrary.traverse.Traverse;

public class DefinedActionTraverse extends Traverse {
	private Tables body;

	public DefinedActionTraverse() {
		//
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		// Just check the parameter names here. Things happen in the call...
		Row header = table.at(1);
		Set<String> parameterNames = new HashSet<String>();
		for (int c = 0; c < header.size(); c++) {
			Cell parameterCell = header.at(c);
			String parameterName = parameterCell.text(this);
			if (parameterNames.contains(parameterName))
				parameterCell.error(testResults, new FitLibraryException("Duplicate parameter names"));
			parameterNames.add(parameterName);
		}
		return null;
	}

	protected DefinedActionTraverse(Table defTable, int parameterCount) {
		Row header = defTable.at(1);
		if (header.size() != parameterCount)
			throw new FitLibraryException("Mismatch in number of parameters to template");
		Map<String, Object> mapToRef = new HashMap<String, Object>();
		for (int c = 0; c < header.size(); c++)
			mapToRef.put(header.text(c, this), paramRef(c));
		body = TableFactory.tables(defTable.at(2).at(0).getEmbeddedTables());
		macroReplace(body, mapToRef);
	}

	public Tables call(List<Object> parameters, TestResults results) {
		Tables copy = TableFactory.tables(body);
		substitute(parameters, copy);
		executeInstantiatedAction(results, copy);
		return copy;
	}

	// Added for Jacques Morel
	protected void executeInstantiatedAction(TestResults results, Tables copy) {
		new BatchFitLibrary(new TableListener(results)).doTables(copy);
	}

	private void substitute(List<Object> parameters, Tables copy) {
		Map<String, Object> mapFromRef = new HashMap<String, Object>();
		for (int i = 0; i < parameters.size(); i++)
			mapFromRef.put(paramRef(i), parameters.get(i));
		macroReplace(copy, mapFromRef);
	}

	private void macroReplace(Tables tables, Map<String, Object> mapToRef) {
		List<String> reverseSortOrder = new ArrayList<String>(mapToRef.keySet());
		Collections.sort(reverseSortOrder, new Comparator<String>() {
			// @Override
			public int compare(String arg0, String arg1) {
				return arg1.compareTo(arg0);
			}
		});
		for (Table table : tables) {
			for (Row row : table) {
				for (Cell cell : row) {
					String text = cell.text(this);
					for (String key : reverseSortOrder) {
						if (text.contains(key)) {
							Object value = mapToRef.get(key);
							if (value instanceof Table) {
								cell.setInnerTables(TableFactory.tables((Table) value));
							} else {
								text = text.replaceAll(key, (String) value);
								cell.setText(text);
							}
						}
					}
				}
			}
		}
	}

	private static String paramRef(int c) {
		return "%__%" + c + "%__%";
	}
}

/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.collection.map;

import java.util.HashMap;
import java.util.Map;

import fitlibrary.exception.table.ExtraCellsException;
import fitlibrary.parser.Parser;
import fitlibrary.runResults.TestResults;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.traverse.Traverse;
import fitlibrary.typed.Typed;

public class MapTraverse extends Traverse {
	private Map<Object, Object> map;
	protected Parser keyParser, valueParser;

	public MapTraverse(Map<Object, Object> map) {
		this.map = map;
	}

	public MapTraverse(Map<Object, Object> map, Typed keyTyped, Typed valueTyped, RuntimeContextInternal runtimeContext) {
		this(map);
		this.runtimeContext = runtimeContext;
		this.keyParser = keyTyped.parser(this);
		this.valueParser = valueTyped.parser(this);
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		if (map.isEmpty()) {
			if (table.size() == 1)
				table.pass(testResults);
			else
				for (int rowNo = 1; rowNo < table.size(); rowNo++)
					table.at(rowNo).missing(testResults);
		} else {
			// Base the parsing on some element of the map
			determineTypes();
			Map<Object, Object> copiedMap = new HashMap<Object, Object>(map);
			for (int rowNo = 1; rowNo < table.size(); rowNo++) {
				interpret(table.at(rowNo), copiedMap, keyParser, testResults);
			}
			addSurplusRows(table, copiedMap, testResults);
		}
		return map;
	}

	protected void determineTypes() {
		if (keyParser != null)
			return;
		Object someKey = map.keySet().iterator().next();
		keyParser = asTyped(someKey).parser(this);
		valueParser = asTyped(map.get(someKey)).parser(this);
	}

	private void interpret(Row row, Map<Object, Object> copiedMap, Parser keyParser2, TestResults testResults) {
		try {
			if (row.size() > 2)
				throw new ExtraCellsException("MapTraverse");
			Object key = keyParser2.parseTyped(row.at(0), testResults).getSubject();
			Object value = copiedMap.get(key);
			if (value == null)
				row.at(0).expectedElementMissing(testResults);
			else {
				Parser valueParser3 = asTyped(value).parser(this);
				if (valueParser3.matches(row.at(1), value, testResults))
					row.pass(testResults);
				else
					row.at(1).fail(testResults, valueParser3.show(value), this);
			}
			copiedMap.remove(key);
		} catch (Exception e) {
			row.error(testResults, e);
		}
	}

	protected void addSurplusRows(Table table, Map<Object, Object> surplus, TestResults testResults) {
		for (Object key : surplus.keySet()) {
			Object value = surplus.get(key);
			Row row = table.newRow();
			try {
				row.addCell(keyParser.show(key));
				row.addCell(valueParser.show(value));
				row.at(0).actualElementMissing(testResults);
			} catch (Exception e) {
				if (row.isEmpty())
					table.error(testResults, e);
				else
					row.error(testResults, e);
			}
		}
	}
}

/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.collection.map;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import fitlibrary.exception.table.RowWrongWidthException;
import fitlibrary.log.FitLibraryLogger;
import fitlibrary.parser.Parser;
import fitlibrary.runResults.TestResults;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.traverse.Traverse;
import fitlibrary.typed.Typed;

public class MapSetUpTraverse extends Traverse {
	private static Logger logger = FitLibraryLogger.getLogger(MapSetUpTraverse.class);
	private Map<Object, Object> theMap = new HashMap<Object, Object>();
	protected Parser keyParser;
	protected Parser valueParser;

	protected MapSetUpTraverse() {
		//
	}

	public MapSetUpTraverse(Typed keyTyped, Typed valueTyped, RuntimeContextInternal runtimeContext) {
		this.runtimeContext = runtimeContext;
		this.keyParser = keyTyped.parser(this);
		this.valueParser = valueTyped.parser(this);
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		try {
			for (int rowNo = 1; rowNo < table.size(); rowNo++)
				processRow(table.at(rowNo), testResults);
		} catch (Exception e) {
			table.error(testResults, e);
		}
		return theMap;
	}

	protected void processRow(Row row, TestResults testResults) throws Exception {
		try {
			if (row.size() != 2)
				throw new RowWrongWidthException(2);
			Object key = keyParser.parseTyped(row.at(0), testResults).getSubject();
			Object value = valueParser.parseTyped(row.at(1), testResults).getSubject();
			logger.trace("Put in map: " + key + " -> " + value);
			theMap.put(key, value);
		} catch (Exception e) {
			row.error(testResults, e);
		}
	}

	public Map<Object, Object> getResults() {
		return theMap;
	}
}

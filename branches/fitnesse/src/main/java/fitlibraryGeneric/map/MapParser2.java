/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibraryGeneric.map;

import java.util.Map;

import fitlibrary.collection.map.MapTraverse;
import fitlibrary.parser.Parser;
import fitlibrary.parser.collection.MapParser;
import fitlibrary.parser.lookup.ParserFactory;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Table;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.Typed;
import fitlibraryGeneric.typed.GenericTyped;

public class MapParser2 extends MapParser {
	public MapParser2(Evaluator evaluator, GenericTyped typed) {
		super(evaluator, typed, typed.getComponentTyped(0), typed.getComponentTyped(1));
	}

	@Override
	protected boolean tableMatches(Table table, Map<Object, Object> map, TestResults testResults) {
		MapTraverse mapCheck = new MapTraverse(map, keyTyped, valueTyped, evaluator.getRuntimeContext());
		return mapCheck.doesInnerTablePass(table, evaluator.getRuntimeContext(), testResults);
	}

	public static ParserFactory parserFactory() {
		return new ParserFactory() {
			// @Override
			public Parser parser(Evaluator evaluator, Typed typed) {
				if (typed instanceof GenericTyped && typed.isGeneric())
					return new MapParser2(evaluator, (GenericTyped) typed);
				return new MapParser(evaluator, typed);
			}
		};
	}
}

/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import fitlibrary.collection.map.MapSetUpTraverse;
import fitlibrary.collection.map.MapTraverse;
import fitlibrary.exception.parse.InvalidMapString;
import fitlibrary.parser.Parser;
import fitlibrary.parser.lookup.ParserFactory;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.Traverse;
import fitlibrary.typed.NonGenericTyped;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;

/**
 * We have to assume that the Map is a Map<String,String>
 */
public class MapParser implements Parser {
	protected final Parser parser, showParser;
	protected final Evaluator evaluator;
	protected final Typed typed;
	protected Typed keyTyped = new NonGenericTyped(String.class);
	protected Typed valueTyped = new NonGenericTyped(String.class);

	public static boolean applicableType(Class<?> type) {
		return Map.class.isAssignableFrom(type);
	}

	public MapParser(Evaluator evaluator, Typed typed) {
		this(evaluator, typed, new NonGenericTyped(String.class), new NonGenericTyped(String.class));
	}

	public MapParser(Evaluator evaluator, Typed typed, Typed keyTyped, Typed valueTyped) {
		this.evaluator = evaluator;
		this.typed = typed;
		this.keyTyped = keyTyped;
		this.valueTyped = valueTyped;
		parser = Traverse.asTyped(String.class).resultParser(evaluator);
		showParser = Traverse.asTyped(Object.class).resultParser(evaluator);
	}

	// @Override
	public TypedObject parseTyped(Cell cell, TestResults testResults) throws Exception {
		return typed.typedObject(parse(cell, testResults));
	}

	private Object parse(Cell cell, TestResults testResults) throws Exception {
		if (cell.hasEmbeddedTables(evaluator))
			return parseTable(cell.getEmbeddedTable(), testResults);
		return parse(cell.text(evaluator), testResults);
	}

	protected Object parseTable(Table table, TestResults testResults) {
		MapSetUpTraverse setUp = new MapSetUpTraverse(keyTyped, valueTyped, evaluator.getRuntimeContext());
		setUp.interpretInnerTableWithInScope(table, evaluator.getRuntimeContext(), testResults);
		return setUp.getResults();
	}

	// @Override
	@SuppressWarnings("unchecked")
	public boolean matches(Cell cell, Object result, TestResults testResults) throws Exception {
		if (result == null)
			return !cell.hasEmbeddedTables(evaluator) && cell.isBlank(evaluator);
		Map<Object, Object> map = (Map<Object, Object>) result;
		if (cell.hasEmbeddedTables(evaluator))
			return tableMatches(cell.getEmbeddedTable(), map, testResults);
		return parse(cell, testResults).equals(result);
	}

	protected boolean tableMatches(Table table, Map<Object, Object> map, TestResults testResults) {
		Traverse traverse = new MapTraverse(map);
		return traverse.doesInnerTablePass(table, evaluator.getRuntimeContext(), testResults);
	}

	private Object parse(String s, TestResults testResults) throws Exception {
		StringTokenizer t = new StringTokenizer(s, ",");
		Map<Object, Object> map = new HashMap<Object, Object>();
		while (t.hasMoreTokens()) {
			String mapString = t.nextToken();
			String[] split = mapString.split("->");
			if (split.length != 2)
				throw new InvalidMapString(mapString);
			map.put(parser.parseTyped(TableFactory.cell(split[0]), testResults).getSubject(),
					parser.parseTyped(TableFactory.cell(split[1]), testResults).getSubject());
		}
		return map;
	}

	// @Override
	public String show(Object object) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		if (object == null)
			return "";
		String result = "";
		boolean first = true;
		Map<?, ?> map = (Map<?, ?>) object;
		for (Object key : map.keySet()) {
			String element = showParser.show(key) + "->" + map.get(key);
			if (first)
				first = false;
			else
				result += ", ";
			result += element;
		}
		return result;
	}

	public static ParserFactory parserFactory() {
		return new ParserFactory() {
			// @Override
			public Parser parser(Evaluator evaluator, Typed typed) {
				return new MapParser(evaluator, typed);
			}
		};
	}

	// @Override
	@SuppressWarnings("unchecked")
	public Evaluator traverse(TypedObject typedObject) {
		return new MapTraverse((Map<Object, Object>) typedObject.getSubject());
	}
}

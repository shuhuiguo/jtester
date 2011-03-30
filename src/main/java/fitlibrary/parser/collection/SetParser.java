/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.collection;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import fitlibrary.collection.CollectionSetUpTraverse;
import fitlibrary.collection.CollectionTraverse;
import fitlibrary.collection.set.SetTraverse;
import fitlibrary.parser.Parser;
import fitlibrary.parser.lookup.ParserFactory;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.FitLibrarySelector;
import fitlibrary.traverse.Traverse;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;

public class SetParser implements Parser {
	protected final Parser parser, showParser;
	protected final Evaluator evaluator;
	protected final Typed typed;

	public static boolean applicableType(Class<?> type) {
		return Set.class.isAssignableFrom(type) || Map.class.isAssignableFrom(type);
	}

	public SetParser(Evaluator evaluator, Typed typed) {
		this.evaluator = evaluator;
		this.typed = typed;
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
		Set<Object> set = new HashSet<Object>();
		CollectionSetUpTraverse setUp = new CollectionSetUpTraverse(evaluator, set, true);
		setUp.interpretInnerTableWithInScope(table, evaluator.getRuntimeContext(), testResults);
		return set;
	}

	// @Override
	public boolean matches(Cell cell, Object result, TestResults testResults) throws Exception {
		if (result == null)
			return !cell.hasEmbeddedTables(evaluator) && cell.isBlank(evaluator);
		if (cell.hasEmbeddedTables(evaluator))
			return tableMatches(cell.getEmbeddedTable(), result, testResults);
		return parse(cell, testResults).equals(result);
	}

	protected boolean tableMatches(Table table, Object result, TestResults testResults) {
		CollectionTraverse traverse = FitLibrarySelector.selectSet(result);
		return traverse.doesInnerTablePass(table, evaluator.getRuntimeContext(), testResults);
	}

	private Object parse(String s, TestResults testResults) throws Exception {
		StringTokenizer t = new StringTokenizer(s, ",");
		Set<Object> set = new HashSet<Object>();
		while (t.hasMoreTokens())
			set.add(parser.parseTyped(TableFactory.cell(t.nextToken()), testResults).getSubject());
		return set;
	}

	// @Override
	public String show(Object object) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		if (object == null)
			return "";
		String result = "";
		boolean first = true;
		Set<?> set = (Set<?>) object;
		for (Object el : set) {
			String element = showParser.show(el);
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
				return new SetParser(evaluator, typed);
			}
		};
	}

	// @Override
	public Evaluator traverse(TypedObject typedObject) {
		return new SetTraverse(typedObject);
	}
}

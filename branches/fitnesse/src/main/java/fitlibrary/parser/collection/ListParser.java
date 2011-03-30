/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.collection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import fitlibrary.collection.CollectionSetUpTraverse;
import fitlibrary.collection.CollectionTraverse;
import fitlibrary.collection.list.ListTraverse;
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
import fitlibrary.utility.CollectionUtility;

public class ListParser implements Parser {
	protected Parser valueParser, showParser;
	protected final Evaluator evaluator;
	private final Typed typed;

	public ListParser(Evaluator evaluator, Typed typed) {
		if (evaluator.getRuntimeContext() == null)
			throw new NullPointerException("Runtime is null");
		this.evaluator = evaluator;
		this.typed = typed;
		valueParser = Traverse.asTyped(String.class).resultParser(evaluator);
		showParser = Traverse.asTyped(Object.class).resultParser(evaluator);
	}

	public static boolean applicableType(Class<?> type) {
		return Collection.class.isAssignableFrom(type) || Iterator.class.isAssignableFrom(type) || type.isArray();
	}

	// @Override
	public TypedObject parseTyped(Cell cell, TestResults testResults) throws Exception {
		return typed.typedObject(parse(cell, testResults));
	}

	private Object parse(Cell cell, TestResults testResults) throws Exception {
		List<Object> results = null;
		if (cell.hasEmbeddedTables(evaluator))
			results = parseTable(cell.getEmbeddedTable(), testResults);
		else
			results = parse(cell.text(evaluator), testResults);
		if (typed.isArray())
			return asArray(typed.getComponentTyped().asClass(), results);
		if (isIterator())
			return results.iterator();
		return results;
	}

	private boolean isIterator() {
		return Iterator.class.isAssignableFrom(typed.asClass());
	}

	protected List<Object> parseTable(Table table, TestResults testResults) {
		List<Object> list = new ArrayList<Object>();
		CollectionSetUpTraverse setUp = new CollectionSetUpTraverse(evaluator, list, true);
		setUp.interpretInnerTableWithInScope(table, evaluator.getRuntimeContext(), testResults);
		return list;
	}

	private Object asArray(Class<?> componentType, List<Object> results) {
		Object array = Array.newInstance(componentType, results.size());
		int i = 0;
		for (Iterator<Object> it = results.iterator(); it.hasNext();)
			Array.set(array, i++, it.next());
		return array;
	}

	// @Override
	public boolean matches(Cell cell, Object actual, TestResults testResults) throws Exception {
		if (actual == null)
			return !cell.hasEmbeddedTables(evaluator) && cell.isBlank(evaluator);
		if (cell.hasEmbeddedTables(evaluator))
			return tableMatches(cell.getEmbeddedTable(), actual, testResults);
		Object expected = parse(cell, testResults);
		if (isIterator())
			return CollectionUtility.equalsIterator((Iterator<?>) expected, ((List<?>) actual).iterator());
		if (typed.isArray())
			return Arrays.equals((Object[]) expected, (Object[]) actual);
		return expected.equals(actual);
	}

	protected boolean tableMatches(Table table, Object result, TestResults testResults) {
		CollectionTraverse traverse = FitLibrarySelector.selectOrderedList(result);
		return traverse.doesInnerTablePass(table, evaluator.getRuntimeContext(), testResults);
	}

	protected List<Object> parse(String s, TestResults testResults) throws Exception {
		StringTokenizer tokeniser = new StringTokenizer(s, ",");
		List<Object> list = new ArrayList<Object>();
		while (tokeniser.hasMoreTokens())
			list.add(valueParser.parseTyped(TableFactory.cell(tokeniser.nextToken()), testResults).getSubject());
		return list;
	}

	@SuppressWarnings("unchecked")
	// @Override
	public String show(Object object) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		String result = "";
		if (object == null)
			return result;
		Iterator<Object> it;
		if (object.getClass().isArray())
			it = Arrays.asList((Object[]) object).iterator();
		else if (isIterator())
			it = ((List<Object>) object).iterator();
		else
			it = ((List<Object>) object).iterator();
		boolean first = true;
		while (it.hasNext()) {
			String element = showParser.show(it.next());
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
				return new ListParser(evaluator, typed);
			}
		};
	}

	// @Override
	public Evaluator traverse(TypedObject typedObject) {
		return new ListTraverse(typedObject);
	}
}

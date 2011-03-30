/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.collection;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import fitlibrary.collection.array.ArraySetUpTraverse;
import fitlibrary.collection.array.ArrayTraverse;
import fitlibrary.exception.FitLibraryException;
import fitlibrary.parser.Parser;
import fitlibrary.parser.lookup.ParserFactory;
import fitlibrary.runResults.TestResults;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Cell;
import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.Traverse;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;
import fitlibrary.utility.ClassUtility;

public class ArrayParser implements Parser {
	protected final Parser componentParser;
	protected Typed componentType;
	final private RuntimeContextInternal runtime;

	public ArrayParser(Evaluator evaluator, Typed typed) {
		this.runtime = evaluator.getRuntimeContext();
		this.componentType = typed.getComponentTyped();
		componentParser = componentType.resultParser(evaluator);
	}

	public static boolean applicableType(Class<?> type) {
		return type.isArray()
				&& (ClassUtility.isEffectivelyPrimitive(type.getComponentType()) || type.getComponentType().isArray());
	}

	// @Override
	public TypedObject parseTyped(Cell cell, TestResults testResults) throws Exception {
		return componentType.typedObject(parse(cell, testResults));
	}

	private Object parse(Cell cell, TestResults testResults) throws Exception {
		if (cell.hasEmbeddedTables(runtime))
			return parseTable(cell.getEmbeddedTable(), testResults);
		return parse(cell.text(runtime), testResults);
	}

	protected Object parseTable(Table table, TestResults testResults) {
		ArraySetUpTraverse setUp = new ArraySetUpTraverse(componentType.asClass(), componentParser);
		setUp.interpretWithinScope(table, runtime, testResults);
		return setUp.getResults();
	}

	// @Override
	public boolean matches(Cell cell, Object result, TestResults testResults) throws Exception {
		if (cell.hasEmbeddedTables(runtime))
			return tableMatches(cell.getEmbeddedTable(), result, testResults);
		return equals(parse(cell, testResults), result, testResults);
	}

	protected boolean tableMatches(Table table, Object results, TestResults testResults) {
		if (results instanceof Object[]) {
			Object[] array = (Object[]) results;
			if (array.getClass().getComponentType().isArray()) {
				Traverse nestingArray = new ArrayTraverse(array, true);
				return nestingArray.doesTablePass(table, runtime, testResults);
			}
			ArrayTraverse traverse = new ArrayTraverse(array);
			return traverse.doesInnerTablePass(table, runtime, testResults);
		}
		Traverse traverse = selectPrimitiveArray(results);
		return traverse.doesInnerTablePass(table, runtime, testResults);
	}

	public static Traverse selectPrimitiveArray(Object array) {
		if (array.getClass().isArray()) {
			if (ClassUtility.isEffectivelyPrimitive(array.getClass().getComponentType()))
				return new ArrayTraverse(array);
			if (array.getClass().getComponentType().isArray())
				return new ArrayTraverse(array);
			List<?> asList = Arrays.asList((Object[]) array);
			return new ArrayTraverse(asArray(asList));
		}
		if (array instanceof Collection)
			return new ArrayTraverse(asArray((Collection<?>) array));
		throw new FitLibraryException("Object is not an array or collection, but is of " + array.getClass());
	}

	private static String[] asArray(Collection<?> collection) {
		String[] asArray = new String[collection.size()];
		int i = 0;
		for (Iterator<?> it = collection.iterator(); it.hasNext(); i++)
			asArray[i] = it.next().toString();
		return asArray;
	}

	private Object parse(String s, TestResults testResults) throws Exception {
		StringTokenizer t = new StringTokenizer(s, ",");
		Object array = Array.newInstance(componentType.asClass(), t.countTokens());
		for (int i = 0; t.hasMoreTokens(); i++)
			Array.set(array, i, componentParser.parseTyped(TableFactory.cell(t.nextToken()), testResults).getSubject());
		return array;
	}

	// @Override
	public String show(Object o) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		if (o == null)
			return "";
		int length = Array.getLength(o);
		StringBuffer b = new StringBuffer(5 * length);
		for (int i = 0; i < length; i++) {
			String element = componentParser.show(Array.get(o, i));
			b.append(element);
			if (i < (length - 1))
				b.append(", ");
		}
		return b.toString();
	}

	private boolean equals(Object a, Object b, TestResults testResults) {
		int length = Array.getLength(a);
		if (length != Array.getLength(b))
			return false;
		for (int i = 0; i < length; i++) {
			try {
				if (!componentParser.matches(TableFactory.cell(Array.get(a, i).toString()), Array.get(b, i),
						testResults))
					return false;
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	public static ParserFactory parserFactory() {
		return new ParserFactory() {
			// @Override
			public Parser parser(Evaluator evaluator, Typed typed) {
				return new ArrayParser(evaluator, typed);
			}
		};
	}

	// @Override
	public Evaluator traverse(TypedObject object) {
		return new ArrayTraverse(object.getSubject());
	}
}

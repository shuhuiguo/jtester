/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.table;

import fit.Parse;
import fitlibrary.dynamicVariable.GlobalDynamicVariables;
import fitlibrary.exception.table.MissingTableException;
import fitlibrary.parser.HtmlStructureParser;
import fitlibrary.parser.Parser;
import fitlibrary.parser.lookup.ParserFactory;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;

public class TableParser extends HtmlStructureParser {
	public TableParser(Typed typed) {
		super(typed);
	}

	public static boolean applicableType(Class<?> type) {
		return fitlibrary.parser.table.TableInterface.class.isAssignableFrom(type);
	}

	@Override
	protected Object parse(Cell cell, TestResults testResults) throws Exception {
		if (!cell.hasEmbeddedTables(new GlobalDynamicVariables()))
			throw new MissingTableException();
		Parse parse = cell.getEmbeddedTable().asParse();
		Object[] args = new Object[] { parse };
		Class<?>[] argTypes = new Class[] { Parse.class };
		return callReflectively("parseTable", args, argTypes, null);
	}

	// @Override
	public String show(Object object) {
		if (object == null)
			return "null";
		return callReflectively("toTable", new Object[] {}, new Class[] {}, object).toString();
	}

	@Override
	protected boolean areEqual(Object expected, Object actual) {
		if (expected == null)
			return actual == null;
		return Table.equals(expected, actual);
	}

	// Is registered in LibraryTypeAdapter.on()
	public static ParserFactory parserFactory() {
		return new ParserFactory() {
			// @Override
			public Parser parser(Evaluator evaluator, Typed typed) {
				return new TableParser(typed);
			}
		};
	}

	// @Override
	public Evaluator traverse(TypedObject typedObject) {
		throw new RuntimeException("No Traverse available");
	}
}

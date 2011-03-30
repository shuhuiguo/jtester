/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.tree;

import fitlibrary.parser.HtmlStructureParser;
import fitlibrary.parser.Parser;
import fitlibrary.parser.lookup.ParserFactory;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;

/**
 * A Parser that handles Tree values instead of Strings
 */
public class TreeParser extends HtmlStructureParser {
	public TreeParser(Typed typed) {
		super(typed);
	}

	public static boolean applicableType(Class<?> type) {
		return fitlibrary.parser.tree.TreeInterface.class.isAssignableFrom(type);
	}

	protected Object parse(String s) {
		ListTree tree = ListTree.parse(s);
		Object[] args = new Object[] { tree };
		Class<?>[] argTypes = new Class[] { Tree.class };
		return callReflectively("parseTree", args, argTypes, null);
	}

	// @Override
	public String show(Object object) {
		if (object == null)
			return "null";
		return callReflectively("toTree", new Object[] {}, new Class[] {}, object).toString();
	}

	@Override
	protected boolean areEqual(Object a, Object b) {
		if (a == null)
			return b == null;
		return ListTree.equals(a, b);
	}

	// Is registered in ValueAdapter.on()
	@Override
	public Object parse(Cell cell, TestResults testResults) throws Exception {
		return parse(cell.fullText());
	}

	public static ParserFactory parserFactory() {
		return new ParserFactory() {
			// @Override
			public Parser parser(Evaluator evaluator, Typed typed) {
				return new TreeParser(typed);
			}
		};
	}

	// @Override
	public Evaluator traverse(TypedObject typedObject) {
		throw new RuntimeException("No Traverse available");
	}
}

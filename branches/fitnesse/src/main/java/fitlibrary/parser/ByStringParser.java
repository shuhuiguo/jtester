/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser;

import fitlibrary.dynamicVariable.VariableResolver;
import fitlibrary.parser.lookup.ParserFactory;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.Traverse;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;

/**
 * This Parser is used when there is nothing specific available. As it's just
 * for method results, a String compare will have to do. It may end up being
 * inconsistent with references, but let's wait ans see.
 */
public class ByStringParser implements Parser {
	private VariableResolver resolver;

	public ByStringParser(VariableResolver resolver) {
		this.resolver = resolver;
	}

	// @Override
	public String show(Object actual) {
		if (actual == null)
			return "null";
		return actual.toString();
	}

	// @Override
	public TypedObject parseTyped(Cell cell, TestResults testResults) throws Exception {
		return Traverse.asTypedObject(parse(cell, testResults));
	}

	private Object parse(Cell cell, TestResults testResults) throws Exception {
		return cell.text(resolver);
	}

	// @Override
	public boolean matches(Cell cell, Object result, TestResults testResults) throws Exception {
		return equals(parse(cell, testResults), result);
	}

	private boolean equals(Object a, Object b) { // a will be a String
		if (a == null)
			return b == null;
		if (b == null)
			return false;
		return a.equals(b.toString());
	}

	public static ParserFactory parserFactory() {
		return new ParserFactory() {
			// @Override
			public Parser parser(Evaluator evaluator, Typed typed) {
				return new ByStringParser(evaluator.getRuntimeContext().getResolver());
			}
		};
	}

	// @Override
	public Evaluator traverse(TypedObject object) {
		throw new RuntimeException("No Traverse available");
	}
}

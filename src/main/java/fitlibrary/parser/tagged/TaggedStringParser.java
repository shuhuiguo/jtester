/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.tagged;

import fitlibrary.parser.HtmlParser;
import fitlibrary.parser.Parser;
import fitlibrary.parser.lookup.ParserFactory;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.Traverse;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;

public class TaggedStringParser implements HtmlParser {
	public static boolean applicableType(Class<?> type) {
		return TaggedString.class.isAssignableFrom(type);
	}

	// @Override
	public String show(Object object) {
		if (object == null)
			return "null";
		return object.toString();
	}

	// @Override
	public TypedObject parseTyped(Cell cell, TestResults testResults) throws Exception {
		return Traverse.asTyped(String.class).typedObject(parse(cell, testResults));
	}

	// Is registered in LibraryTypeAdapter.on()
	public Object parse(Cell cell, TestResults testResults) throws Exception {
		return new TaggedString(cell.fullText());
	}

	// @Override
	public boolean matches(Cell cell, Object result, TestResults testResults) throws Exception {
		return parse(cell, testResults).equals(result);
	}

	public static ParserFactory parserFactory() {
		return new ParserFactory() {
			private TaggedStringParser taggedStringParser = new TaggedStringParser();

			// @Override
			public Parser parser(Evaluator evaluator, Typed typed) {
				return taggedStringParser;
			}
		};
	}

	// @Override
	public Evaluator traverse(TypedObject typedObject) {
		throw new RuntimeException("No Traverse available");
	}
}
/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 12/11/2006
 */

package fitlibrary.parser;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.parser.lookup.ParserFactory;
import fitlibrary.runResults.TestResults;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Cell;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;
import fitlibraryGeneric.typed.GenericTyped;

public class EnumParser implements Parser {
	private GenericTyped typed;
	private RuntimeContextInternal runtime;

	public EnumParser(GenericTyped typed, RuntimeContextInternal runtime) {
		this.typed = typed;
		this.runtime = runtime;
	}

	// @Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TypedObject parseTyped(Cell cell, TestResults testResults) throws Exception {
		String text = cell.text(runtime);
		if (text.equals(""))
			return typed.typedObject(null);
		Class asClass = typed.asClass();
		try {
			String stringValue = runtime.extendedCamel(text.replaceAll("\\s+", "")).toUpperCase();
			return typed.typedObject(Enum.valueOf(asClass, stringValue));
		} catch (IllegalArgumentException e) {
			try {
				return typed.typedObject(Enum.valueOf(asClass, runtime.extendedCamel(text.replaceAll("\\s+", "_"))
						.toUpperCase()));
			} catch (IllegalArgumentException e2) {
				throw new FitLibraryException("Unknown");
			}
		}
	}

	// @Override
	public boolean matches(Cell cell, Object result, TestResults testResults) throws Exception {
		if (cell.hasEmbeddedTables(runtime)) {
			cell.unexpected(testResults, "collection");
			return false;
		}
		if (cell.text(runtime).equals(""))
			return result == null;
		Object parsed = parseTyped(cell, testResults).getSubject();
		return parsed.equals(result);
	}

	// @Override
	public String show(Object result) throws Exception {
		return result.toString();
	}

	public static ParserFactory parserFactory() {
		return new ParserFactory() {
			// @Override
			public Parser parser(Evaluator evaluator, Typed typed) {
				return new EnumParser((GenericTyped) typed, evaluator.getRuntimeContext());
			}
		};
	}

	// @Override
	public Evaluator traverse(TypedObject typedObject) {
		throw new RuntimeException("No Traverse available");
	}
}

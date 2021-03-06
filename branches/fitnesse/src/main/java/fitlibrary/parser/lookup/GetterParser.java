/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.lookup;

import java.lang.reflect.Method;

import fitlibrary.parser.HtmlParser;
import fitlibrary.parser.Parser;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.TypedObject;

public class GetterParser implements ResultParser {
	private Parser parser;
	private Object objectToCall;
	private Method method;

	public GetterParser(Parser parser, Method method) {
		this.parser = parser;
		this.method = method;
	}

	// @Override
	public TypedObject parseTyped(Cell cell, TestResults testResults) throws Exception {
		return parser.parseTyped(cell, testResults);
	}

	// @Override
	public boolean matches(Cell cell, Object result, TestResults testResults) throws Exception {
		return parser.matches(cell, result, testResults);
	}

	// @Override
	public String show(Object result) throws Exception {
		if (result == null)
			return "";
		return parser.show(result);
	}

	// @Override
	public void setTarget(Object element) {
		this.objectToCall = element;
	}

	// @Override
	public Object getResult() throws Exception {
		return method.invoke(objectToCall, new Object[] {});
	}

	// @Override
	public boolean isShowAsHtml() {
		return parser instanceof HtmlParser;
	}

	// @Override
	public Evaluator traverse(TypedObject typedObject) {
		return parser.traverse(typedObject);
	}
}

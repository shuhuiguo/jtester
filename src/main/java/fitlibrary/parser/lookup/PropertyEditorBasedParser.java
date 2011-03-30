/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.lookup;

import java.beans.PropertyEditor;

import fitlibrary.exception.parse.BadNumberException;
import fitlibrary.object.DomainObjectCheckTraverse;
import fitlibrary.object.DomainObjectSetUpTraverse;
import fitlibrary.parser.Parser;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Table;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;

public class PropertyEditorBasedParser implements Parser {
	private PropertyEditor editor;
	private Evaluator evaluator;
	private Typed typed;
	private boolean nullOK;

	public PropertyEditorBasedParser(Evaluator evaluator, Typed typed, PropertyEditor editor, boolean nullOK) {
		this.evaluator = evaluator;
		this.typed = typed;
		this.editor = editor;
		this.nullOK = nullOK;
	}

	// @Override
	public TypedObject parseTyped(Cell cell, TestResults testResults) throws Exception {
		return typed.typedObject(parse(cell, testResults));
	}

	private Object parse(Cell cell, TestResults testResults) throws Exception {
		if (cell.hasEmbeddedTables(evaluator))
			return parseTable(cell.getEmbeddedTable(), testResults);
		if (nullOK && cell.isBlank(evaluator)) {
			return null;
		}
		try {
			return parse(cell.text(evaluator));
		} catch (NumberFormatException e) {
			throw new BadNumberException();
		}
	}

	private Object parse(String text) throws Exception {
		editor.setAsText(text);
		return editor.getValue();
	}

	private Object parseTable(Table embeddedTable, TestResults testResults) throws Exception {
		Object newInstance = typed.newInstance();
		DomainObjectSetUpTraverse setUp = new DomainObjectSetUpTraverse(newInstance);
		setUp.setRuntimeContext(evaluator.getRuntimeContext());
		setUp.callStartCreatingObjectMethod(newInstance);
		setUp.interpretInnerTableWithInScope(embeddedTable, evaluator.getRuntimeContext(), testResults);
		setUp.callEndCreatingObjectMethod(newInstance);
		return newInstance;
	}

	// @Override
	public boolean matches(Cell cell, Object result, TestResults testResults) throws Exception {
		if (cell.hasEmbeddedTables(evaluator))
			return matchesTable(cell.getEmbeddedTable(), result, testResults);
		return matches(parse(cell, testResults), result);
	}

	private boolean matches(Object a, Object b) {
		if (a == null)
			return b == null;
		return a.equals(b);
	}

	private boolean matchesTable(Table table, Object result, TestResults testResults) {
		DomainObjectCheckTraverse traverse = new DomainObjectCheckTraverse(result, typed);
		return traverse.doesInnerTablePass(table, evaluator.getRuntimeContext(), testResults);
	}

	// @Override
	public String show(Object object) {
		editor.setValue(object);
		return editor.getAsText();
	}

	@Override
	public String toString() {
		return "PropertyEditorBasedParser[" + editor + "]";
	}

	// @Override
	public Evaluator traverse(TypedObject typedObject) {
		throw new RuntimeException("No Traverse available");
	}
}

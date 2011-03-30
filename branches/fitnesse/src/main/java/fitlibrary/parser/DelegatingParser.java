/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser;

import fitlibrary.closure.Closure;
import fitlibrary.exception.classes.NoNullaryConstructor;
import fitlibrary.exception.parse.BadNumberException;
import fitlibrary.global.PlugBoard;
import fitlibrary.object.DomainObjectCheckTraverse;
import fitlibrary.object.DomainObjectSetUpTraverse;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Table;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;

public class DelegatingParser implements Parser {
	protected final DelegateParser delegateParser;
	protected final Evaluator evaluator;
	protected final Typed typed;

	public DelegatingParser(DelegateParser delegateParser, Evaluator evaluator, Typed typed) {
		this.delegateParser = delegateParser;
		this.evaluator = evaluator;
		this.typed = typed;
	}

	// @Override
	public TypedObject parseTyped(Cell cell, TestResults testResults) throws Exception {
		return typed.typedObject(parse(cell, testResults));
	}

	private Object parse(Cell cell, TestResults testResults) throws Exception {
		if (cell.hasEmbeddedTables(evaluator))
			return parseTable(cell.getEmbeddedTable(), testResults);
		try {
			return delegateParser.parse(cell.text(evaluator), typed);
		} catch (NumberFormatException e) {
			throw new BadNumberException();
		}
	}

	protected Object parseTable(Table embeddedTable, TestResults testResults) throws Exception {
		Object newInstance = null;
		try {
			newInstance = typed.newInstance();
		} catch (Exception e) {
			Closure fixturingMethod = PlugBoard.lookupTarget.findNewInstancePluginMethod(evaluator);
			if (fixturingMethod == null)
				throw new NoNullaryConstructor(typed.asClass());
			newInstance = fixturingMethod.invoke(new Object[] { typed.asClass() });
		}
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
		return delegateParser.matches(parse(cell, testResults), result);
	}

	protected boolean matchesTable(Table table, Object result, TestResults testResults) {
		DomainObjectCheckTraverse traverse = new DomainObjectCheckTraverse(result, typed);
		return traverse.doesInnerTablePass(table, evaluator.getRuntimeContext(), testResults);
	}

	// @Override
	public String show(Object result) throws Exception {
		return delegateParser.show(result);
	}

	@Override
	public String toString() {
		return delegateParser.toString();
	}

	// @Override
	public Evaluator traverse(TypedObject object) {
		throw new RuntimeException("No Traverse available");
	}
}

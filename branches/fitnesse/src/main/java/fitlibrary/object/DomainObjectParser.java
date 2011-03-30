/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.object;

import fitlibrary.closure.Closure;
import fitlibrary.global.PlugBoard;
import fitlibrary.parser.Parser;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Table;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;

public class DomainObjectParser implements Parser {
	protected Evaluator evaluator;
	private Typed typed;
	private Finder finder;

	public DomainObjectParser(Evaluator evaluator, Typed typed) {
		this.evaluator = evaluator;
		this.typed = typed;
		finder = typed.getFinder(evaluator);
	}

	// @Override
	public TypedObject parseTyped(Cell cell, TestResults testResults) throws Exception {
		return typed.typedObject(parse(cell, testResults));
	}

	private Object parse(Cell cell, TestResults testResults) throws Exception {
		if (cell.hasEmbeddedTables(evaluator))
			return parseTable(cell.getEmbeddedTable(), testResults);
		// if (cell.text().equals("")) // Someone may want to use a pseudo key
		// value of ""
		// return null;
		return finder.find(cell.text(evaluator));
	}

	protected Object parseTable(Table embeddedTable, TestResults testResults) throws Exception {
		TypedObject newInstance = null;
		try {
			newInstance = typed.newTypedInstance();
		} catch (Exception ex) {
			try {
				Closure fixturingMethod = PlugBoard.lookupTarget.findFixturingMethod(evaluator, "newInstancePlugin",
						(new Class[] { Class.class }));
				if (fixturingMethod != null)
					newInstance = typed.typedObject(fixturingMethod.invoke(new Object[] { typed.asClass() }));
			} catch (Exception e) {
				//
			}
			// So instead, we'll try creating it from the class that's specified
			// in the table
		}
		DomainObjectSetUpTraverse setUp = new DomainObjectSetUpTraverse(newInstance, typed);
		setUp.setRuntimeContext(evaluator.getRuntimeContext());

		if (newInstance != null)
			setUp.callStartCreatingObjectMethod(newInstance);
		setUp.interpretInnerTableWithInScope(embeddedTable, evaluator.getRuntimeContext(), testResults);
		return setUp.getSystemUnderTest();
	}

	// @Override
	public boolean matches(Cell cell, Object result, TestResults testResults) throws Exception {
		if (result == null)
			return !cell.hasEmbeddedTables(evaluator) && cell.isBlank(evaluator);
		if (cell.hasEmbeddedTables(evaluator))
			return matchesTable(cell.getEmbeddedTable(), result, testResults);
		return matches(parse(cell, testResults), result);
	}

	protected boolean matchesTable(Table table, Object result, TestResults testResults) {
		DomainObjectCheckTraverse traverse = new DomainObjectCheckTraverse(result, typed);
		return traverse.doesInnerTablePass(table, evaluator.getRuntimeContext(), testResults);
	}

	public boolean matches(Object a, Object b) {
		if (a == null)
			return b == null;
		return a.equals(b);
	}

	// @Override
	public String show(Object result) throws Exception {
		return finder.show(result);
	}

	// @Override
	public Evaluator traverse(TypedObject typedObject) {
		return new DomainObjectCheckTraverse(typedObject);
	}

	public boolean hasFinderMethod() {
		return finder.hasFinderMethod();
	}
}

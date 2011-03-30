/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser;

import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.TypedObject;

/**
 * There are several ways that Parsers are used: o To parse cell values into
 * objects, such as for method arguments o To check that a cell value matches
 * the actual object (with colouring) o To take the result of a method
 * (including a getter) to map it onto a value or table, show it, etc
 * 
 * Some Parsers are used without change (such as those used in ArrayTraverse and
 * subclasses), while others are reused with different targets (such as those
 * used in SetUpTraverse).
 */
public interface Parser {
	TypedObject parseTyped(Cell cell, TestResults testResults) throws Exception;

	boolean matches(Cell cell, Object result, TestResults testResults) throws Exception;

	String show(Object result) throws Exception;

	Evaluator traverse(TypedObject typedObject);
}

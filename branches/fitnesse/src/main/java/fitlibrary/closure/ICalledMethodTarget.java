/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.closure;

import fitlibrary.parser.Parser;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.typed.TypedObject;

public interface ICalledMethodTarget extends MethodTarget {
	boolean checkResult(Cell at, Object result, boolean b, boolean c, TestResults testResults);

	void color(Row row, boolean booleanValue, TestResults testResults) throws Exception;

	Class<?> getOwningClass();

	Parser[] getParameterParsers();

	Class<?>[] getParameterTypes();

	Object getResult(Cell expectedCell, TestResults testResults);

	Parser getResultParser();

	String getResultString(Object result) throws Exception;

	Class<?> getReturnType();

	Object invoke() throws Exception;

	Object invoke(Cell cell, TestResults testResults) throws Exception;

	Object invoke(Object[] arguments) throws Exception;

	Object invoke(Row row, TestResults testResults, boolean catchParseError) throws Exception;

	void invokeAndCheckForSpecial(Row rowFrom, Cell expectedCell, TestResults testResults, Row row, Cell cell);

	void invokeAndCheck(Row row, Cell at, TestResults testResults, boolean b);

	Object invokeForSpecial(Row row, TestResults testResults, boolean catchParseError, Cell operatorCell)
			throws Exception;

	TypedObject invokeTyped(Row fromAt, TestResults testResults) throws Exception;

	void notResult(Cell expectedCell, Object result, TestResults testResults);

	boolean returnsBoolean();

	boolean returnsVoid();

	void setEverySecond(boolean b);

	void setRepeatAndExceptionString(String repeatString, String exceptionString);
}

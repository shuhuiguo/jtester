/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fitlibrary.closure.ICalledMethodTarget;
import fitlibrary.exception.method.VoidMethodException;
import fitlibrary.exception.table.RowWrongWidthException;
import fitlibrary.global.PlugBoard;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.workflow.DoTraverse;

/**
 * Used to be called SetUpTraverse A traverse for entering data for setup (or
 * anything else). Serves a similar purpose to Michael Feather's RowEntryFixture
 * It operates the same as CalculateTraverse, except that there is no empty
 * column and thus no expected columns.
 */
public class CollectionSetUpTraverse extends DoTraverse {
	protected ICalledMethodTarget target;
	protected int argCount = -1;
	protected boolean boundOK = false;
	protected Collection<Object> collection = new ArrayList<Object>();
	protected boolean embedded = false;

	public CollectionSetUpTraverse() {
		//
	}

	public CollectionSetUpTraverse(Collection<Object> collection) {
		this.collection = collection;
	}

	public CollectionSetUpTraverse(Object sut, Collection<Object> collection, boolean embedded) {
		super(sut);
		this.collection = collection;
		this.embedded = embedded;
	}

	public CollectionSetUpTraverse(Object sut) {
		super(sut);
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		bindFirstRowToTarget(table.at(1), testResults, this);
		for (int i = 2; i < table.size(); i++)
			processRow(table.at(i), testResults);
		return collection;
	}

	@Override
	public Object interpretInFlow(Table table, TestResults testResults) {
		try {
			interpretAfterFirstRow(table, testResults);
		} catch (Exception e) {
			int rowNo = 0;
			if (embedded)
				rowNo = 1;
			table.at(rowNo).error(testResults, e);
		}
		return collection;
	}

	public void bindFirstRowToTarget(Row row, TestResults testResults, Evaluator evaluator) {
		try {
			argCount = row.size();
			target = findMethodTarget(row, evaluator, embedded);
			boundOK = true;
		} catch (Exception e) {
			row.error(testResults, e);
		}
	}

	private static ICalledMethodTarget findMethodTarget(Row row, Evaluator evaluator, boolean embedded) {
		List<String> arguments = new ArrayList<String>();
		String argNames = buildArguments(row, arguments, evaluator);
		String methodName = evaluator.getRuntimeContext().extendedCamel(argNames);
		ICalledMethodTarget findMethod = PlugBoard.lookupTarget.findMethod(methodName, arguments, "Type", evaluator);
		if (findMethod.returnsVoid() && embedded)
			throw new VoidMethodException(methodName, "SetUpTraverse");
		return findMethod;
	}

	private static String buildArguments(Row row, List<String> arguments, Evaluator evaluator) {
		String argNames = "";
		for (int i = 0; i < row.size(); i++) {
			String name = row.text(i, evaluator);
			argNames += " " + name;
			arguments.add(evaluator.getRuntimeContext().extendedCamel(name));
		}
		return argNames;
	}

	public void processRow(Row row, TestResults testResults) {
		if (!boundOK) {
			row.ignore(testResults);
			return;
		}
		if (row.size() != argCount) {
			row.error(testResults, new RowWrongWidthException(argCount));
			return;
		}
		try {
			invokeMethod(row, testResults);
		} catch (Exception e) {
			row.error(testResults, e);
		}
	}

	public Object invokeMethod(Row row, TestResults testResults) throws Exception {
		Object result = target().invoke(row, testResults, true);
		collection.add(result);
		return result;
	}

	public ICalledMethodTarget target() {
		return target;
	}

	public Collection<Object> getCollection() {
		return collection;
	}

	public static boolean hasObjectFactoryMethodFor(Table table, Evaluator evaluator) {
		try {
			findMethodTarget(table.at(0), evaluator, false);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

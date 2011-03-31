/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse.workflow.caller;

import fit.Fixture;
import fitlibrary.definedAction.ParameterBinder;
import fitlibrary.exception.FitLibraryException;
import fitlibrary.runResults.TableListener;
import fitlibrary.runResults.TestResults;
import fitlibrary.runResults.TestResultsFactory;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;
import fitlibrary.traverse.TableEvaluator;
import fitlibrary.traverse.Traverse;

public class MultiDefinedActionRunnerTraverse extends Traverse {
	private ParameterBinder binder;
	private RuntimeContextInternal runtime;

	public MultiDefinedActionRunnerTraverse(ParameterBinder binder, RuntimeContextInternal runtime) {
		this.binder = binder;
		this.runtime = runtime;
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		try {
			if (table.size() < 3)
				throw new FitLibraryException("Missing data rows in table");
			getRuntimeContext().pushLocalDynamicVariables();
			Row parameterRow = table.at(1);
			binder.verifyHeaderAgainstFormalParameters(parameterRow, this);
			parameterRow.pass(testResults);
			for (int r = 2; r < table.size(); r++) {
				Row callRow = table.at(r);
				if (runtime.isAbandoned(testResults))
					callRow.ignore(testResults);
				else
					try {
						runRow(callRow, parameterRow, testResults);
					} catch (Exception e) {
						callRow.error(testResults, e);
					}
			}
			getRuntimeContext().popLocalDynamicVariables();
		} catch (Exception e) {
			table.error(testResults, e);
		}
		return null;
	}

	private void runRow(Row callRow, Row parameterRow, TestResults testResults) {
		Tables body = binder.getCopyOfBody();
		TestResults subTestResults = TestResultsFactory.testResults();
		DefinedActionCallManager definedActionCallManager = runtime.getDefinedActionCallManager();
		try {
			definedActionCallManager.startCall(binder);
			binder.bindMulti(parameterRow, callRow, getDynamicVariables());
			runBody(body, subTestResults);
			colourRowInReport(callRow, testResults, subTestResults);
		} finally {
			definedActionCallManager.endCall(binder);
		}
		if (runtime.toExpandDefinedActions() || subTestResults.problems() || runtime.isAbandoned(testResults)) {
			Cell cell = TableFactory.cell(body);
			cell.at(0).setLeader(Fixture.label(DefinedActionCaller.link(binder.getPageName())));
			cell.calls();
			callRow.add(cell);
		} else if (definedActionCallManager.readyToShow())
			callRow.add(TableFactory.cell(TableFactory.tables(definedActionCallManager.getShowsTable())));
	}

	private void runBody(Tables body, TestResults subTestResults) {
		TableEvaluator tableEvaluator = runtime.getTableEvaluator();
		tableEvaluator.runInnerTables(body, new TableListener(subTestResults));
	}

	private void colourRowInReport(Row callRow, TestResults testResults, TestResults subTestResults) {
		if (runtime.isAbandoned(testResults))
			callRow.ignore(testResults);
		else if (runtime.toExpandDefinedActions() || subTestResults.problems()) {
			if (subTestResults.passed())
				callRow.passKeywords(testResults);
			else if (subTestResults.errors())
				for (int i = 0; i < callRow.size(); i++)
					callRow.at(i).error(testResults, new FitLibraryException(""));
			else if (subTestResults.failed())
				for (int i = 0; i < callRow.size(); i++)
					callRow.at(i).fail(testResults);
			else
				for (int i = 0; i < callRow.size(); i++)
					callRow.at(i).pass(testResults);
		} else
			callRow.pass(testResults);
	}
}

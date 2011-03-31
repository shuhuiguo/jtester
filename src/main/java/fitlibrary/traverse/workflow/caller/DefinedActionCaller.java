/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse.workflow.caller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import fit.Fixture;
import fitlibrary.DefineAction;
import fitlibrary.definedAction.DefinedActionsRepository;
import fitlibrary.definedAction.ParameterBinder;
import fitlibrary.exception.FitLibraryException;
import fitlibrary.global.TemporaryPlugBoardForRuntime;
import fitlibrary.log.FitLibraryLogger;
import fitlibrary.runResults.TableListener;
import fitlibrary.runResults.TestResults;
import fitlibrary.runResults.TestResultsFactory;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;
import fitlibrary.traverse.TableEvaluator;
import fitlibrary.traverse.workflow.AbstractDoCaller;
import fitlibrary.typed.TypedObject;
import fitlibraryGeneric.typed.GenericTypedObject;

public class DefinedActionCaller extends AbstractDoCaller {
	private static Logger logger = FitLibraryLogger.getLogger(DefinedActionCaller.class);
	private ParameterBinder binder;
	private String methodName;
	private RuntimeContextInternal runtime;
	private List<Object> actualArgs = new ArrayList<Object>();

	public DefinedActionCaller(Row row, RuntimeContextInternal runtime) {
		this.runtime = runtime;
		methodName = row.methodNameForCamel(runtime);
		actualArgs = actualArgs(row);
		binder = repository().lookupByCamel(methodName, actualArgs.size());
		if (binder == null)
			lookupByClass();
	}

	public DefinedActionCaller(String object, String className, Row row, RuntimeContextInternal runtime) {
		this.runtime = runtime;
		methodName = row.methodNameForCamel(runtime);
		actualArgs.add(object);
		actualArgs(row, actualArgs);
		this.binder = repository().lookupByClassByCamel(className, methodName, (actualArgs.size() - 1), runtime);
		if (binder == null)
			throw new FitLibraryException("Unknown defined action for object of class " + className);
	}

	private DefinedActionsRepository repository() {
		return TemporaryPlugBoardForRuntime.definedActionsRepository();
	}

	private void lookupByClass() {
		Object objectName = runtime.getDynamicVariable("this");
		if (objectName != null) {
			Object className = runtime.getDynamicVariable(objectName + ".class");
			actualArgs.add(0, objectName.toString());
			if (className != null && !"".equals(className))
				binder = repository().lookupByClassByCamel(className.toString(), methodName, (actualArgs.size() - 1),
						runtime);
		}
	}

	// @Override
	public boolean isValid() {
		return binder != null;
	}

	// @Override
	public TypedObject run(Row row, TestResults testResults) {
		logger.trace("Calling " + methodName + binder.getParameterList() + " from " + binder.getPageName());
		DefinedActionCallManager definedActionCallManager = runtime.getDefinedActionCallManager();
		definedActionCallManager.startCall(binder);
		runtime.pushLocalDynamicVariables();
		try {
			Object oldThisValue = runtime.getDynamicVariable("this");
			if (!actualArgs.isEmpty())
				runtime.setDynamicVariable("this", actualArgs.get(0));
			binder.bindUni(actualArgs, runtime.getDynamicVariables());
			processDefinedAction(binder.getCopyOfBody(), row, testResults);
			runtime.setDynamicVariable("this", oldThisValue);
		} finally {
			definedActionCallManager.endCall(binder);
			runtime.popLocalDynamicVariables();
		}
		if (!runtime.toExpandDefinedActions() && definedActionCallManager.readyToShow()
				&& !runtime.isAbandoned(testResults))
			row.add(TableFactory.cell(TableFactory.tables(definedActionCallManager.getShowsTable())));
		return GenericTypedObject.NULL;
	}

	// @Override
	public String ambiguityErrorMessage() {
		return "defined action " + methodName;
	}

	private List<Object> actualArgs(Row row) {
		return actualArgs(row, new ArrayList<Object>());
	}

	private List<Object> actualArgs(Row row, List<Object> result) {
		for (int i = 1; i < row.size(); i += 2) {
			Cell cell = row.at(i);
			if (cell.hasEmbeddedTables(runtime.getResolver()))
				result.add(cell.getEmbeddedTables());
			else
				result.add(cell.text(runtime.getResolver()));
		}
		return result;
	}

	private void processDefinedAction(Tables definedActionBody, Row row, TestResults testResults) {
		TestResults subTestResults = TestResultsFactory.testResults();
		TableEvaluator tableEvaluator = runtime.getTableEvaluator();
		tableEvaluator.runInnerTables(definedActionBody, new TableListener(subTestResults));
		colourRowInReport(row, testResults, subTestResults);
		if (runtime.toExpandDefinedActions() || subTestResults.problems() || runtime.isAbandoned(testResults)) {
			Cell cell = TableFactory.cell(definedActionBody);
			cell.at(0).setLeader(Fixture.label(link(binder.getPageName())) + cell.at(0).getLeader());
			cell.calls();
			row.add(cell);
		}
	}

	private void colourRowInReport(Row row, TestResults testResults, TestResults subTestResults) {
		if (runtime.toExpandDefinedActions() || subTestResults.problems()) {
			if (runtime.isAbandoned(testResults)) {
				// Leave it to caller to ignore the row
			} else if (subTestResults.passed())
				row.passKeywords(testResults);
			else if (subTestResults.errors())
				for (int i = 0; i < row.size(); i += 2)
					row.at(i).error(testResults, new FitLibraryException(""));
			else if (subTestResults.failed())
				for (int i = 0; i < row.size(); i += 2)
					row.at(i).fail(testResults);
			else
				for (int i = 0; i < row.size(); i += 2)
					row.at(i).ignore(testResults);
		} else if (!runtime.isAbandoned(testResults))
			row.passKeywords(testResults);
	}

	public static String link(String pageName) {
		if (pageName.equals(DefineAction.STORYTEST_BASED))
			return "Defined action call:";
		return "Defined action call <a href='" + pageName + "'>." + pageName + "</a>:";
	}

	public static String link2(String pageName) {
		if (pageName.equals(DefineAction.STORYTEST_BASED))
			return "storytest";
		return "<a href='" + pageName + "'>." + pageName + "</a>:";
	}
}
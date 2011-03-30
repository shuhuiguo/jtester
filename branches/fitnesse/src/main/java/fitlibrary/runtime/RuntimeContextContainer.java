/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.runtime;

import java.io.IOException;
import java.util.Stack;

import org.apache.log4j.Logger;

import fitlibrary.config.Configuration;
import fitlibrary.dynamicVariable.DynamicVariables;
import fitlibrary.dynamicVariable.DynamicVariablesRecording;
import fitlibrary.dynamicVariable.DynamicVariablesRecordingThatFails;
import fitlibrary.dynamicVariable.DynamicVariablesRecordingToFile;
import fitlibrary.dynamicVariable.GlobalDynamicVariables;
import fitlibrary.dynamicVariable.LocalDynamicVariables;
import fitlibrary.dynamicVariable.VariableResolver;
import fitlibrary.flow.GlobalActionScope;
import fitlibrary.flow.IScope;
import fitlibrary.listener.OnError;
import fitlibrary.log.ConfigureLog4j;
import fitlibrary.log.FitLibraryLogger;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.table.Tables;
import fitlibrary.tableProxy.CellProxy;
import fitlibrary.tableProxy.RowProxy;
import fitlibrary.traverse.TableEvaluator;
import fitlibrary.traverse.workflow.caller.DefinedActionCallManager;
import fitlibrary.typed.TypedObject;
import fitlibrary.utility.ExtendedCamelCase;
import fitlibrary.utility.Pair;

public class RuntimeContextContainer implements RuntimeContextInternal {
	private static Logger logger = FitLibraryLogger.getLogger(RuntimeContextContainer.class);
	private static final String EXPAND_DEFINED_ACTIONS = "$$expandDefinedActions$$";
	private SuiteWideRuntimeContext suiteWideRuntimeContext;
	// This is part suite-wide and part local to a storytest:
	protected DynamicVariables dynamicVariables = new GlobalDynamicVariables();
	// Following are local to a storytest:
	private DynamicVariablesRecording dynamicVariablesRecording = new DynamicVariablesRecordingThatFails();
	private DefinedActionCallManager definedActionCallManager = new DefinedActionCallManager();
	private FoldingTexts foldingTexts = new FoldingTexts();
	protected TestResults testResults;
	private Stack<TestResults> testResultsStack = new Stack<TestResults>();
	protected Row currentRow;
	protected Table currentTable;
	private String currentPageName = "";
	private ConfigureLog4j configureLog4j;
	private OnError onErrorHandler = new OnError() {
		// @Override
		public boolean stopOnError(int fails, int errors) {
			return false;
		}
	};

	public RuntimeContextContainer() {
		this(null, new GlobalActionScope()); // For those cases where a fixture
												// is being used independently
												// of table execution
	}

	public RuntimeContextContainer(IScope scope, GlobalActionScope global) {
		suiteWideRuntimeContext = new SuiteWideRuntimeContext(scope, global);
		global.setRuntimeContext(this);
		configureLog4j = new ConfigureLog4j(this);
	}

	public RuntimeContextContainer(String[] s) {
		for (int i = 0; i < s.length - 1; i += 2)
			dynamicVariables.put(s[i], s[i + 1]);
	}

	protected RuntimeContextContainer(DynamicVariables dynamicVariables,
			SuiteWideRuntimeContext suiteWideRuntimeContext, ConfigureLog4j configureLog4j, FoldingTexts foldingTexts) {
		this.dynamicVariables = dynamicVariables;
		this.suiteWideRuntimeContext = suiteWideRuntimeContext;
		this.configureLog4j = configureLog4j;
		this.foldingTexts = foldingTexts;
	}

	public RuntimeContextInternal copyFromSuite() {
		logger.trace("Use Suite dynamic variables " + dynamicVariables.top());
		return new RuntimeContextContainer(new GlobalDynamicVariables(dynamicVariables.top()), suiteWideRuntimeContext,
				configureLog4j, foldingTexts);
	}

	public void reset() {
		dynamicVariables = new GlobalDynamicVariables();
		suiteWideRuntimeContext.reset();
	}

	public DynamicVariables getDynamicVariables() {
		return dynamicVariables;
	}

	@Override
	public String toString() {
		return getDynamicVariables().toString();
	}

	public void putTimeout(String name, int timeout) {
		suiteWideRuntimeContext.putTimeout(name, timeout);
	}

	public int getTimeout(String name, int defaultTimeout) {
		return suiteWideRuntimeContext.getTimeout(name, defaultTimeout);
	}

	public void startLogging(String fileName) {
		suiteWideRuntimeContext.startLogging(fileName);
	}

	public void printToLog(String s) throws IOException {
		suiteWideRuntimeContext.printToLog(s);
	}

	public void pushLocalDynamicVariables() {
		dynamicVariables = new LocalDynamicVariables(dynamicVariables);
	}

	public void popLocalDynamicVariables() {
		dynamicVariables = dynamicVariables.popLocal();
	}

	public void setDynamicVariable(String key, Object value) {
		dynamicVariables.put(key, value);
	}

	public Object getDynamicVariable(String key) {
		return dynamicVariables.get(key);
	}

	public boolean toExpandDefinedActions() {
		return "true".equals(getDynamicVariable(EXPAND_DEFINED_ACTIONS));
	}

	public void setExpandDefinedActions(boolean expandDefinedActions) {
		setDynamicVariable(EXPAND_DEFINED_ACTIONS, "" + expandDefinedActions);
	}

	public IScope getScope() {
		return suiteWideRuntimeContext.getScope();
	}

	public void SetTableEvaluator(TableEvaluator evaluator) {
		suiteWideRuntimeContext.SetTableEvaluator(evaluator);
	}

	public TableEvaluator getTableEvaluator() {
		return suiteWideRuntimeContext.getTableEvaluator();
	}

	public GlobalActionScope getGlobal() {
		return suiteWideRuntimeContext.getGlobal();
	}

	public void showAsAfterTable(String title, String s) {
		foldingTexts.logAsAfterTable(title, s);
	}

	public void show(String s) {
		currentRow.addCell(s).shown();
		getDefinedActionCallManager().addShow(currentRow);
	}

	public void addAccumulatedFoldingText(Table table) {
		foldingTexts.addAccumulatedFoldingText(table);
	}

	public void recordToFile(String fileName) {
		dynamicVariablesRecording = new DynamicVariablesRecordingToFile(fileName);
	}

	public DynamicVariablesRecording getDynamicVariableRecorder() {
		return dynamicVariablesRecording;
	}

	public void setAbandon(boolean abandon) {
		suiteWideRuntimeContext.setAbandon(abandon);
	}

	public boolean isAbandoned(TestResults testResults2) {
		return suiteWideRuntimeContext.isAbandoned(testResults2);
	}

	public void setStopOnError(boolean stop) {
		suiteWideRuntimeContext.setStopOnError(stop);
	}

	public DefinedActionCallManager getDefinedActionCallManager() {
		return definedActionCallManager;
	}

	public VariableResolver getResolver() {
		return this;
	}

	public Pair<String, Tables> resolve(String key) {
		return getDynamicVariables().resolve(key);
	}

	public void setCurrentRow(Row row) {
		currentRow = row;
	}

	public void setCurrentTable(Table table) {
		currentTable = table;
	}

	public boolean hasRowsAfter(Row row) {
		if (currentTable == null || currentRow == null)
			return false;
		return currentTable.hasRowsAfter(currentRow);
	}

	public TestResults getTestResults() {
		return testResults;
	}

	public void pushTestResults(TestResults results) {
		testResultsStack.push(this.testResults);
		this.testResults = results;
	}

	public void popTestResults() {
		this.testResults = testResultsStack.pop();
	}

	public CellProxy cellAt(final int i) {
		final Cell cell = currentRow.at(i);
		return new CellProxy() {
			// @Override
			public void pass() {
				cell.pass(testResults);
			}

			// @Override
			public void pass(String msg) {
				cell.pass(testResults, msg);
			}

			// @Override
			public void fail(String msg) {
				if (msg == null || "".equals(msg))
					cell.fail(testResults);
				else
					cell.fail(testResults, msg, dynamicVariables);
			}

			// @Override
			public void failHtml(String msg) {
				cell.failHtml(testResults, msg);
			}

			// @Override
			public void fail() {
				cell.fail(testResults);
			}

			// @Override
			public void error(String msg) {
				if (msg == null || "".equals(msg))
					cell.error(testResults);
				else
					cell.error(testResults, msg);
			}

			// @Override
			public void error(Throwable e) {
				cell.error(testResults, e);
			}

			// @Override
			public void error() {
				cell.error(testResults);
			}
		};
	}

	public RowProxy currentRow() {
		return new RowProxy() {
			// @Override
			public void addShow(String s) {
				currentRow.addCell(s).shown();
			}
		};
	}

	public Table currentTable() {
		return currentTable;
	}

	public void setCurrentPageName(String pageName) {
		this.currentPageName = pageName;
	}

	public String getCurrentPageName() {
		return currentPageName;
	}

	public ConfigureLog4j getConfigureLog4j() {
		return configureLog4j;
	}

	public void addNamedObject(String name, TypedObject typedObject) {
		suiteWideRuntimeContext.addNamedObject(name, typedObject);
	}

	public Configuration getConfiguration() {
		return suiteWideRuntimeContext;
	}

	public String extendedCamel(String s) {
		return ExtendedCamelCase.camel(s, suiteWideRuntimeContext.keepingUniCode());
	}

	public Row row() {
		return currentRow;
	}

	// @Override
	public void registerOnErrorHandler(OnError onError) {
		onErrorHandler = onError;
	}

	// @Override
	public void checkStopOnError(TestResults testResults2) {
		if (onErrorHandler.stopOnError(testResults2.getCounts().wrong, testResults2.getCounts().exceptions))
			getScope().setAbandon(true);
	}
}

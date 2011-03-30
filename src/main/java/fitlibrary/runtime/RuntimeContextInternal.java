/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.runtime;

import fitlibrary.config.Configuration;
import fitlibrary.dynamicVariable.DynamicVariablesRecording;
import fitlibrary.flow.GlobalActionScope;
import fitlibrary.flow.IScope;
import fitlibrary.listener.OnError;
import fitlibrary.log.ConfigureLog4j;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.tableProxy.CellProxy;
import fitlibrary.tableProxy.RowProxy;
import fitlibrary.traverse.TableEvaluator;
import fitlibrary.traverse.workflow.caller.DefinedActionCallManager;
import fitlibrary.typed.TypedObject;

public interface RuntimeContextInternal extends RuntimeContext {
	void addAccumulatedFoldingText(Table table);

	CellProxy cellAt(int i);

	RuntimeContextInternal copyFromSuite();

	RowProxy currentRow();

	Table currentTable();

	ConfigureLog4j getConfigureLog4j();

	String getCurrentPageName();

	DefinedActionCallManager getDefinedActionCallManager();

	DynamicVariablesRecording getDynamicVariableRecorder();

	GlobalActionScope getGlobal();

	IScope getScope();

	TableEvaluator getTableEvaluator();

	TestResults getTestResults();

	int getTimeout(String name, int defaultTimeout);

	boolean hasRowsAfter(Row row);

	boolean isAbandoned(TestResults testResults);

	void popLocalDynamicVariables();

	void popTestResults();

	void putTimeout(String name, int timeout);

	void pushLocalDynamicVariables();

	void pushTestResults(TestResults testResults);

	void recordToFile(String fileName);

	void reset();

	void setCurrentPageName(String pageName);

	void setCurrentRow(Row row);

	void setCurrentTable(Table table);

	void setExpandDefinedActions(boolean expandDefinedActions);

	void startLogging(String fileName);

	boolean toExpandDefinedActions();

	void addNamedObject(String string, TypedObject result);

	Configuration getConfiguration();

	String extendedCamel(String s);

	Row row();

	void registerOnErrorHandler(OnError result);

	void checkStopOnError(TestResults testResults);
}

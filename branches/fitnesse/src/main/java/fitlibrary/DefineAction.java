package fitlibrary;

import java.util.ArrayList;
import java.util.List;

import fitlibrary.definedAction.DefinedActionParameterTranslation;
import fitlibrary.definedAction.DefinedActionsRepository;
import fitlibrary.definedAction.ParameterBinder;
import fitlibrary.exception.FitLibraryException;
import fitlibrary.exception.FitLibraryExceptionInHtml;
import fitlibrary.global.TemporaryPlugBoardForRuntime;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;
import fitlibrary.traverse.Traverse;
import fitlibrary.traverse.workflow.caller.DefinedActionCaller;

public class DefineAction extends Traverse {
	public static final String AUTO_TRANSLATE_DEFINED_ACTION_PARAMETERS = "auto-translate defined action parameters";
	public static final String STORYTEST_BASED = "storytest table";
	private String wikiClassName = "";
	private String pageName;

	public DefineAction() {
		this.pageName = STORYTEST_BASED;
	}

	public DefineAction(String className) {
		this(className, STORYTEST_BASED);
	}

	public DefineAction(String className, String pathName) {
		this.wikiClassName = className;
		this.pageName = pathName;
	}

	public String getPageName() {
		return pageName;
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		try {
			define(table, testResults);
		} catch (Exception e) {
			table.error(testResults, e);
		}
		return null;
	}

	public void define(Table table, TestResults testResults) {
		checkSizeWithRegardToTheClass(table);
		Tables tables = table.at(1).at(0).getEmbeddedTables();
		Table headerTable = tables.at(0);
		if (headerTable.size() > 1)
			processMultiDefinedAction(headerTable, copyBody(tables));
		else
			processDefinedAction(headerTable, copyBody(tables), testResults);
	}

	private void processDefinedAction(Table headerTable, Tables bodyCopy, TestResults testResults) {
		Row parametersRow = headerTable.at(0);
		parametersRow.passKeywords(testResults);
		List<String> formalParameters = getFormalParameters(parametersRow, 1, 2);
		if (getDynamicVariable(AUTO_TRANSLATE_DEFINED_ACTION_PARAMETERS) == "true"
				&& DefinedActionParameterTranslation.needToTranslateParameters(formalParameters, bodyCopy)) {
			formalParameters = DefinedActionParameterTranslation.translateParameters(formalParameters, bodyCopy);
		}
		String name = parametersRow.methodNameForCamel(getRuntimeContext());
		ParameterBinder binder = new ParameterBinder(name, formalParameters, bodyCopy, pageName);
		repository().define(parametersRow, wikiClassName, binder, getRuntimeContext(), pageName);
	}

	private void processMultiDefinedAction(Table headerTable, Tables bodyCopy) {
		if (headerTable.size() > 2)
			error("Unexpected rows in first table of defined action", headerTable.at(0));
		String definedActionName = headerTable.at(0).at(0).text();
		List<String> formalParameters = getFormalParameters(headerTable.at(1), 0, 1);
		ParameterBinder binder = new ParameterBinder(definedActionName, formalParameters, bodyCopy, pageName);
		repository().defineMultiDefinedAction(definedActionName, binder);
	}

	private static Tables copyBody(Tables tables) {
		if (tables.atExists(1))
			return tables.fromAt(1).deepCopy();
		Row row = TableFactory.row();
		row.addCell("comment");
		return TableFactory.tables(TableFactory.table(row));
	}

	private void error(String msg, Row parametersRow) {
		throw new FitLibraryExceptionInHtml(msg + " in <b>" + parametersRow.methodNameForCamel(getRuntimeContext())
				+ "</b> in " + DefinedActionCaller.link2(pageName));
	}

	private List<String> getFormalParameters(Row parametersRow, int start, int increment) {
		List<String> formalParameters = new ArrayList<String>();
		if (wikiClassBased())
			formalParameters.add("this");
		for (int i = start; i < parametersRow.size(); i += increment)
			if (i < parametersRow.size()) {
				String parameter = parametersRow.text(i, this);
				if ("".equals(parameter))
					error("Parameter name is blank", parametersRow);
				if (formalParameters.contains(parameter))
					error("Parameter name '<b>" + parameter + "</b>' is duplicated", parametersRow);
				formalParameters.add(parameter);
			}
		return formalParameters;
	}

	private boolean wikiClassBased() {
		return !"".equals(wikiClassName);
	}

	private DefinedActionsRepository repository() {
		return TemporaryPlugBoardForRuntime.definedActionsRepository();
	}

	private void checkSizeWithRegardToTheClass(Table table) {
		if (table.size() < 2 || table.size() > 3)
			throw new FitLibraryException("Table for DefineAction needs to be two or three rows, but is "
					+ table.size() + ".");
		boolean hasClass = false;
		int bodyRow = 1;
		if (table.size() == 3) {
			hasClass = true;
			bodyRow = 2;
		}
		if (table.at(1).size() != 1)
			throw new FitLibraryException("Second row of table for DefineAction needs to contain one cell.");
		if (hasClass && table.at(2).size() != 1)
			throw new FitLibraryException("Third row of table for DefineAction needs to contain one cell.");
		if (!table.at(bodyRow).at(0).hasEmbeddedTables(this))
			throw new FitLibraryException("Second row of table for DefineAction needs to contain nested tables.");
		if (hasClass)
			wikiClassName = table.at(1).text(0, this);
	}
}

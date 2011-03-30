/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.flow;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import fit.Fixture;
import fitlibrary.DefineAction;
import fitlibrary.annotation.ActionType;
import fitlibrary.annotation.AnAction;
import fitlibrary.annotation.CompoundAction;
import fitlibrary.annotation.NullaryAction;
import fitlibrary.annotation.ShowSelectedActions;
import fitlibrary.annotation.SimpleAction;
import fitlibrary.config.Configuration;
import fitlibrary.definedAction.DefineActionsOnPage;
import fitlibrary.definedAction.DefineActionsOnPageSlowly;
import fitlibrary.domainAdapter.FileHandler;
import fitlibrary.domainAdapter.RelativeFileHandler;
import fitlibrary.domainAdapter.StringAdapter;
import fitlibrary.dynamicVariable.DynamicVariables;
import fitlibrary.exception.FitLibraryException;
import fitlibrary.exception.FitLibraryShowException;
import fitlibrary.exception.IgnoredException;
import fitlibrary.exception.NotRejectedException;
import fitlibrary.global.PlugBoard;
import fitlibrary.global.TemporaryPlugBoardForRuntime;
import fitlibrary.listener.OnError;
import fitlibrary.log.ConfigureLogger;
import fitlibrary.log.FitLibraryLogger;
import fitlibrary.polling.Eventually;
import fitlibrary.polling.PassFail;
import fitlibrary.polling.PollForPass;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.special.DoAction;
import fitlibrary.suite.SuiteFixture;
import fitlibrary.table.Row;
import fitlibrary.traverse.CommentTraverse;
import fitlibrary.traverse.RuntimeContextual;
import fitlibrary.traverse.workflow.RandomSelectTraverse;
import fitlibrary.traverse.workflow.SetVariableTraverse;
import fitlibrary.traverse.workflow.StopWatch;
import fitlibrary.typed.TypedObject;
import fitlibrary.xref.CrossReferenceFixture;
import fitlibraryGeneric.typed.GenericTypedObject;

// Note: If runtime was the SUT, we could eliminate some of these methods (for action lookup, anyway).
@ShowSelectedActions
public class GlobalActionScope implements RuntimeContextual {
	@SuppressWarnings("unused")
	private static Logger logger = FitLibraryLogger.getLogger(GlobalActionScope.class);
	public static final String STOP_WATCH = "$$STOP WATCH$$";
	public static final String BECOMES_TIMEOUT = "becomes";
	private static int UNNAMED = 0;
	private RuntimeContextInternal runtime;

	// @Override
	public void setRuntimeContext(RuntimeContextInternal runtime) {
		this.runtime = runtime;
	}

	// --- BECOMES, ETC TIMEOUTS:
	@SimpleAction(wiki = "|''<i>becomes timeout</i>''|timeout|", tooltip = "Change the timeout period (in ms) for becomes.")
	public void becomesTimeout(int timeout) {
		putTimeout(BECOMES_TIMEOUT, timeout);
	}

	@NullaryAction(tooltip = "What is the timeout period (in ms) for becomes?")
	public int becomesTimeout() {
		return getTimeout(BECOMES_TIMEOUT);
	}

	@SimpleAction(wiki = "|''<i>timeout</i>''|timeout name|", tooltip = "What is the timeout period (in ms) for the named timeout?")
	public int getTimeout(String name) {
		return runtime.getTimeout(name, 1000);
	}

	@SimpleAction(wiki = "|''<i>put timeout</i>''|timeout name|", tooltip = "Change the timeout period (in ms) for the named timeout.")
	public void putTimeout(String name, int timeout) {
		runtime.putTimeout(name, timeout);
	}

	// --- STOP ON ERROR AND ABANDON:
	/**
	 * When (stopOnError), don't continue interpreting a table if there's been a
	 * problem
	 */
	@SimpleAction(wiki = "|''<i>set stop on error</i>''|true or false|", tooltip = "Alter whether or not a storytest will stop on error, to avoid the extra time and errors.")
	public void setStopOnError(boolean stopOnError) {
		runtime.setStopOnError(stopOnError);
	}

	@NullaryAction(tooltip = "Stop running the storytest immediately, so you can effectively ignore the rest of a storytest while exploring a problem.")
	public void abandonStorytest() {
		runtime.setAbandon(true);
	}

	// --- DYNAMIC VARIABLES:
	public DynamicVariables getDynamicVariables() {
		return runtime.getDynamicVariables();
	}

	public void setDynamicVariable(String key, Object value) {
		getDynamicVariables().put(key, value);
	}

	public Object getDynamicVariable(String key) {
		return getDynamicVariables().get(key);
	}

	public boolean clearDynamicVariables() {
		getDynamicVariables().clearAll();
		return true;
	}

	@SimpleAction(wiki = "|''<i>add dynamic variables from file</i>''|filename|", tooltip = "Load the given property file and set dynamic variables from the properties (but it is usually better to set them on a page).")
	public boolean addDynamicVariablesFromFile(String fileName) {
		return getDynamicVariables().addFromPropertiesFile(fileName);
	}

	@SimpleAction(wiki = "|''<i>add dynamic variables from unicode file</i>''|filename|", tooltip = "Load the given property file, respecting unicode, and set dynamic variables from the properties (but it is usually better to set them on a page).")
	public void addDynamicVariablesFromUnicodeFile(String fileName) throws IOException {
		getDynamicVariables().addFromUnicodePropertyFile(fileName);
	}

	@SimpleAction(wiki = "|''<i>set system property</i>''|property name|''<i>to</i>''|value|", tooltip = "Set the Java system property, and the dynamic variable of the same name, to the value. Can be used to affect an application that is still be created.")
	public boolean setSystemPropertyTo(String property, String value) {
		System.setProperty(property, value);
		setDynamicVariable(property, value);
		return true;
	}

	@SimpleAction(wiki = "|''<i>set fit variable</i>''|variable name|value|", tooltip = "Set the Fit variable to the value, so a value from FitLibrary can be used in a Fit table.")
	public void setFitVariable(String variableName, Object result) {
		Fixture.setSymbol(variableName, result);
	}

	@SimpleAction(wiki = "|''<i>get symbol named</i>''|variable name|", tooltip = "Get the value of the Fit variable, so it can be used with a FitLibrary table.")
	public Object getSymbolNamed(String fitSymbolName) {
		return Fixture.getSymbol(fitSymbolName);
	}

	// --- SLEEP & STOPWATCH:
	@SimpleAction(wiki = "|''<i>sleep for</i>''|time in milliseconds|", tooltip = "Sleep for the given time in millseconds (the becomes special action may be better).")
	public boolean sleepFor(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			// Nothing to do
		}
		return true;
	}

	@NullaryAction(tooltip = "Start the stopwatch, so it can be used to fail a storytest if things take too long (with |stop stop watch|).")
	public void startStopWatch() {
		setDynamicVariable(STOP_WATCH, new StopWatch());
	}

	@NullaryAction(tooltip = "Stop the stopwatch, and return how long it has been going (in ms), so you can cause the storytest to fail if it's been too long.")
	public long stopWatch() {
		return getStopWatch().delay();
	}

	private StopWatch getStopWatch() {
		StopWatch stopWatch = (StopWatch) getDynamicVariable(STOP_WATCH);
		if (stopWatch == null)
			throw new FitLibraryException("No stopwatch started");
		return stopWatch;
	}

	// --- FIXTURE SELECTION
	/** The rest of the table is ignored (and not coloured) */
	@NullaryAction(tooltip = "Place this in the first row of a table that is to be ignored (not run at all).")
	public CommentTraverse comment() {
		return new CommentTraverse();
	}

	/**
	 * The rest of the table is ignored (and the first row is coloured as
	 * ignored)
	 */
	@NullaryAction(tooltip = "Place this in the first row of a table that is to be ignored (not run at all). The table is coloured to show it has been ignored.")
	public CommentTraverse ignored() {
		return ignore();
	}

	@NullaryAction(tooltip = "Place this in the first row of a table that is to be ignored (not run at all).")
	public CommentTraverse ignore() {
		return new CommentTraverse(true);
	}

	@NullaryAction(tooltip = "Place this in the first row of a table that is to be ignored (not run at all).")
	public CommentTraverse ignoreTable() {
		return new CommentTraverse(true);
	}

	@SimpleAction(wiki = "|''<i>xref</i>''|page name of a defined action suite|", tooltip = "Produce a cross reference of all the defined actions in the selected suite")
	public CrossReferenceFixture xref(String suiteName) {
		return new CrossReferenceFixture(suiteName);
	}

	public SetVariableTraverse setVariables() {
		return new SetVariableTraverse();
	}

	@CompoundAction(wiki = "|''<i>file</i>''|absolute file name|", tooltip = "Access the given file and allow actions on it in the rest of the table.")
	public FileHandler file(String fileName) {
		return new FileHandler(fileName);
	}

	@CompoundAction(wiki = "|''<i>relative file</i>''|relative file name|", tooltip = "Access the given file and allow actions on it in the rest of the table.")
	public RelativeFileHandler relativeFile(String fileName) {
		return new RelativeFileHandler(fileName);
	}

	public SuiteFixture suite() {
		return new SuiteFixture();
	}

	// --- DEFINED ACTIONS
	public DefineAction defineAction(String wikiClassName) {
		DefineAction defineAction = new DefineAction(wikiClassName);
		defineAction.setRuntimeContext(runtime);
		return defineAction;
	}

	@NullaryAction(tooltip = "Define a new action of that name and as given by the rest of the table.")
	public DefineAction defineAction() {
		return new DefineAction();
	}

	public void defineActionsSlowlyAt(String pageName) throws Exception {
		new DefineActionsOnPageSlowly(pageName, runtime).process();
	}

	@SimpleAction(wiki = "|''<i>define actions at</i>''|page name|", tooltip = "Load the defined actions from the given page and any sub-pages.")
	public void defineActionsAt(String pageName) throws Exception {
		new DefineActionsOnPage(pageName, runtime).process();
	}

	@SimpleAction(wiki = "|''<i>define actions at</i>''|page name|''<i>from</i>''|folder location|", tooltip = "Load the defined actions from the given page and any sub-pages, taking account of the folder location.")
	public void defineActionsAtFrom(String pageName, String rootLocation) throws Exception {
		new DefineActionsOnPage(pageName, rootLocation, runtime).process();
	}

	public void clearDefinedActions() {
		TemporaryPlugBoardForRuntime.definedActionsRepository().clear();
	}

	public boolean toExpandDefinedActions() {
		return runtime.toExpandDefinedActions();
	}

	@SimpleAction(wiki = "|''<i>set expand defined actions</i>''|true or false|", tooltip = "If set to true, expand calls to defined actions regardless.")
	public void setExpandDefinedActions(boolean expandDefinedActions) {
		runtime.setExpandDefinedActions(expandDefinedActions);
	}

	@NullaryAction(tooltip = "Allow for old-style use of parameters in defined actions, with no @{}.")
	public void autoTranslateDefinedActionParameters() {
		setDynamicVariable(DefineAction.AUTO_TRANSLATE_DEFINED_ACTION_PARAMETERS, "true");
	}

	// --- RANDOM, TO, GET, FILE, HARVEST

	@SimpleAction(wiki = "|''<i>select randomly</i>''|dynamic variable name|", tooltip = "Randomly select one of the values in the rows of the rest of the table.")
	public RandomSelectTraverse selectRandomly(String var) {
		return new RandomSelectTraverse(var);
	}

	@SimpleAction(wiki = "|''<i>to</i>''|string|", tooltip = "Returns the string, which is handy for use with a special action, such as set.")
	public String to(String s) {
		return s;
	}

	@SimpleAction(wiki = "|''<i>get</i>''|string|", tooltip = "Returns the string, which is handy for use with a special action, such as show.")
	public String get(String s) {
		return s;
	}

	public void removeFile(String fileName) {
		new File(fileName).delete();
	}

	@SimpleAction(wiki = "|''<i>harvest</i>''|dynamic variables|''<i>using pattern</i>''|regular expression|''<i>from</i>''|text|", tooltip = "Uses the regular expression to extract out one or more dynamic variables from the text, which is handy when text needs to be pulled apart to get at the required data.")
	public boolean harvestUsingPatternFrom(String[] vars, String pattern, String text) {
		Matcher matcher = Pattern.compile(pattern, Pattern.DOTALL).matcher(text);
		if (!matcher.find())
			throw new FitLibraryException("Pattern doesn't match");
		int groups = matcher.groupCount();
		if (vars.length > groups)
			throw new FitLibraryException("Expected " + expectedGroups(vars) + ", but there " + actualGroups(groups));
		for (int v = 0; v < vars.length && v < groups; v++)
			setDynamicVariable(vars[v], matcher.group(v + 1));
		return true;
	}

	private String expectedGroups(String[] vars) {
		if (vars.length == 1)
			return "1 bracketed group";
		return vars.length + " bracketed groups";
	}

	private String actualGroups(int groups) {
		if (groups == 1)
			return "is only 1";
		return "are only " + groups;
	}

	// --- LOGGING

	@CompoundAction(wiki = "", tooltip = "Starts a table for configuring log4j.")
	public ConfigureLogger withLog4j() {
		return runtime.getConfigureLog4j().withNormalLog4j();
	}

	@CompoundAction(wiki = "", tooltip = "Starts a table for accessing the FitLibrary-specific logger.")
	public ConfigureLogger withFitLibraryLogger() {
		return runtime.getConfigureLog4j().withFitLibraryLogger();
	}

	@CompoundAction(wiki = "", tooltip = "Starts a table for accessing the Fixturing-specific logger.")
	public ConfigureLogger withFixturingLogger() {
		return runtime.getConfigureLog4j().withFixturingLogger();
	}

	// --- FILE LOGGING
	@SimpleAction(wiki = "|''<i>record to file</i>''|file name|", tooltip = "Writes the dynamic variables to the file (this needs a better explanation).")
	public void recordToFile(String fileName) {
		runtime.recordToFile(fileName);
		try {
			addDynamicVariablesFromFile(fileName);
		} catch (Exception e) {
			//
		}
	}

	@SimpleAction(wiki = "|''<i>start logging</i>''|file name|", tooltip = "Starts an obscure logging process to the file (this needs a better explanation).")
	public void startLogging(String fileName) {
		runtime.startLogging(fileName);
	}

	@SimpleAction(wiki = "|''<i>log message</i>''|message|", tooltip = "Log the message to an obscure logging process (this needs a better explanation).")
	public void logMessage(String s) {
		try {
			runtime.printToLog(s);
		} catch (IOException e) {
			throw new FitLibraryException(e.getMessage());
		}
	}

	@SimpleAction(wiki = "|''<i>log text</i>''|message|", tooltip = "Log the message to the log4j log.")
	public void logText(String s) {
		runtime.getConfigureLog4j().log(s);
	}

	// --- SHOW

	@AnAction(wiki = "", actionType = ActionType.PREFIX, isCompound = false, tooltip = "Show the result of the action in a cell added to the row.")
	public void show(Row row, String text) {
		row.addCell(text).shown();
		runtime.getDefinedActionCallManager().addShow(row);
	}

	@AnAction(wiki = "|'''<b>show</b>'''|title|'''<b>as</b>'''|message|'''<b>after table</b>'''|", actionType = ActionType.PREFIX, isCompound = false, tooltip = "Show the message in a titled area that's added after the table.")
	public void showAsAfterTable(String title, String s) {
		runtime.showAsAfterTable(title, s);
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}

	// --- SELECT

	@SimpleAction(wiki = "|''<i>select</i>''|name|", tooltip = "Select the named object so that it's actions take priority over others.")
	public void select(String name) {
		runtime.getTableEvaluator().select(name);
	}

	// --- CONFIG
	public Configuration configureFitLibrary() {
		return runtime.getConfiguration();
	}

	@CompoundAction(wiki = "|''<i>runtime configuration</i>''|", tooltip = "Starts a table for changing configuation of FitLibrary.")
	public Configuration runtimeConfiguration() {
		return runtime.getConfiguration();
	}

	// --- WHAT'S IN SCOPE

	@SimpleAction(wiki = "", tooltip = "Show all the actions in scope after the current table.")
	public boolean help() {
		showAsAfterTable("Actions", WhatIsInScope.what(runtime.getScope(), ""));
		return true;
	}

	@SimpleAction(wiki = "|''<i>help with</i>''|text|", tooltip = "Show all the actions in scope that contain the string after the current table.")
	public boolean helpWith(String substring) {
		showAsAfterTable("Actions", WhatIsInScope.what(runtime.getScope(), substring));
		return true;
	}

	// -------------------------------------- SPECIALS
	// -----------------------------------------
	/**
	 * Check that the result of the action in the first part of the row is less
	 * than the expected value in the last cell of the row.
	 */
	@AnAction(wiki = "|action...|'''<b>&lt;</b>'''|expected value|", actionType = ActionType.SUFFIX, isCompound = false, tooltip = "Take the result of the action and see whether it is less than the expected value.")
	public void lessThan(DoAction action, Object expect) throws Exception {
		comparison(action, expect, new Comparison() {
			// @Override
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public boolean compares(Comparable actual, Comparable expected) {
				return actual.compareTo(expected) < 0;
			}
		});
	}

	/**
	 * Check that the result of the action in the first part of the row is less
	 * than or equal to the expected value in the last cell of the row.
	 */
	@AnAction(wiki = "|action...|'''<b>&lt;=</b>'''|expected value|", actionType = ActionType.SUFFIX, isCompound = false, tooltip = "Take the result of the action and see whether it is less than or equal to the expected value.")
	public void lessThanEquals(DoAction action, Object expect) throws Exception {
		comparison(action, expect, new Comparison() {
			// @Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public boolean compares(Comparable actual, Comparable expected) {
				return actual.compareTo(expected) <= 0;
			}
		});
	}

	/**
	 * Check that the result of the action in the first part of the row is
	 * greater than the expected value in the last cell of the row.
	 */
	@AnAction(wiki = "|action...|'''<b>></b>'''|expected value|", actionType = ActionType.SUFFIX, isCompound = false, tooltip = "Take the result of the action and see whether it is greater than the expected value.")
	public void greaterThan(DoAction action, Object expect) throws Exception {
		comparison(action, expect, new Comparison() {
			// @Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public boolean compares(Comparable actual, Comparable expected) {
				return actual.compareTo(expected) > 0;
			}
		});
	}

	/**
	 * Check that the result of the action in the first part of the row is
	 * greater than or equal to the expected value in the last cell of the row.
	 */
	@AnAction(wiki = "|action...|'''<b>>=</b>'''|expected value|", actionType = ActionType.SUFFIX, isCompound = false, tooltip = "Take the result of the action and see whether it is greater than or equal to the expected value.")
	public void greaterThanEquals(DoAction action, Object expect) throws Exception {
		comparison(action, expect, new Comparison() {
			// @Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public boolean compares(Comparable actual, Comparable expected) {
				return actual.compareTo(expected) >= 0;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	private void comparison(DoAction action, Object expected, Comparison compare) throws Exception {
		if (!(expected instanceof Comparable<?>))
			throw new FitLibraryException("Expected value is not a Comparable");
		Object actual = action.run();
		if (actual instanceof Comparable<?>) {
			if (compare.compares((Comparable) actual, (Comparable) expected))
				action.cellAt(1).pass();
			else
				action.cellAt(1).fail(actual.toString());
		} else if (actual == null)
			throw new FitLibraryException("Actual value is null");
		else
			throw new FitLibraryException("Actual value is not a Comparable: " + actual.getClass().getName());
	}

	public interface Comparison {
		@SuppressWarnings("rawtypes")
		boolean compares(Comparable actual, Comparable expected);
	}

	/**
	 * Check that the result of the action in the first part of the row, as a
	 * string, contains the string in the last cell of the row.
	 */
	@AnAction(wiki = "|action...|'''<b>contains</b>'''|expected value|", actionType = ActionType.SUFFIX, isCompound = false, tooltip = "Take the result of the action and see whether it contains the expected value.")
	public void contains(DoAction action, String s) throws Exception {
		if (s == null) {
			action.cellAt(1).fail("expected is null");
			return;
		}
		Object run = action.run();
		if (run == null) {
			action.cellAt(1).fail("result is null");
			return;
		}
		String result = run.toString();
		if (result.contains(s))
			action.cellAt(1).pass();
		else
			action.cellAt(1).fail(result);
	}

	/**
	 * Check that the result of the action in the first part of the row, as a
	 * string, contains the string in the last cell of the row.
	 */
	@AnAction(wiki = "|action...|'''<b>does not contain</b>'''|unexpected value|", actionType = ActionType.SUFFIX, isCompound = false, tooltip = "Take the result of the action and see whether it does not contain the unexpected value.")
	public void doesNotContain(DoAction action, String s) throws Exception {
		if (s == null) {
			action.cellAt(1).fail("expected is null");
			return;
		}
		Object run = action.run();
		if (run == null) {
			action.cellAt(1).fail("result is null");
			return;
		}
		String result = run.toString();
		if (!result.contains(s))
			action.cellAt(1).pass();
		else if (result.equals(s))
			action.cellAt(1).fail();
		else
			action.cellAt(1).fail(result);
	}

	/**
	 * Check that the result of the action in the first part of the row, as a
	 * string, contains the string in the last cell of the row.
	 */
	@AnAction(wiki = "|action...|'''<b>eventually contains</b>'''|expected value|", actionType = ActionType.SUFFIX, isCompound = false, tooltip = "Take the result of the action and see whether it eventually contains the expected value. It fails if the timeout period for becomes is exceeded.")
	public void eventuallyContains(final DoAction action, final String s) throws Exception {
		if (s == null) {
			action.cellAt(1).fail("expected is null");
			return;
		}
		Eventually eventually = new Eventually(getTimeout(BECOMES_TIMEOUT));
		PassFail answer = eventually.poll(new PollForPass() {
			// @Override
			public PassFail result() throws Exception {
				Object run = action.run();
				if (run == null)
					return new PassFail(false, null);
				String result = run.toString();
				return new PassFail(result.contains(s), result);
			}
		});
		if (answer != null && answer.result != null) {
			if (answer.hasPassed)
				action.cellAt(1).pass();
			else
				action.cellAt(1).fail(answer.result.toString());
		} else
			action.cellAt(1).fail("result is null");
	}

	@AnAction(wiki = "", actionType = ActionType.PREFIX, isCompound = false, tooltip = "Show the value of the action is a cell added to the row.")
	public void show(DoAction action) throws Exception {
		Object result = action.run();
		if (result != null)
			action.showResult(result);
	}

	@AnAction(wiki = "", actionType = ActionType.PREFIX, isCompound = false, tooltip = "Show the value of the action in a cell added to the row. But the text is escaped so that any tags, etc will be visible.")
	public void showEscaped(DoAction action) throws Exception {
		Object result = action.run();
		if (result != null)
			action.show("<pre>" + Fixture.escape(result.toString()) + "</pre>");
	}

	@AnAction(wiki = "", actionType = ActionType.PREFIX, isCompound = false, tooltip = "Show the value of the action in a cell added to the row. The tags will be visible.")
	public void showWithTags(DoAction action) throws Exception {
		Object result = action.run();
		if (result != null)
			action.show("<pre>" + Fixture.escape(result.toString()) + "</pre>");
	}

	@AnAction(wiki = "", actionType = ActionType.PREFIX, isCompound = false, tooltip = "Show the value of the action is a cell added to the row. The text retains its layout across lines.")
	public void showPredefined(DoAction action) throws Exception {
		Object result = action.run();
		if (result != null)
			action.show("<pre>" + result.toString() + "</pre>");
	}

	@AnAction(wiki = "", actionType = ActionType.PREFIX, isCompound = false, tooltip = "Show the value of the action in a folding area added after the table.")
	public void showAfter(DoAction action) throws Exception {
		Object result = action.run();
		if (result != null)
			action.showAfter(result);
	}

	@AnAction(wiki = "|'''<b>show after as</b>'''|action...|", actionType = ActionType.PREFIX, isCompound = false, tooltip = "Show the value of the action in a titled folding area added after the table.")
	public void showAfterAs(String title, DoAction action) throws Exception {
		Object result = action.run();
		if (result != null)
			action.showAfterAs(title, result);
	}

	/**
	 * Check that the result of the action in the first part of the row, as a
	 * string, matches the regular expression in the last cell of the row.
	 */
	@AnAction(wiki = "|action...|'''<b>matches</b>'''|regular expression|", actionType = ActionType.SUFFIX, isCompound = false, tooltip = "Take the result of the action and see whether it matches the regular expression.")
	public void matches(DoAction action, String pattern) throws Exception {
		if (pattern == null) {
			action.cellAt(1).fail("expected is null");
			return;
		}
		Object run = action.run();
		if (run == null) {
			action.cellAt(1).fail("result is null");
			return;
		}
		String result = run.toString();
		boolean matches = Pattern.compile(".*" + pattern + ".*", Pattern.DOTALL).matcher(result).matches();
		if (matches)
			action.cellAt(1).pass();
		else
			action.cellAt(1).fail(result);
	}

	/**
	 * Check that the result of the action in the first part of the row, as a
	 * string, does not match the regular expression in the last cell of the
	 * row.
	 */
	@AnAction(wiki = "|action...|'''<b>does not match</b>'''|regular expression|", actionType = ActionType.SUFFIX, isCompound = false, tooltip = "Take the result of the action and see whether it does not match the regular expression.")
	public void doesNotMatch(DoAction action, String pattern) throws Exception {
		if (pattern == null) {
			action.cellAt(1).fail("expected is null");
			return;
		}
		Object run = action.run();
		if (run == null) {
			action.cellAt(1).fail("result is null");
			return;
		}
		String result = run.toString();
		boolean matches = Pattern.compile(".*" + pattern + ".*", Pattern.DOTALL).matcher(result).matches();
		if (!matches)
			action.cellAt(1).pass();
		else if (result.equals(pattern))
			action.cellAt(1).fail();
		else
			action.cellAt(1).fail(result);
	}

	/**
	 * Check that the result of the action in the first part of the row, as a
	 * string, eventually matches the regular expression in the last cell of the
	 * row.
	 */
	@AnAction(wiki = "|action...|'''<b>eventually matches</b>'''|regular expression|", actionType = ActionType.SUFFIX, isCompound = false, tooltip = "Take the result of the action and see whether it eventually matches the regular expression. It fails if the timeout period for becomes is exceeded.")
	public void eventuallyMatches(final DoAction action, final String s) throws Exception {
		if (s == null) {
			action.cellAt(1).fail("expected is null");
			return;
		}
		final Pattern pattern = Pattern.compile(".*" + s + ".*", Pattern.DOTALL);
		Eventually eventually = new Eventually(getTimeout(BECOMES_TIMEOUT));
		PassFail answer = eventually.poll(new PollForPass() {
			// @Override
			public PassFail result() throws Exception {
				Object run = action.run();
				if (run == null)
					return new PassFail(false, null);
				String result = run.toString();
				return new PassFail(pattern.matcher(result).matches(), result);
			}
		});
		if (answer != null && answer.result != null) {
			if (answer.hasPassed)
				action.cellAt(1).pass();
			else if (answer.result != null)
				action.cellAt(1).fail(answer.result.toString());
			else
				action.cellAt(1).fail();
		} else
			action.cellAt(1).fail("result is null");
	}

	/**
	 * Check that the action in the rest of the row succeeds. o If a boolean is
	 * returned, it must be true. o For other result types, no exception should
	 * be thrown. It's no longer needed, because the same result can now be
	 * achieved with a boolean method.
	 */
	public Boolean ensure(DoAction action) throws Exception {
		Object result = action.run();
		if (result instanceof Boolean)
			return ((Boolean) result).booleanValue();
		if (result == null)
			return true;
		return null;
	}

	@AnAction(wiki = "", actionType = ActionType.PREFIX, isCompound = false, tooltip = "Passes if the action fails or gives an error. Fails otherwise. Use notTrue if errors should be shown.")
	public Boolean not(DoAction action) throws Exception {
		Object result = null;
		try {
			result = action.runWithNoColouring();
			if (result instanceof Boolean)
				return !((Boolean) result).booleanValue();
		} catch (IgnoredException e) {
			if (e.getIgnoredException() != null)
				action.show(e.getIgnoredException().getMessage());
			return true;
		} catch (Exception e) {
			Throwable embedded = PlugBoard.exceptionHandling.unwrapThrowable(e);
			if (embedded instanceof FitLibraryShowException)
				action.show(((FitLibraryShowException) embedded).getResult().getHtmlString());
			return true;
		}
		if (result == null)
			throw new NotRejectedException();
		return null;
	}

	@AnAction(wiki = "", actionType = ActionType.PREFIX, isCompound = false, tooltip = "Passes if the action fails and vice versa. Any errors are shown.")
	public boolean notTrue(DoAction action) throws Exception {
		Object result = action.run();
		if (result instanceof Boolean)
			return !((Boolean) result).booleanValue();
		throw new NotRejectedException();
	}

	@AnAction(wiki = "", actionType = ActionType.PREFIX, isCompound = false, tooltip = "Passes if the action fails or gives an error. Fails otherwise. Use notTrue if errors should be shown.")
	public boolean reject(DoAction action) throws Exception {
		return not(action);
	}

	/**
	 * Log result to a file
	 */
	@AnAction(wiki = "", actionType = ActionType.PREFIX, isCompound = false, tooltip = "Logs the result of the action to the current (old-style) log file, if any.")
	public void log(DoAction action) throws Exception {
		Object result = action.run();
		if (result != null)
			logMessage(result.toString());
	}

	/**
	 * Log result to log4j
	 */
	@AnAction(wiki = "", actionType = ActionType.PREFIX, isCompound = false, tooltip = "Logs the result of the action to log4j.")
	public void logged(DoAction action) throws Exception {
		Object result = action.run();
		if (result != null)
			runtime.getConfigureLog4j().log(result.toString());
	}

	/**
	 * Allow access to String methods
	 */
	@AnAction(wiki = "|'''<b>as string</b>'''|", actionType = ActionType.PREFIX, isCompound = true, tooltip = "The object that results from the action can now be tested as a String.")
	public StringAdapter asString(DoAction action) throws Exception {
		Object result = action.run();
		if (result != null)
			return new StringAdapter(result.toString());
		return null;
	}

	/**
	 * Allow the action result to be treated as an extra (unnamed) fixturing
	 * object
	 */
	@AnAction(wiki = "", actionType = ActionType.PREFIX, isCompound = false, tooltip = "The object that results from the action is added to the scope, so actions can be called on it in future.")
	public void alsoRun(DoAction action) throws Exception {
		alsoRunAs(action, "Unnamed#" + (UNNAMED++));
	}

	/**
	 * Allow the action result to be treated as an extra fixturing object
	 */
	@AnAction(wiki = "|action...|'''<b>also run as</b>'''|name|", actionType = ActionType.SELF_FORMAT, isCompound = false, tooltip = "The object that results from the action is added to the scope, so actions can be called on it in future and it can be selected by name.")
	public void alsoRunAs(DoAction action, String name) throws Exception {
		Object result = action.run();
		if (result != null) {
			if (!(result instanceof TypedObject))
				result = new GenericTypedObject(result);
			runtime.addNamedObject(name, (TypedObject) result);
		}
	}

	@AnAction(wiki = "", actionType = ActionType.PREFIX, isCompound = false, tooltip = "The action must return an object that implements OnError.\nOnce there are fails or errors in a storytest, the method stopOnError() is called with the counts after each action.")
	public void informOnFailOrErrorInStorytest(DoAction action) throws Exception {
		Object result = action.run();
		if (result == null || !(result instanceof OnError))
			throw new FitLibraryException("Must be an object of type OnError");
		runtime.registerOnErrorHandler((OnError) result);
	}

	// public void is(DoAction action, Object expected) throws Exception {
	// Object result = action.run();
	// if (action.equals(result,expected))
	// action.cellAt(1).pass();
	// else
	// action.cellAt(1).fail(result.toString());
	// }
}

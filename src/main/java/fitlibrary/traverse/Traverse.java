/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import fitlibrary.annotation.ShowSelectedActions;
import fitlibrary.closure.Closure;
import fitlibrary.differences.DifferenceInterface;
import fitlibrary.differences.FitNesseDifference;
import fitlibrary.differences.LocalFile;
import fitlibrary.dynamicVariable.DynamicVariables;
import fitlibrary.dynamicVariable.VariableResolver;
import fitlibrary.exception.CycleException;
import fitlibrary.flow.GlobalActionScope;
import fitlibrary.flow.IScope;
import fitlibrary.global.PlugBoard;
import fitlibrary.parser.lookup.ParseDelegation;
import fitlibrary.runResults.TestResults;
import fitlibrary.runResults.TestResultsFactory;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.RowOnList;
import fitlibrary.table.Table;
import fitlibrary.table.Tables;
import fitlibrary.typed.NonGenericTyped;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;
import fitlibrary.utility.Pair;
import fitlibraryGeneric.typed.GenericTyped;
import fitlibraryGeneric.typed.GenericTypedObject;

@ShowSelectedActions
public abstract class Traverse implements Evaluator, ShowAfter {
	public static DifferenceInterface FITNESSE_DIFFERENCES = new FitNesseDifference();
	protected static FitHandler FIT_HANDLER = new FitHandler();
	public static final String FITNESSE_URL_KEY = "fitNesse.url";
	private TypedObject typedObjectUnderTest = new GenericTypedObject(null);
	protected RuntimeContextInternal runtimeContext;

	public Traverse() {
		// No SUT
	}

	public Traverse(Object sut) {
		setSystemUnderTest(sut);
	}

	public Traverse(TypedObject typedObjectUnderTest) {
		this.typedObjectUnderTest = typedObjectUnderTest;
	}

	/**
	 * Registers a delegate, a class that will handle parsing of other types of
	 * values.
	 */
	protected void registerParseDelegate(Class<?> type, Class<?> parseDelegate) {
		ParseDelegation.registerParseDelegate(type, parseDelegate);
	}

	/**
	 * Registers a delegate object that will handle parsing of other types of
	 * values.
	 */
	protected void registerParseDelegate(Class<?> type, Object parseDelegate) {
		ParseDelegation.registerParseDelegate(type, parseDelegate);
	}

	/**
	 * Registers a delegate object that will handle parsing of the given type
	 * and any subtype.
	 */
	protected void registerSuperParseDelegate(Class<?> type, Object superParseDelegate) {
		ParseDelegation.registerSuperParseDelegate(type, superParseDelegate);
	}

	/**
	 * Set the systemUnderTest. If an action can't be satisfied by the Traverse,
	 * the systemUnderTest is tried instead. Thus the Traverse is an adapter
	 * with methods just when they're needed.
	 */
	public void setSystemUnderTest(Object sut) {
		if (sut instanceof TypedObject)
			setTypedSystemUnderTest((TypedObject) sut);
		else
			setTypedSystemUnderTest(asTypedObject(sut));
	}

	private boolean cycleSUT(DomainAdapter domainAdapter, Object sut) {
		if (domainAdapter == sut)
			return true;
		if (sut instanceof DomainAdapter)
			return cycleSUT(domainAdapter, ((DomainAdapter) sut).getSystemUnderTest());
		return false;
	}

	// @Override
	public Object getSystemUnderTest() {
		if (typedObjectUnderTest == null)
			return null;
		return typedObjectUnderTest.getSubject();
	}

	// @Override
	public TypedObject getTypedSystemUnderTest() {
		return typedObjectUnderTest;
	}

	public void setTypedSystemUnderTest(TypedObject typedObject) {
		if (cycleSUT(this, typedObject.getSubject()))
			throw new CycleException("systemUnderTest", this, typedObject.getSubject());
		this.typedObjectUnderTest = typedObject;
	}

	public static void setDifferenceStrategy(DifferenceInterface difference) {
		FITNESSE_DIFFERENCES = difference;
	}

	public static DifferenceInterface getDifferenceStrategy() {
		return FITNESSE_DIFFERENCES;
	}

	public static LocalFile getLocalFile(String localFileName) {
		return FITNESSE_DIFFERENCES.getLocalFile(localFileName);
	}

	public static LocalFile getGlobalFile(String localFileName) {
		return FITNESSE_DIFFERENCES.getGlobalFile(localFileName);
	}

	public static String htmlLink(File file) {
		return FITNESSE_DIFFERENCES.getGlobalFile(file).htmlLink();
	}

	public static void setContext(File reportDiry) {
		FITNESSE_DIFFERENCES.setContext(reportDiry);
	}

	public String extendedCamel(String suppliedName) {
		return runtimeContext.extendedCamel(suppliedName);
	}

	public void interpretWithinScope(Table table, RuntimeContextInternal runtime, TestResults testResults) {
		setRuntimeContext(runtime);
		IScope scope = runtime.getScope();
		scope.temporarilyAdd(this);
		try {
			interpretAfterFirstRow(table, testResults);
		} finally {
			scope.removeTemporary(this);
		}
	}

	public void interpretInnerTableWithInScope(Table table, RuntimeContextInternal runtime, TestResults testResults) {
		RowOnList row = new RowOnList();
		table.add(0, row);
		row.setIsHidden();
		try {
			interpretWithinScope(table, runtime, testResults);
		} finally {
			table.removeElementAt(0);
		}
	}

	public boolean doesInnerTablePass(Table table, RuntimeContextInternal runtime, TestResults testResults) {
		TestResults innerResults = TestResultsFactory.testResults();
		interpretInnerTableWithInScope(table, runtime, innerResults);
		testResults.add(innerResults);
		return innerResults.passed();
	}

	public boolean doesTablePass(Table table, RuntimeContextInternal runtime, TestResults testResults) {
		TestResults innerResults = TestResultsFactory.testResults();
		interpretWithinScope(table, runtime, innerResults);
		testResults.add(innerResults);
		return innerResults.passed();
	}

	public static FitHandler getFitHandler() {
		return FIT_HANDLER;
	}

	public static Typed asTyped(Class<?> type) {
		return new NonGenericTyped(type);
	}

	public static Typed asTyped(Object object) {
		return new NonGenericTyped(object.getClass());
	}

	public static Typed asTyped(Method method) {
		return new GenericTyped(method.getGenericReturnType(), true);
	}

	protected TypedObject asTypedObject() {
		return new GenericTypedObject(this);
	}

	public static TypedObject asTypedObject(Object sut) {
		if (sut instanceof TypedObject)
			return (TypedObject) sut;
		return new GenericTypedObject(sut);
	}

	public void callStartCreatingObjectMethod(TypedObject object) throws IllegalAccessException,
			InvocationTargetException {
		IScope scope = this.getRuntimeContext().getScope();
		scope.temporarilyAdd(this);
		try {
			callCreatingMethod("startCreatingObject", object.getSubject());
		} finally {
			scope.removeTemporary(this);
		}
	}

	public void callStartCreatingObjectMethod(Object element) throws IllegalAccessException, InvocationTargetException {
		callCreatingMethod("startCreatingObject", element);
	}

	public void callEndCreatingObjectMethod(TypedObject object) throws IllegalAccessException,
			InvocationTargetException {
		if (object != null)
			callCreatingMethod("endCreatingObject", object.getSubject());
	}

	public void callEndCreatingObjectMethod(Object element) throws IllegalAccessException, InvocationTargetException {
		callCreatingMethod("endCreatingObject", element);
	}

	// @Override
	public RuntimeContextInternal getRuntimeContext() {
		if (runtimeContext == null)
			throw new NullPointerException("Runtime has not been injected into " + this
					+ ". See .FitLibrary.AdvancedTutorial.RuntimeInjection.");
		return runtimeContext;
	}

	// @Override
	public IScope getScope() {
		return getRuntimeContext().getScope();
	}

	// @Override
	public void setRuntimeContext(RuntimeContextInternal runtimeContext) {
		this.runtimeContext = runtimeContext;
		setRuntimeContextDownSutChain(this, runtimeContext);
	}

	private static void setRuntimeContextDownSutChain(Object object, RuntimeContextInternal runtimeContext) {
		if (object instanceof DomainAdapter)
			setRuntimeContextDownSutChain(((DomainAdapter) object).getSystemUnderTest(), runtimeContext);
	}

	public DynamicVariables getDynamicVariables() {
		return getRuntimeContext().getDynamicVariables();
	}

	public VariableResolver getResolver() {
		return getDynamicVariables();
	}

	// @Override
	public Pair<String, Tables> resolve(String key) {
		return getResolver().resolve(key);
	}

	// @Override
	public void setDynamicVariable(String key, Object value) {
		getDynamicVariables().put(key, value);
	}

	public Object getDynamicVariable(String key) {
		return getDynamicVariables().get(key);
	}

	private void callCreatingMethod(String creatingMethodName, Object element) throws IllegalAccessException,
			InvocationTargetException {
		Closure startCreatingMethod = PlugBoard.lookupTarget.findFixturingMethod(this, creatingMethodName,
				(new Class[] { Object.class }));
		if (startCreatingMethod != null)
			startCreatingMethod.invoke(new Object[] { element });
	}

	public void show(String s) {
		runtimeContext.show(s);
	}

	public void showAfterTable(String s) {
		showAsAfterTable("Logs", s);
	}

	// @Override
	public void showAsAfterTable(String title, String s) {
		global().showAsAfterTable(title, s);
	}

	protected GlobalActionScope global() {
		return getRuntimeContext().getGlobal();
	}

	// @Override
	public abstract Object interpretAfterFirstRow(Table table, TestResults testResults);
}

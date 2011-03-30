package org.jtester.module.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jtester.fit.util.SymbolUtil;
import org.jtester.module.TestListener;
import org.jtester.module.dbfit.AutoFindDbFit;
import org.jtester.module.dbfit.DatabaseFixture;
import org.jtester.module.dbfit.DbFitRunner;
import org.jtester.module.tracer.jdbc.JdbcTracerManager;
import org.jtester.module.utils.DatabaseModuleHelper;
import org.jtester.module.utils.DbFitModuleHelper;
import org.jtester.unitils.dbfit.DbFit;
import org.jtester.unitils.dbfit.FitVar;
import org.jtester.utility.AnnotationUtils;

public class DbFitModule implements Module {
	public void init(Properties configuration) {
		;
	}

	public void afterInit() {
		;
	}

	public TestListener getTestListener() {
		return new DbFitTestListener();
	}

	protected class DbFitTestListener extends TestListener {
		@Override
		public void beforeTestClass(Class<?> testClass) {
			JdbcTracerManager.suspendThreadMonitorJdbc();// 不记录dbfit的sql语句

			DbFit dbFit = AnnotationUtils.getClassLevelAnnotation(DbFit.class, testClass);
			setSymbols(dbFit);

			String[] wikis = AutoFindDbFit.autoFindClassWhen(testClass);
			// class基本上的wiki没有事务的概念,所有操作都是即时提交
			DbFitModuleHelper.setTransactionDisabled(true);
			runDbWiki(testClass, wikis);
			JdbcTracerManager.continueThreadMonitorJdbc();
		}

		/**
		 * dbfit放在beforetestmethod中初始化，而不是放在beforeTestSetUp中初始化的原因是：<br>
		 * beforeTestSetUp中的异常会导致后面所有的测试方法broken <br>
		 * <br>{@inheritDoc}
		 */
		@Override
		public void beforeTestSetUp(Object testObject, Method testMethod) {
			JdbcTracerManager.suspendThreadMonitorJdbc();// 不记录dbfit的sql语句

			boolean isTransactionDisabled = DatabaseModuleHelper.isDisabledTransaction(testObject, testMethod);
			DbFitModuleHelper.setTransactionDisabled(isTransactionDisabled);

			DbFit dbFit = testMethod.getAnnotation(DbFit.class);
			setSymbols(dbFit);

			Class<?> testedClazz = testObject.getClass();
			String[] wikis = AutoFindDbFit.autoFindMethodWhen(testedClazz, testMethod);
			runDbWiki(testedClazz, wikis);

			JdbcTracerManager.continueThreadMonitorJdbc();
		}

		@Override
		public void afterTestMethod(Object testObject, Method testMethod, Throwable testThrowable) {
			JdbcTracerManager.suspendThreadMonitorJdbc();

			DbFit dbFit = testMethod.getAnnotation(DbFit.class);
			setSymbols(dbFit);

			Class<?> testedClazz = testObject.getClass();
			String[] wikis = AutoFindDbFit.autoFindMethodThen(testedClazz, testMethod);
			runDbWiki(testedClazz, wikis);

			SymbolUtil.cleanSymbols();
			DatabaseFixture.endTransactional();

			JdbcTracerManager.continueThreadMonitorJdbc();
		}
	}

	public static void setSymbols(DbFit dbFit) {
		if (dbFit == null) {
			return;
		}
		Map<String, Object> symbols = exactFitVars(dbFit);
		SymbolUtil.setSymbol(symbols);
	}

	/**
	 * 获取DbFit中设置的参数列表
	 * 
	 * @param dbFit
	 * @return
	 */
	private static Map<String, Object> exactFitVars(DbFit dbFit) {
		Map<String, Object> symbols = new HashMap<String, Object>();
		if (dbFit == null) {
			return symbols;
		}
		FitVar[] vars = dbFit.vars();
		if (vars == null) {
			return symbols;
		}
		for (FitVar var : vars) {
			symbols.put(var.key(), var.value());
		}
		return symbols;
	}

	private static void runDbWiki(Class<?> testClazz, String[] wikis) {
		if (wikis == null) {
			return;
		}
		for (String wiki : wikis) {
			DbFitRunner.runDbFit(testClazz, wiki);
		}
	}
}

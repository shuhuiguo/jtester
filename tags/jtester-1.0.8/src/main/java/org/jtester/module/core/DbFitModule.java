package org.jtester.module.core;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.FitVar;
import org.jtester.core.context.DbFitContext;
import org.jtester.core.context.DbFitContext.RunIn;
import org.jtester.fit.util.SymbolUtil;
import org.jtester.module.TestListener;
import org.jtester.module.database.SqlRunner;
import org.jtester.module.database.environment.DBEnvironment;
import org.jtester.module.database.environment.DBEnvironmentFactory;
import org.jtester.module.dbfit.AutoFindDbFit;
import org.jtester.module.dbfit.DbFitRunner;
import org.jtester.module.tracer.jdbc.JdbcTracerManager;
import org.jtester.utility.AnnotationUtils;

@SuppressWarnings("rawtypes")
public class DbFitModule implements Module {
	private final static Logger log4j = Logger.getLogger(DbFitModule.class);

	public void init() {
		;
	}

	public void afterInit() {
		;
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

	/**
	 * 运行数据准备(验证)文件<br>
	 * 可以是wiki文件，sql文件。<br>
	 * 未来计划添加其他格式的文件，比如Excel, csv, xml等
	 * 
	 * @param testClazz
	 * @param files
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	private static void runDbFiles(Class testClazz, String[] files) throws SQLException, FileNotFoundException {
		if (files == null) {
			return;
		}
		DbFitContext.setRunIn(RunIn.TestCase);
		for (String file : files) {
			if (file == null || "".equals(file.trim())) {
				throw new RuntimeException("@DbFit file name can't be null or emptry.");
			}
			if (file.endsWith(".wiki")) {
				DbFitRunner.runDbFit(testClazz, file);
			} else if (file.endsWith(".sql")) {
				SqlRunner.executeFromFile(testClazz, file);
				log4j.info(String.format("execute sql file[%s] successfully.", file));
			} else {
				String error = String.format(
						"@DbFit only support wiki file or sql file, please check the file[%s] format.", file);
				throw new RuntimeException(error);
			}
		}
	}

	public TestListener getTestListener() {
		return new DbFitTestListener();
	}

	protected class DbFitTestListener extends TestListener {
		@Override
		public void beforeTestClass(Object testObject) throws Exception {
			Class clazz = testObject.getClass();
			DbFit dbFit = AnnotationUtils.getClassLevelAnnotation(DbFit.class, clazz);
			if (dbFit == null) {
				return;
			}
			JdbcTracerManager.suspendThreadMonitorJdbc();// 不记录dbfit的sql语句

			DBEnvironmentFactory.changeDBEnvironment(dbFit.dataSource());
			DbFitModule.setSymbols(dbFit);
			String[] files = AutoFindDbFit.autoFindClassWhen(clazz);
			runDbFiles(clazz, files);
			DBEnvironmentFactory.commitCurrentDBEnvironment();

			JdbcTracerManager.continueThreadMonitorJdbc();
		}

		/**
		 * dbfit放在beforetestmethod中初始化，而不是放在beforeTestSetUp中初始化的原因是：<br>
		 * beforeTestSetUp中的异常会导致后面所有的测试方法broken <br>
		 * <br>{@inheritDoc}
		 */
		@Override
		public void beforeTestMethod(Object testObject, Method testMethod) throws Exception {
			Class testedClazz = testObject.getClass();
			JdbcTracerManager.suspendThreadMonitorJdbc();// 不记录dbfit的sql语句

			DbFit dbFit = testMethod.getAnnotation(DbFit.class);
			if (dbFit != null) {
				DBEnvironmentFactory.changeDBEnvironment(dbFit.dataSource());
				DbFitModule.setSymbols(dbFit);
			}
			String[] files = AutoFindDbFit.autoFindMethodWhen(testedClazz, testMethod);
			runDbFiles(testedClazz, files);

			JdbcTracerManager.continueThreadMonitorJdbc();
		}

		@Override
		public void afterTestMethod(Object testObject, Method testMethod, Throwable testThrowable) throws Exception {
			Class testedClazz = testObject.getClass();
			JdbcTracerManager.suspendThreadMonitorJdbc();

			DbFit dbFit = testMethod.getAnnotation(DbFit.class);
			if (dbFit != null) {
				DBEnvironmentFactory.changeDBEnvironment(dbFit.dataSource());
				DbFitModule.setSymbols(dbFit);
			}
			String[] files = AutoFindDbFit.autoFindMethodThen(testedClazz, testMethod);
			runDbFiles(testedClazz, files);
			SymbolUtil.cleanSymbols();

			DBEnvironmentFactory.changeDBEnvironment(DBEnvironment.DEFAULT_DATASOURCE_NAME);
			JdbcTracerManager.continueThreadMonitorJdbc();
		}

		@Override
		protected String getName() {
			return "DbFitTestListener";
		}
	}
}

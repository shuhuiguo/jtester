/*
 * Copyright 2008,  Unitils.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jtester.module.core;

import static org.jtester.module.utils.ModuleUtils.getAnnotationPropertyDefaults;
import static org.jtester.module.utils.ModuleUtils.getEnumValueReplaceDefault;
import static org.jtester.utility.AnnotationUtils.getMethodOrClassLevelAnnotationProperty;
import static org.jtester.annotations.Transactional.TransactionMode.COMMIT;
import static org.jtester.annotations.Transactional.TransactionMode.DEFAULT;
import static org.jtester.annotations.Transactional.TransactionMode.DISABLED;
import static org.jtester.annotations.Transactional.TransactionMode.ROLLBACK;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jtester.annotations.Transactional;
import org.jtester.module.TestListener;
import org.jtester.module.database.ConstraintsDisabler;
import org.jtester.module.database.DataSourceFactory;
import org.jtester.module.database.DatabaseAccessing;
import org.jtester.module.database.DatabaseModuleConfigUtils;
import org.jtester.module.database.support.DefaultSQLHandler;
import org.jtester.module.database.support.SQLHandler;
import org.jtester.module.database.transaction.TransactionManager;
import org.jtester.module.utils.ConfigUtil;
import org.jtester.module.utils.DatabaseModuleHelper;
import org.springframework.transaction.PlatformTransactionManager;
import org.jtester.annotations.Transactional.TransactionMode;

/**
 * Module that provides support for database testing: Creation of a datasource
 * that connects to the test database, support for executing tests in a
 * transaction and automatic maintenance of the test database.
 * <p/>
 * A datasource will be created the first time one is requested. Which type of
 * datasource will be created depends on the configured
 * {@link DataSourceFactory}. By default this will be a pooled datasource that
 * gets its connection-url, username and password from the unitils
 * configuration.
 * <p/>
 * The created datasource can be injected into a field of the test by annotating
 * the field with {@link TestDataSource}. It can then be used to install it in
 * your DAO or other class under test.
 * <p/>
 * If the DBMaintainer is enabled (by setting
 * {@link #PROPERTY_UPDATEDATABASESCHEMA_ENABLED} to true), the test database
 * schema will automatically be updated if needed. This check will be performed
 * once during your test-suite run, namely when the data source is created.
 * <p/>
 * If the test class or method is annotated with {@link Transactional} with
 * transaction mode {@link TransactionMode#COMMIT} or
 * {@link TransactionMode#ROLLBACK}, or if the property
 * 'DatabaseModule.Transactional.value.default' was set to 'commit' or
 * 'rollback', every test is executed in a transaction.
 * 
 */
public class DatabaseModule implements Module {
	/* The logger instance for this class */
	static Log logger = LogFactory.getLog(DatabaseModule.class);

	/**
	 * Property indicating if the database schema should be updated before
	 * performing the tests
	 */
	public static final String PROPERTY_UPDATEDATABASESCHEMA_ENABLED = "updateDataBaseSchema.enabled";

	/**
	 * Map holding the default configuration of the database module annotations
	 */
	protected Map<Class<? extends Annotation>, Map<String, String>> defaultAnnotationPropertyValues;

	/**
	 * The configuration of Unitils
	 */
	protected Properties configuration;

	/**
	 * Initializes this module using the given <code>Configuration</code>
	 * 
	 * @param configuration
	 *            The config, not null
	 */
	@SuppressWarnings("unchecked")
	public void init(Properties configuration) {
		this.configuration = configuration;

		defaultAnnotationPropertyValues = getAnnotationPropertyDefaults(DatabaseModule.class, configuration,
				Transactional.class);

		PlatformTransactionManager.class.getName();
	}

	/**
	 * Initializes the spring support object
	 */
	public void afterInit() {
	}

	/**
	 * @param testObject
	 *            The test object, not null
	 * @param testMethod
	 *            The test method, not null
	 * @return The {@link TransactionMode} for the given object
	 */
	protected TransactionMode getTransactionMode(Object testObject, Method testMethod) {
		TransactionMode transactionMode = getMethodOrClassLevelAnnotationProperty(Transactional.class, "value",
				DEFAULT, testMethod, testObject.getClass());
		transactionMode = getEnumValueReplaceDefault(Transactional.class, "value", transactionMode,
				defaultAnnotationPropertyValues);
		return transactionMode;
	}

	/**
	 * Starts a transaction. If the Unitils DataSource was not loaded yet, we
	 * simply remember that a transaction was started but don't actually start
	 * it. If the DataSource is loaded within this test, the transaction will be
	 * started immediately after loading the DataSource.
	 * 
	 * @param testObject
	 *            The test object, not null
	 * @param testMethod
	 *            The test method, not null
	 */
	protected void startTransactionForTestMethod(Object testObject, Method testMethod) {
		if (isTransactionsEnabled(testObject, testMethod)) {
			TransactionManager.startTransaction(testObject);
		}
	}

	/**
	 * Commits or rollbacks the current transaction, if transactions are enabled
	 * and a transactionManager is active for the given testObject
	 * 
	 * @param testObject
	 *            The test object, not null
	 * @param testMethod
	 *            The test method, not null
	 */
	protected void endTransactionForTestMethod(Object testObject, Method testMethod) {
		if (isTransactionsEnabled(testObject, testMethod)) {
			if (getTransactionMode(testObject, testMethod) == COMMIT) {
				TransactionManager.commitTransaction(testObject);
			} else if (getTransactionMode(testObject, testMethod) == ROLLBACK) {
				TransactionManager.rollbackTransaction(testObject);
			}
		}
	}

	/**
	 * @param testObject
	 *            The test object, not null
	 * @param testMethod
	 *            The test method, not null
	 * @return Whether transactions are enabled for the given test method and
	 *         test object
	 */
	public boolean isTransactionsEnabled(Object testObject, Method testMethod) {
		TransactionMode transactionMode = getTransactionMode(testObject, testMethod);
		return transactionMode != DISABLED;
	}

	/**
	 * Disables all foreigh key and not-null constraints on the configured
	 * schema's.
	 */
	public void disableConstraints() {
		getConfiguredDatabaseTaskInstance(ConstraintsDisabler.class).disableConstraints();
	}

	/**
	 * @return A configured instance of {@link DatabaseAccessing} of the given
	 *         type
	 * 
	 * @param databaseTaskType
	 *            The type of database task, not null
	 */
	protected <T extends DatabaseAccessing> T getConfiguredDatabaseTaskInstance(Class<T> databaseTaskType) {
		return DatabaseModuleConfigUtils.getConfiguredDatabaseTaskInstance(databaseTaskType, configuration,
				getDefaultSqlHandler());
	}

	/**
	 * @return The default SQLHandler, which simply executes the sql statements
	 *         on the unitils-configured test database
	 */
	protected SQLHandler getDefaultSqlHandler() {
		DataSource dataSource = TransactionManager.getDataSource();
		return new DefaultSQLHandler(dataSource);
	}

	/**
	 * @return The {@link TestListener} associated with this module
	 */
	public TestListener getTestListener() {
		return new DatabaseTestListener();
	}

	protected static boolean hasExetedDisabled = false;

	/**
	 * The {@link TestListener} for this module
	 */
	protected class DatabaseTestListener extends TestListener {
		/**
		 * 是否需要去除数据库的外键约束和字段not null约束
		 */
		public void beforeTestClass(Class<?> testClass) {
			if (hasExetedDisabled == true) {
				return;
			}
			hasExetedDisabled = true;
			if (ConfigUtil.doesDisableConstraints() == false) {
				return;
			}
			DatabaseModuleHelper.disableConstraints();
		}

		@Override
		public void beforeTestSetUp(Object testObject, Method testMethod) {
			startTransactionForTestMethod(testObject, testMethod);
		}

		@Override
		public void afterTestTearDown(Object testObject, Method testMethod) {
			endTransactionForTestMethod(testObject, testMethod);
		}
	}
}

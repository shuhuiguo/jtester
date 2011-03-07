package org.jtester.module.database.transaction;

import java.util.Properties;

import javax.sql.DataSource;

import org.jtester.core.intf.ITransaction;
import org.jtester.module.database.DataSourceFactory;
import org.jtester.module.utils.ConfigUtil;
import org.jtester.module.utils.ConfigurationLoader;
import org.jtester.module.utils.PropertyUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.UnexpectedRollbackException;
import org.testng.log4testng.Logger;

/**
 * dataSource管理器
 * 
 * @author darui.wudr
 * 
 */
public class TransactionManager {

	private static final Logger log = Logger.getLogger(TransactionManager.class);
	/**
	 * Property indicating whether the datasource injected onto test fields
	 * annotated with @TestDataSource or retrieved using
	 * {@link #getTransactionalDataSourceAndActivateTransactionIfNeeded(Object)}
	 * must be wrapped in a transactional proxy
	 */
	public static final String PROPERTY_WRAP_DATASOURCE_IN_TRANSACTIONAL_PROXY = "dataSource.wrapInTransactionalProxy";

	private static TransactionManager _instance = null;

	private static Properties properties = null;

	private DataSource dataSource;

	/**
	 * Indicates whether the datasource injected onto test fields annotated with @TestDataSource
	 * or retrieved using
	 * {@link #getTransactionalDataSourceAndActivateTransactionIfNeeded} must be
	 * wrapped in a transactional proxy
	 */
	protected static boolean wrapDataSourceInTransactionalProxy = false;

	public static TransactionManager getInstance() {
		if (_instance == null) {
			ConfigurationLoader configurationLoader = new ConfigurationLoader();
			properties = configurationLoader.loadConfiguration();
			_instance = new TransactionManager();
			wrapDataSourceInTransactionalProxy = PropertyUtils.getBoolean(
					PROPERTY_WRAP_DATASOURCE_IN_TRANSACTIONAL_PROXY, properties);
		}
		return _instance;
	}

	private TransactionManager() {

	}

	public static boolean isDataSourceLoaded() {
		return getInstance().dataSource != null;
	}

	/**
	 * Returns the <code>DataSource</code> that provides connection to the unit
	 * test database. When invoked the first time, the DBMaintainer is invoked
	 * to make sure the test database is up-to-date (if database updating is
	 * enabled)
	 * 
	 * @return The <code>DataSource</code>
	 */
	public static DataSource getDataSource() {
		TransactionManager instance = getInstance();
		if (instance.dataSource == null) {
			instance.dataSource = createDataSource();
		}
		return instance.dataSource;
	}

	/**
	 * 带有事务处理的dataSource
	 * 
	 * @return
	 */
	public static DataSource getDataSourceAwareTransactional() {
		DataSource dataSource = getDataSource();
		if (wrapDataSourceInTransactionalProxy) {
			dataSource = new TransactionAwareDataSourceProxy(dataSource);
		}
		return dataSource;
	}

	public void activateTransactionIfNeeded(Object testedObject) {
		ITransaction transaction = (ITransaction) testedObject;
		boolean active = transaction.getTestedObjectTransaction().isActive();
		if (active == false) {
			PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(dataSource);
			transaction.getTestedObjectTransaction().setTransaction(platformTransactionManager);
		}
	}

	/**
	 * Starts a new transaction on the transaction manager configured in unitils
	 * 
	 * @param testObject
	 *            The test object, not null
	 */
	public static void startTransaction(Object testObject) {
		DataSource dataSource = TransactionManager.getDataSource();
		PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(dataSource);

		ITransaction transaction = (ITransaction) testObject;
		transaction.getTestedObjectTransaction().setTransaction(platformTransactionManager);
	}

	/**
	 * Commits the transaction. Uses the PlatformTransactionManager and
	 * transaction that is associated with the given test object.
	 * 
	 * @param testObject
	 *            The test object, not null
	 */
	public static void commitTransaction(Object testedObject) {
		ITransaction transaction = (ITransaction) testedObject;
		try {
			boolean active = transaction.getTestedObjectTransaction().isActive();
			if (active == false) {
				return;
			}
			log.debug("commit transaction");
			transaction.getTestedObjectTransaction().commit();
			transaction.getTestedObjectTransaction().removeTransaction();
		} catch (UnexpectedRollbackException e) {
			StringBuffer message = new StringBuffer();
			message.append("Catch a transaction exception: org.springframework.transaction.UnexpectedRollbackException.\n");
			message.append("\tplease use @Transactional(TransactionMode.DISABLED) on test method.\n");
			message.append("\tException:" + e.getMessage());
			log.warn(message.toString());
		}
	}

	/**
	 * Rolls back the transaction. Uses the PlatformTransactionManager and
	 * transaction that is associated with the given test object.
	 * 
	 * @param testObject
	 *            The test object, not null
	 */
	public static void rollbackTransaction(Object testedObject) {
		ITransaction transaction = (ITransaction) testedObject;
		boolean active = transaction.getTestedObjectTransaction().isActive();
		if (active == false) {
			return;
		}
		log.debug("Rolling back transaction");
		transaction.getTestedObjectTransaction().rollback();
		transaction.getTestedObjectTransaction().removeTransaction();
	}

	/**
	 * Creates a datasource by using the factory that is defined by the
	 * dataSourceFactory.className property
	 * 
	 * @return the datasource
	 */
	private static DataSource createDataSource() {
		// Get the factory for the data source and create it
		DataSourceFactory dataSourceFactory = ConfigUtil.getConfiguredInstanceOf(DataSourceFactory.class, properties);
		dataSourceFactory.init(properties);
		DataSource dataSource = dataSourceFactory.createDataSource();

		return dataSource;
	}
}

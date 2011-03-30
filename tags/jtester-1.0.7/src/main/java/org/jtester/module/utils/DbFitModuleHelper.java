package org.jtester.module.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.jtester.module.ModulesRepository;
import org.jtester.module.core.SpringModule;

public class DbFitModuleHelper {
	private final static Map<Long, Boolean> isSpringTransactionMap = new ConcurrentHashMap<Long, Boolean>();

	private final static Map<Long, Boolean> isTransactionDisabledMap = new ConcurrentHashMap<Long, Boolean>();

	public static Connection getConnection(DataSource dataSource) throws SQLException {
		ModulesRepository modulesRepository = ModuleHelper.getInstance().getModulesRepository();
		Connection connection = null;
		if (modulesRepository.isModuleEnabled(SpringModule.class)) {
			connection = SpringModuleHelper.getConnection(dataSource);
			if (connection != null) {
				setSpringTransaction(true);
			}
		}
		if (connection == null) {
			connection = dataSource.getConnection();
		}
		return connection;
	}

	private static void setSpringTransaction(boolean isSpringTransaction) {
		long currThread = Thread.currentThread().getId();
		isSpringTransactionMap.put(currThread, isSpringTransaction);
	}

	public static boolean isSpringTransaction() {
		long currThread = Thread.currentThread().getId();
		Boolean isSpringTransaction = isSpringTransactionMap.get(currThread);
		return isSpringTransaction == null ? false : isSpringTransaction;
	}

	/**
	 * 当前测试方法是否加了@Transactonal(TransactionMode.DISABLED)
	 * 
	 * @param isDisabled
	 */
	public static void setTransactionDisabled(boolean isDisabled) {
		long currThread = Thread.currentThread().getId();
		isTransactionDisabledMap.put(currThread, isDisabled);
	}

	public static boolean isTransactionDisabled() {
		long currThread = Thread.currentThread().getId();
		Boolean isTransactionDisabled = isTransactionDisabledMap.get(currThread);
		return isTransactionDisabled == null ? true : isTransactionDisabled;
	}
}

package org.jtester.module.dbfit.db.enviroment;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.jtester.module.database.transaction.TransactionManager;
import org.jtester.module.dbfit.DbFactory;
import org.jtester.module.dbfit.db.model.BigDecimalParseDelegate;
import org.jtester.module.dbfit.db.model.DbParameterAccessor;
import org.jtester.module.dbfit.db.model.SqlDateParseDelegate;
import org.jtester.module.dbfit.db.model.SqlTimestampParseDelegate;
import org.jtester.module.utils.DbFitModuleHelper;

import fit.TypeAdapter;

public abstract class AbstractDbEnvironment implements DBEnvironment {

	protected Connection currentConnection;

	/**
	 * 连接在wiki文件结束时是否自动commit
	 */
	private static ThreadLocal<Boolean> teardown_commit = new ThreadLocal<Boolean>();

	protected final String getDriverClassName() {
		return DbFactory.currDriver();
	}

	private boolean driverRegistered = false;

	public AbstractDbEnvironment() {
		TypeAdapter.registerParseDelegate(BigDecimal.class, BigDecimalParseDelegate.class);
		TypeAdapter.registerParseDelegate(java.sql.Date.class, SqlDateParseDelegate.class);
		TypeAdapter.registerParseDelegate(java.sql.Timestamp.class, SqlTimestampParseDelegate.class);
	}

	private void registerDriver() {
		String driverName = getDriverClassName();
		try {
			if (driverRegistered)
				return;
			DriverManager.registerDriver((Driver) Class.forName(driverName).newInstance());
			driverRegistered = true;
		} catch (Exception e) {
			throw new RuntimeException("Cannot register SQL driver " + driverName);
		}
	}

	private void registerDriver(String driver) {
		try {
			DriverManager.registerDriver((Driver) Class.forName(driver).newInstance());
		} catch (Exception e) {
			throw new Error("Cannot register SQL driver " + driver);
		}
	}

	public final void connect() throws SQLException {
		registerDriver();
		DataSource dataSource = TransactionManager.getDataSourceAwareTransactional();

		currentConnection = DbFitModuleHelper.getConnection(dataSource);
		currentConnection.setAutoCommit(false);
		teardown_commit.set(false);
	}

	public final void connect(String url, String username, String password, String driver) throws SQLException {
		registerDriver(driver);
		currentConnection = DriverManager.getConnection(url, username, password);
		// 显式参数的连接设置在teardown是自动提交(因为没有全局事务)
		currentConnection.setAutoCommit(false);
		teardown_commit.set(true);
	}

	public final void teardown() throws SQLException {
		boolean isCommit = teardown_commit.get();
		if (isCommit) {
			commit();
		}
	}

	/**
	 * any processing required to turn a string into something jdbc driver can
	 * process, can be used to clean up CRLF, externalise parameters if required
	 * etc.
	 */
	protected String parseCommandText(String commandText) {
		commandText = commandText.replace("\n", " ");
		commandText = commandText.replace("\r", " ");
		return commandText;
	}

	/*
	 * from .Net, not needed since JDBC has a better interface protected static
	 * void AddInput(CallableStatement dbCommand, String name, Object value) can
	 * be directly invoked using dbCommand.setObject(parameterName, x,
	 * targetSqlType)
	 */
	public final PreparedStatement createStatementWithBoundFixtureSymbols(String commandText) throws SQLException {
		if (Options.isBindSymbols()) {
			PreparedStatement cs = currentConnection.prepareStatement(parseCommandText(commandText));
			String paramNames[] = extractParamNames(commandText);
			for (int i = 0; i < paramNames.length; i++) {
				Object value = org.jtester.fit.util.SymbolUtil.getSymbol(paramNames[i]);
				cs.setObject(i + 1, value);
			}
			return cs;
		} else {
			// no parsing, return directly what is there and execute as native
			// code
			PreparedStatement cs = currentConnection.prepareStatement(commandText);
			return cs;
		}
	}

	public void closeConnection() throws SQLException {
		if (currentConnection != null && currentConnection.isClosed() == false) {
			// currentConnection.rollback();
			currentConnection.close();
		}
	}

	public void commit() throws SQLException {
		if (currentConnection != null && currentConnection.isClosed() == false) {
			currentConnection.commit();
			currentConnection.setAutoCommit(false);
		}
	}

	public void rollback() throws SQLException {
		// checkConnectionValid(currentConnection);
		if (currentConnection != null && currentConnection.isClosed() == false) {
			currentConnection.rollback();
		}
	}

	public Connection getConnection() {
		return currentConnection;
	}

	public int getExceptionCode(SQLException dbException) {
		return dbException.getErrorCode();
	}

	/**
	 * MUST RETURN PARAMETER NAMES IN EXACT ORDER AS IN STATEMENT. IF SINGLE
	 * PARAMETER APPEARS MULTIPLE TIMES, MUST BE LISTED MULTIPLE TIMES IN THE
	 * ARRAY ALSO
	 */
	public String[] extractParamNames(String commandText) {
		ArrayList<String> hs = new ArrayList<String>();
		Matcher mc = getParameterPattern().matcher(commandText);
		while (mc.find()) {
			hs.add(mc.group(1));
		}
		String[] array = new String[hs.size()];
		return hs.toArray(array);
	}

	protected abstract Pattern getParameterPattern();

	/**
	 * by default, this will support retrieving a single autogenerated key via
	 * JDBC. DB environments which support automated column retrieval after
	 * insert, like oracle, should override this and put in parameters for OUT
	 * accessors
	 */
	public String buildInsertCommand(String tableName, DbParameterAccessor[] accessors) {
		/*
		 * currently only supports retrieving the primary key column
		 * 
		 * maybe change later to implement:
		 * 
		 * http://dev.mysql.com/doc/refman/5.0/en/comparison-operators.html
		 * 
		 * You can find the row that contains the most recent AUTO_INCREMENT
		 * value by issuing a statement of the following form immediately after
		 * generating the value: SELECT * FROM tbl_name WHERE auto_col IS NULL
		 * This behavior can be disabled by setting SQL_AUTO_IS_NULL=0. See
		 * Section 13.5.3, “SET Syntax.
		 */
		StringBuilder sb = new StringBuilder("insert into ");
		sb.append(tableName).append("(");
		String comma = "";

		StringBuilder values = new StringBuilder();

		for (DbParameterAccessor accessor : accessors) {
			if (accessor.getDirection() == DbParameterAccessor.INPUT) {
				sb.append(comma);
				values.append(comma);
				sb.append(accessor.getName());
				// values.append(":").append(accessor.getName());
				values.append("?");
				comma = ",";
			}
		}
		sb.append(") values (");
		sb.append(values);
		sb.append(")");
		return sb.toString();
	}

	public String buildDeleteCommand(String tableName, DbParameterAccessor[] accessors) {
		StringBuilder sb = new StringBuilder("delete from " + tableName + " where ");
		String comma = "";
		for (DbParameterAccessor accessor : accessors) {
			if (accessor.getDirection() == DbParameterAccessor.INPUT) {
				sb.append(comma);
				sb.append(accessor.getName());
				sb.append("=?");
				comma = ",";
			}
		}
		return sb.toString();
	}

	/**
	 * by default, this is set to false.
	 * 
	 * @see org.jtester.module.dbfit.db.enviroment.DBEnvironment#supportsOuputOnInsert()
	 */
	public boolean supportsOuputOnInsert() {
		return false;
	}

	/** Check the validity of the supplied connection. */
	public static void checkConnectionValid(final Connection conn) throws SQLException {
		if (conn == null || conn.isClosed()) {
			throw new IllegalArgumentException("No open connection to a database is available. "
					+ "Make sure your database is running and that you have connected before performing any queries.");
		}
	}

}
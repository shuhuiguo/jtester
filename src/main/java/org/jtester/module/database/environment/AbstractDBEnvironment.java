package org.jtester.module.database.environment;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.jtester.bytecode.reflector.helper.ClazzConst;
import org.jtester.bytecode.reflector.helper.ClazzHelper;
import org.jtester.core.TestContext;
import org.jtester.core.context.DbFitContext;
import org.jtester.core.context.DbFitContext.RunIn;
import org.jtester.fit.util.SymbolUtil;
import org.jtester.module.database.DataSourceType;
import org.jtester.module.database.JTesterDataSource;
import org.jtester.module.dbfit.db.model.BigDecimalParseDelegate;
import org.jtester.module.dbfit.db.model.DbParameterAccessor;
import org.jtester.module.dbfit.db.model.SqlDateParseDelegate;
import org.jtester.module.dbfit.db.model.SqlTimestampParseDelegate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import fit.TypeAdapter;

public abstract class AbstractDBEnvironment implements DBEnvironment {

	protected Connection connection;

	protected final String dataSourceName;

	protected final String dataSourceFrom;

	protected DataSourceType dataSourceType;

	private JTesterDataSource jtesterDataSource;

	private DataSource dataSourceProxy = null;

	/**
	 * 是否使用spring来管理事务
	 */
	private boolean hasTransaction;

	protected AbstractDBEnvironment(DataSourceType dataSourceType, String dataSourceName, String dataSourceFrom) {
		TypeAdapter.registerParseDelegate(BigDecimal.class, BigDecimalParseDelegate.class);
		TypeAdapter.registerParseDelegate(java.sql.Date.class, SqlDateParseDelegate.class);
		TypeAdapter.registerParseDelegate(java.sql.Timestamp.class, SqlTimestampParseDelegate.class);

		this.dataSourceName = dataSourceName;
		this.dataSourceFrom = dataSourceFrom;
		this.dataSourceType = dataSourceType;
	}

	public void setDataSource(String driver, String url, String schemas, String username, String password) {
		this.jtesterDataSource = new JTesterDataSource(dataSourceType, driver, url, schemas, username, password);
	}

	public DataSource getDataSource(boolean withTransactionManager) {
		this.dataSourceProxy = this.jtesterDataSource;
		if (withTransactionManager) {
			this.dataSourceProxy = new TransactionAwareDataSourceProxy(jtesterDataSource);
		}
		return this.dataSourceProxy;
	}

	public void close() {
		try {
			if (connection != null && connection.isClosed() == false) {
				commit();
				DataSourceUtils.doReleaseConnection(connection, this.dataSourceProxy);
				connection = null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(String.format("close datasource[%s] connection error.",
					this.jtesterDataSource.toString()), e);
		}
	}

	/**
	 * 连接当前数据源
	 * 
	 * @return
	 */
	public Connection connect() throws SQLException {
		this.hasTransaction = false;
		RunIn runIn = DbFitContext.getRunIn();
		if (runIn == RunIn.TestCase) {
			boolean springAvailable = ClazzHelper.isClassAvailable(ClazzConst.Spring_DataSourceUtils);
			hasTransaction = springAvailable && TestContext.isTransactionsEnabled();
		}
		this.dataSourceProxy = getDataSource(hasTransaction);

		connection = DataSourceUtils.doGetConnection(this.dataSourceProxy);
		if (connection == null || connection.isClosed()) {
			System.out.println();
		}

		connection.setAutoCommit(false);
		return connection;
	}

	public final void teardown() throws SQLException {
		boolean isDefaultDbEnv = isDefaultDBEnvironment();

		if (!(isDefaultDbEnv && this.hasTransaction && connection != null)) {
			commit();

			DataSourceUtils.doReleaseConnection(connection, this.dataSourceProxy);
			connection = null;
		}
	}

	/**
	 * 是否是默认的数据源
	 * 
	 * @return
	 */
	private boolean isDefaultDBEnvironment() {
		boolean isDefault = DEFAULT_DATASOURCE_NAME.equals(this.dataSourceName)
				&& DEFAULT_DATASOURCE_FROM.equals(dataSourceFrom);
		return isDefault;
	}

	/**
	 * any processing required to turn a string into something jdbc driver can
	 * process, can be used to clean up CRLF, externalise parameters if required
	 * etc.
	 */
	protected String parseCommandText(String commandText) {
		return commandText;
	}

	public final PreparedStatement createStatementWithBoundFixtureSymbols(String commandText) throws SQLException {
		String text = SymbolUtil.replacedBySymbols(commandText);
		String paramNames[] = extractParamNames(text);

		String sql = parseCommandText(text);
		PreparedStatement cs = connection.prepareStatement(sql);
		for (int i = 0; i < paramNames.length; i++) {
			Object value = org.jtester.fit.util.SymbolUtil.getSymbol(paramNames[i]);
			cs.setObject(i + 1, value);
		}
		return cs;
	}

	public void commit() throws SQLException {
		if (connection != null && connection.isClosed() == false) {
			connection.commit();
			connection.setAutoCommit(false);
		}
	}

	public void rollback() throws SQLException {
		if (connection != null && connection.isClosed() == false) {
			connection.rollback();
		}
	}

	public Connection getConnection() {
		return connection;
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
	 * @see org.jtester.module.database.environment.DBEnvironment#supportsOuputOnInsert()
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
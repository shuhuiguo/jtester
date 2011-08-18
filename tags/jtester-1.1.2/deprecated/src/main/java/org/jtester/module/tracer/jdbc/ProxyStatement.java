//package org.jtester.module.tracer.jdbc;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.SQLWarning;
//import java.sql.Statement;
//
//import org.jtester.module.tracer.TracerManager;
//
///**
// * Statement代理类:SQL执行跟踪
// * 
// */
//public class ProxyStatement implements Statement {
//	protected ProxyConnection connectionProxy;
//	protected Statement statement = null;
//	protected boolean hasClosed = false;
//
//	public ProxyStatement(ProxyConnection conn, Statement stmt) {
//		this.statement = stmt;
//		this.connectionProxy = conn;
//	}
//
//	/**
//	 * 执行statement proxy的executeQuery api，且记录活动轨迹<br>
//	 * <br> {@inheritDoc}
//	 */
//	public ResultSet executeQuery(String sql) throws SQLException {
//		checkOpen();
//
//		TracerManager.addJdbcTracerEvent(sql);
//		ResultSet rs = statement.executeQuery(sql);
//
//		return rs;
//	}
//
//	/**
//	 * 执行statement proxy的execute api，且记录活动轨迹<br>
//	 * <br> {@inheritDoc}
//	 */
//	public boolean execute(String sql) throws SQLException {
//		checkOpen();
//		TracerManager.addJdbcTracerEvent(sql);
//		boolean tag = statement.execute(sql);
//
//		return tag;
//	}
//
//	/**
//	 * 执行statement proxy的executeUpdate api，且记录活动轨迹<br>
//	 * <br> {@inheritDoc}
//	 */
//	public int executeUpdate(String sql) throws SQLException {
//		checkOpen();
//		TracerManager.addJdbcTracerEvent(sql);
//		int tag = statement.executeUpdate(sql);
//		return tag;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void close() throws SQLException {
//		try {
//			if (statement != null) {
//				statement.close();
//			}
//		} finally {
//			statement = null;
//			connectionProxy = null;
//			hasClosed = true;
//		}
//	}
//
//	protected void checkOpen() throws SQLException {
//		if (this.hasClosed || statement == null) {
//			throw new SQLException(this.getClass().getName() + " is closed.");
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public ResultSet getResultSet() throws SQLException {
//		checkOpen();
//		return statement.getResultSet();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public int getMaxFieldSize() throws SQLException {
//		checkOpen();
//		return statement.getMaxFieldSize();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void setMaxFieldSize(int max) throws SQLException {
//		checkOpen();
//		statement.setMaxFieldSize(max);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public int getMaxRows() throws SQLException {
//		checkOpen();
//		return statement.getMaxRows();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void setMaxRows(int max) throws SQLException {
//		checkOpen();
//		statement.setMaxRows(max);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void setEscapeProcessing(boolean enable) throws SQLException {
//		checkOpen();
//		statement.setEscapeProcessing(enable);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public int getQueryTimeout() throws SQLException {
//		checkOpen();
//		return statement.getQueryTimeout();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void setQueryTimeout(int seconds) throws SQLException {
//		checkOpen();
//		statement.setQueryTimeout(seconds);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void cancel() throws SQLException {
//		checkOpen();
//		statement.cancel();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public SQLWarning getWarnings() throws SQLException {
//		checkOpen();
//		return statement.getWarnings();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void clearWarnings() throws SQLException {
//		checkOpen();
//		statement.clearWarnings();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void setCursorName(String name) throws SQLException {
//		checkOpen();
//		statement.setCursorName(name);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public int getUpdateCount() throws SQLException {
//		checkOpen();
//		return statement.getUpdateCount();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public boolean getMoreResults() throws SQLException {
//		checkOpen();
//		return statement.getMoreResults();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void setFetchDirection(int direction) throws SQLException {
//		checkOpen();
//		statement.setFetchDirection(direction);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public int getFetchDirection() throws SQLException {
//		checkOpen();
//		return statement.getFetchDirection();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void setFetchSize(int rows) throws SQLException {
//		checkOpen();
//		statement.setFetchSize(rows);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public int getFetchSize() throws SQLException {
//		checkOpen();
//		return statement.getFetchSize();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public int getResultSetConcurrency() throws SQLException {
//		checkOpen();
//		return statement.getResultSetConcurrency();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public int getResultSetType() throws SQLException {
//		checkOpen();
//		return statement.getResultSetType();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void addBatch(String sql) throws SQLException {
//		checkOpen();
//		statement.addBatch(sql);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void clearBatch() throws SQLException {
//		checkOpen();
//		statement.clearBatch();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public int[] executeBatch() throws SQLException {
//		checkOpen();
//		return statement.executeBatch();
//	}
//
//	// ------------------- JDBC 3.0 -----------------------------------------
//	/**
//	 * {@inheritDoc}
//	 */
//	public boolean getMoreResults(int current) throws SQLException {
//		checkOpen();
//		return statement.getMoreResults(current);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public ResultSet getGeneratedKeys() throws SQLException {
//		checkOpen();
//		return statement.getGeneratedKeys();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public int executeUpdate(String sql, int autoGeneratedKeys)
//			throws SQLException {
//		checkOpen();
//		return statement.executeUpdate(sql, autoGeneratedKeys);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public int executeUpdate(String sql, int columnIndexes[])
//			throws SQLException {
//		checkOpen();
//		return statement.executeUpdate(sql, columnIndexes);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public int executeUpdate(String sql, String columnNames[])
//			throws SQLException {
//		checkOpen();
//		return statement.executeUpdate(sql, columnNames);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public boolean execute(String sql, int autoGeneratedKeys)
//			throws SQLException {
//		checkOpen();
//		return statement.execute(sql, autoGeneratedKeys);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public boolean execute(String sql, int columnIndexes[]) throws SQLException {
//		checkOpen();
//		return statement.execute(sql, columnIndexes);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public boolean execute(String sql, String columnNames[])
//			throws SQLException {
//		checkOpen();
//		return statement.execute(sql, columnNames);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public int getResultSetHoldability() throws SQLException {
//		checkOpen();
//		return statement.getResultSetHoldability();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public Connection getConnection() throws SQLException {
//		checkOpen();
//		return connectionProxy;
//	}
//}

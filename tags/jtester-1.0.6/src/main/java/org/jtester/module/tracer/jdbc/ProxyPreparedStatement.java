package org.jtester.module.tracer.jdbc;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;

import org.jtester.module.tracer.TracerManager;

/**
 * PreparedStatement代理类:跟踪SQL执行轨迹
 */
public class ProxyPreparedStatement extends ProxyStatement implements PreparedStatement {

	protected PreparedStatement preparedStatementProxy = null;

	private String sql = null;

	public ProxyPreparedStatement(ProxyConnection conn, PreparedStatement stmt) {
		super(conn, stmt);
		preparedStatementProxy = stmt;
	}

	public ProxyPreparedStatement(ProxyConnection conn, PreparedStatement stmt, String sql) {
		this(conn, stmt);
		this.sql = sql;
		TracerManager.addJdbcTracerEvent(this.sql);
	}

	/**
	 * 执行proxy的executeQuery api，同时设置所有子查询事件的结束时间<br>
	 * <br> {@inheritDoc}
	 */
	public ResultSet executeQuery() throws SQLException {
		checkOpen();
		return preparedStatementProxy.executeQuery();
	}

	/**
	 * 执行proxy的addBatch api，同时 增加一个子查询跟踪器<br>
	 * <br> {@inheritDoc}
	 */
	public void addBatch() throws SQLException {
		checkOpen();
		preparedStatementProxy.addBatch();
	}

	/**
	 * 执行proxy的executeUpdate api，同时设置所有子查询事件的结束时间<br>
	 * <br> {@inheritDoc}
	 */
	public int executeUpdate() throws SQLException {
		checkOpen();
		return preparedStatementProxy.executeUpdate();
	}

	/**
	 * 执行proxy的execute api，同时设置所有子查询事件的结束时间<br>
	 * <br> {@inheritDoc}
	 */
	public boolean execute() throws SQLException {
		checkOpen();
		return preparedStatementProxy.execute();
	}

	/**
	 * {@inheritDoc}
	 */
	public void close() throws SQLException {
		super.close();
		this.preparedStatementProxy = null;

	}

	/**
	 * {@inheritDoc}
	 */
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		checkOpen();
		preparedStatementProxy.setNull(parameterIndex, sqlType);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setBoolean(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setByte(int parameterIndex, byte x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setByte(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setShort(int parameterIndex, short x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setShort(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setInt(int parameterIndex, int x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setInt(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLong(int parameterIndex, long x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setLong(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFloat(int parameterIndex, float x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setFloat(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDouble(int parameterIndex, double x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setDouble(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setBigDecimal(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setString(int parameterIndex, String x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setString(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setBytes(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDate(int parameterIndex, java.sql.Date x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setDate(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTime(int parameterIndex, java.sql.Time x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setTime(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTimestamp(int parameterIndex, java.sql.Timestamp x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setTimestamp(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAsciiStream(int parameterIndex, java.io.InputStream x, int length) throws SQLException {
		checkOpen();
		preparedStatementProxy.setAsciiStream(parameterIndex, x, length);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated
	 */
	public void setUnicodeStream(int parameterIndex, java.io.InputStream x, int length) throws SQLException {
		checkOpen();
		preparedStatementProxy.setUnicodeStream(parameterIndex, x, length);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setBinaryStream(int parameterIndex, java.io.InputStream x, int length) throws SQLException {
		checkOpen();
		preparedStatementProxy.setBinaryStream(parameterIndex, x, length);
	}

	/**
	 * {@inheritDoc}
	 */
	public void clearParameters() throws SQLException {
		checkOpen();
		preparedStatementProxy.clearParameters();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
		checkOpen();
		preparedStatementProxy.setObject(parameterIndex, x, targetSqlType, scale);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		checkOpen();
		preparedStatementProxy.setObject(parameterIndex, x, targetSqlType);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setObject(int parameterIndex, Object x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setObject(parameterIndex, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCharacterStream(int parameterIndex, java.io.Reader reader, int length) throws SQLException {
		checkOpen();
		preparedStatementProxy.setCharacterStream(parameterIndex, reader, length);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRef(int i, Ref x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setRef(i, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setBlob(int i, Blob x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setBlob(i, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setClob(int i, Clob x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setClob(i, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setArray(int i, Array x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setArray(i, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public ResultSetMetaData getMetaData() throws SQLException {
		checkOpen();
		return preparedStatementProxy.getMetaData();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDate(int parameterIndex, java.sql.Date x, Calendar cal) throws SQLException {
		checkOpen();
		preparedStatementProxy.setDate(parameterIndex, x, cal);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTime(int parameterIndex, java.sql.Time x, Calendar cal) throws SQLException {
		checkOpen();
		preparedStatementProxy.setTime(parameterIndex, x, cal);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTimestamp(int parameterIndex, java.sql.Timestamp x, Calendar cal) throws SQLException {
		checkOpen();
		preparedStatementProxy.setTimestamp(parameterIndex, x, cal);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
		checkOpen();
		preparedStatementProxy.setNull(paramIndex, sqlType, typeName);
	}

	// ------------------- JDBC 3.0 -----------------------------------------
	/**
	 * {@inheritDoc}
	 */
	public void setURL(int parameterIndex, java.net.URL x) throws SQLException {
		checkOpen();
		preparedStatementProxy.setURL(parameterIndex, x);
	}

	public java.sql.ParameterMetaData getParameterMetaData() throws SQLException {
		checkOpen();
		return preparedStatementProxy.getParameterMetaData();
	}
}

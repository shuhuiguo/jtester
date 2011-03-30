package org.jtester.module.tracer.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.jtester.module.tracer.TracerManager;

/**
 * CallableStatement代理<br>
 * 本类只是继承ProxyPreparedStatement做一下代理转发，没有干额外的工作
 */
public class ProxyCallableStatement extends ProxyPreparedStatement implements CallableStatement {

	protected CallableStatement callableStatementProxy = null;

	public ProxyCallableStatement(ProxyConnection conn, CallableStatement stmt) {
		super(conn, stmt);
		callableStatementProxy = stmt;
	}

	public ProxyCallableStatement(ProxyConnection conn, CallableStatement stmt, String sql) {
		super(conn, stmt);
		callableStatementProxy = stmt;
		TracerManager.addJdbcTracerEvent(sql);
	}

	/**
	 * {@inheritDoc}
	 */
	public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
		checkOpen();
		callableStatementProxy.registerOutParameter(parameterIndex, sqlType);
	}

	/**
	 * {@inheritDoc}
	 */
	public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
		checkOpen();
		callableStatementProxy.registerOutParameter(parameterIndex, sqlType, scale);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean wasNull() throws SQLException {
		checkOpen();
		return callableStatementProxy.wasNull();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getString(int parameterIndex) throws SQLException {
		checkOpen();
		return callableStatementProxy.getString(parameterIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean getBoolean(int parameterIndex) throws SQLException {
		checkOpen();
		return callableStatementProxy.getBoolean(parameterIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	public byte getByte(int parameterIndex) throws SQLException {
		checkOpen();
		return callableStatementProxy.getByte(parameterIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	public short getShort(int parameterIndex) throws SQLException {
		checkOpen();
		return callableStatementProxy.getShort(parameterIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	public int getInt(int parameterIndex) throws SQLException {
		checkOpen();
		return callableStatementProxy.getInt(parameterIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	public long getLong(int parameterIndex) throws SQLException {
		checkOpen();
		return callableStatementProxy.getLong(parameterIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	public float getFloat(int parameterIndex) throws SQLException {
		checkOpen();
		return callableStatementProxy.getFloat(parameterIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	public double getDouble(int parameterIndex) throws SQLException {
		checkOpen();
		return callableStatementProxy.getDouble(parameterIndex);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated
	 */
	public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
		checkOpen();
		return callableStatementProxy.getBigDecimal(parameterIndex, scale);
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] getBytes(int parameterIndex) throws SQLException {
		checkOpen();
		return callableStatementProxy.getBytes(parameterIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getDate(int parameterIndex) throws SQLException {
		checkOpen();
		return callableStatementProxy.getDate(parameterIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	public Time getTime(int parameterIndex) throws SQLException {
		checkOpen();
		return callableStatementProxy.getTime(parameterIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	public Timestamp getTimestamp(int parameterIndex) throws SQLException {
		checkOpen();
		return callableStatementProxy.getTimestamp(parameterIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getObject(int parameterIndex) throws SQLException {
		checkOpen();
		return callableStatementProxy.getObject(parameterIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
		checkOpen();
		return callableStatementProxy.getBigDecimal(parameterIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
		checkOpen();
		return callableStatementProxy.getObject(i, map);
	}

	/**
	 * {@inheritDoc}
	 */
	public Ref getRef(int i) throws SQLException {
		checkOpen();
		return callableStatementProxy.getRef(i);
	}

	/**
	 * {@inheritDoc}
	 */
	public Blob getBlob(int i) throws SQLException {
		checkOpen();
		return callableStatementProxy.getBlob(i);
	}

	/**
	 * {@inheritDoc}
	 */
	public Clob getClob(int i) throws SQLException {
		checkOpen();
		return callableStatementProxy.getClob(i);
	}

	/**
	 * {@inheritDoc}
	 */
	public Array getArray(int i) throws SQLException {
		checkOpen();
		return callableStatementProxy.getArray(i);
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
		checkOpen();
		return callableStatementProxy.getDate(parameterIndex, cal);
	}

	/**
	 * {@inheritDoc}
	 */
	public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
		checkOpen();
		return callableStatementProxy.getTime(parameterIndex, cal);
	}

	/**
	 * {@inheritDoc}
	 */
	public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
		checkOpen();
		return callableStatementProxy.getTimestamp(parameterIndex, cal);
	}

	/**
	 * {@inheritDoc}
	 */
	public void registerOutParameter(int paramIndex, int sqlType, String typeName) throws SQLException {
		checkOpen();
		callableStatementProxy.registerOutParameter(paramIndex, sqlType, typeName);
	}

	// ------------------- JDBC 3.0 -----------------------------------------
	/**
	 * {@inheritDoc}
	 */
	public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
		checkOpen();
		callableStatementProxy.registerOutParameter(parameterName, sqlType);
	}

	/**
	 * {@inheritDoc}
	 */
	public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
		checkOpen();
		callableStatementProxy.registerOutParameter(parameterName, sqlType, scale);
	}

	/**
	 * {@inheritDoc}
	 */
	public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
		checkOpen();
		callableStatementProxy.registerOutParameter(parameterName, sqlType, typeName);
	}

	/**
	 * {@inheritDoc}
	 */
	public URL getURL(int parameterIndex) throws SQLException {
		checkOpen();
		return callableStatementProxy.getURL(parameterIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setURL(String parameterName, URL val) throws SQLException {
		checkOpen();
		callableStatementProxy.setURL(parameterName, val);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setNull(String parameterName, int sqlType) throws SQLException {
		checkOpen();
		callableStatementProxy.setNull(parameterName, sqlType);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setBoolean(String parameterName, boolean x) throws SQLException {
		checkOpen();
		callableStatementProxy.setBoolean(parameterName, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setByte(String parameterName, byte x) throws SQLException {
		checkOpen();
		callableStatementProxy.setByte(parameterName, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setShort(String parameterName, short x) throws SQLException {
		checkOpen();
		callableStatementProxy.setShort(parameterName, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setInt(String parameterName, int x) throws SQLException {
		checkOpen();
		callableStatementProxy.setInt(parameterName, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLong(String parameterName, long x) throws SQLException {
		checkOpen();
		callableStatementProxy.setLong(parameterName, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFloat(String parameterName, float x) throws SQLException {
		checkOpen();
		callableStatementProxy.setFloat(parameterName, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDouble(String parameterName, double x) throws SQLException {
		checkOpen();
		callableStatementProxy.setDouble(parameterName, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
		checkOpen();
		callableStatementProxy.setBigDecimal(parameterName, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setString(String parameterName, String x) throws SQLException {
		checkOpen();
		callableStatementProxy.setString(parameterName, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setBytes(String parameterName, byte[] x) throws SQLException {
		checkOpen();
		callableStatementProxy.setBytes(parameterName, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDate(String parameterName, Date x) throws SQLException {
		checkOpen();
		callableStatementProxy.setDate(parameterName, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTime(String parameterName, Time x) throws SQLException {
		checkOpen();
		callableStatementProxy.setTime(parameterName, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
		checkOpen();
		callableStatementProxy.setTimestamp(parameterName, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
		checkOpen();
		callableStatementProxy.setAsciiStream(parameterName, x, length);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
		checkOpen();
		callableStatementProxy.setBinaryStream(parameterName, x, length);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
		checkOpen();
		callableStatementProxy.setObject(parameterName, x, targetSqlType, scale);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
		checkOpen();
		callableStatementProxy.setObject(parameterName, x, targetSqlType);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setObject(String parameterName, Object x) throws SQLException {
		checkOpen();
		callableStatementProxy.setObject(parameterName, x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
		checkOpen();
		callableStatementProxy.setCharacterStream(parameterName, reader, length);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
		checkOpen();
		callableStatementProxy.setDate(parameterName, x, cal);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
		checkOpen();
		callableStatementProxy.setTime(parameterName, x, cal);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
		checkOpen();
		callableStatementProxy.setTimestamp(parameterName, x, cal);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
		checkOpen();
		callableStatementProxy.setNull(parameterName, sqlType, typeName);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getString(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getString(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean getBoolean(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getBoolean(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public byte getByte(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getByte(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public short getShort(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getShort(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public int getInt(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getInt(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public long getLong(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getLong(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public float getFloat(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getFloat(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public double getDouble(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getDouble(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] getBytes(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getBytes(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getDate(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getDate(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public Time getTime(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getTime(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public Timestamp getTimestamp(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getTimestamp(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getObject(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getObject(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public BigDecimal getBigDecimal(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getBigDecimal(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
		checkOpen();
		return callableStatementProxy.getObject(parameterName, map);
	}

	/**
	 * {@inheritDoc}
	 */
	public Ref getRef(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getRef(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public Blob getBlob(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getBlob(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public Clob getClob(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getClob(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public Array getArray(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getArray(parameterName);
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getDate(String parameterName, Calendar cal) throws SQLException {
		checkOpen();
		return callableStatementProxy.getDate(parameterName, cal);
	}

	/**
	 * {@inheritDoc}
	 */
	public Time getTime(String parameterName, Calendar cal) throws SQLException {
		checkOpen();
		return callableStatementProxy.getTime(parameterName, cal);
	}

	/**
	 * {@inheritDoc}
	 */
	public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
		checkOpen();
		return callableStatementProxy.getTimestamp(parameterName, cal);
	}

	/**
	 * {@inheritDoc}
	 */
	public URL getURL(String parameterName) throws SQLException {
		checkOpen();
		return callableStatementProxy.getURL(parameterName);
	}
}

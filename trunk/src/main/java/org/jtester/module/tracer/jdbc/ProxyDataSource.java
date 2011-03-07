package org.jtester.module.tracer.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * DataSource代理
 */
public class ProxyDataSource implements DataSource {
	private DataSource dataSource;

	public ProxyDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * {@inheritDoc}
	 */
	public Connection getConnection() throws SQLException {
		Connection conn = dataSource.getConnection();
		return getProxy(conn);
	}

	/**
	 * {@inheritDoc}
	 */
	public Connection getConnection(String arg0, String arg1) throws SQLException {
		Connection conn = dataSource.getConnection(arg0, arg1);
		return getProxy(conn);
	}

	/**
	 * {@inheritDoc}
	 */
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	/**
	 * {@inheritDoc}
	 */
	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		dataSource.setLogWriter(arg0);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLoginTimeout(int arg0) throws SQLException {
		dataSource.setLoginTimeout(arg0);
	}

	/**
	 * 得到连接代理
	 */
	private Connection getProxy(Connection conn) {
		if (conn instanceof ProxyConnection) {
			return conn;
		} else {
			return new ProxyConnection(conn);
		}
	}
}

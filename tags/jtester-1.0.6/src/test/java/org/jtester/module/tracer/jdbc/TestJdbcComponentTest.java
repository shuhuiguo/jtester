package org.jtester.module.tracer.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.jtester.core.IJTester;
import org.jtester.module.tracer.jdbc.JdbcTracerManager;
import org.jtester.module.tracer.jdbc.ProxyConnection;
import org.jtester.module.tracer.jdbc.ProxyDataSource;
import org.testng.annotations.Test;

public class TestJdbcComponentTest implements IJTester {
	@Test(groups = "jtester")
	public void minitorSql() throws Exception {
		JdbcTracerManager.initJdbcTracer(true);
		DataSource dataSource = new TestedDataSource();
		ProxyDataSource proxy = new ProxyDataSource(dataSource);

		Connection conn = proxy.getConnection();
		want.object(conn).clazIs(ProxyConnection.class);
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("select * from tdd_user");
		want.object(rs).notNull();

		String traces = JdbcTracerManager.endTracer();
		want.string(traces).contains("table").contains("select").contains("tdd_user");
	}
}

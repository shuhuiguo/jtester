package org.jtester.module.tracer.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.jtester.IAssertion;
import org.jtester.database.IProxyMarker;
import org.jtester.database.JTesterDataSource;
import org.jtester.helper.ConfigurationHelper;
import org.jtester.module.database.util.DataSourceType;
import org.jtester.tracer.JdbcTracerManager;
import org.junit.Test;

public class ConnectionProxyTest implements IAssertion {
	@Test
	public void minitorSql() throws Exception {
		JdbcTracerManager.initJdbcTracer(true);
		DataSourceType type = DataSourceType.databaseType("mysql");
		String username = ConfigurationHelper.databaseUserName();
		String driver = ConfigurationHelper.databaseDriver();
		String url = ConfigurationHelper.databaseUrl();
		String password = ConfigurationHelper.databasePassword();

		JTesterDataSource proxy = new JTesterDataSource(type, driver, url, "jtester", username, password);
		reflector.setStaticField(JTesterDataSource.class, "proxy", true);

		Connection conn = proxy.getConnection();
		want.object(conn).clazIs(Connection.class).clazIs(IProxyMarker.class);
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("select * from tdd_user");
		want.object(rs).notNull();

		String traces = JdbcTracerManager.endTracer();
		want.string(traces).contains("table").contains("select").contains("tdd_user");
	}
}

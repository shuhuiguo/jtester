package org.jtester.core.context;

import java.sql.SQLException;
import java.util.Map;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;
import org.jtester.fit.util.SymbolUtilTest.PoJoExample;

@Test(groups = "jtester")
@SuppressWarnings("rawtypes")
public class JTesterFitnesseTest extends JTester {

	@Test
	public void testExecute_NoSymbols() throws SQLException {
		fit.execute("delete from tdd_user", "insert into tdd_user(id,first_name) values(1,'darui.wu')", "commit");

		Map map = fit.query("select * from tdd_user", Map.class);
		want.map(map).hasEntry("id", 1, "firstName", "darui.wu");
	}

	@Test
	public void testExecute_HasSymbols() throws SQLException {
		fit.cleanSymbols();
		fit.setSymbol("firstName", "darui.wu");
		fit.execute("delete from tdd_user", "insert into tdd_user(id,first_name) values(1,'darui.wu')", "commit");

		Map map = fit.query("select * from tdd_user where first_name='@{firstName}'", Map.class);
		want.map(map).hasEntry("id", 1, "firstName", "darui.wu");
	}

	@Test
	public void testExecute_HasSymbols2() throws SQLException {
		fit.cleanSymbols();
		fit.setSymbol("pojo", new PoJoExample("darui.wu", null));
		fit.execute("delete from tdd_user", "insert into tdd_user(id,first_name) values(1,'darui.wu')", "commit");

		Map map = fit.query("select * from tdd_user where first_name='@{pojo[name]}'", Map.class);
		want.map(map).hasEntry("id", 1, "firstName", "darui.wu");
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testExecute_HasSymbols_exception() throws SQLException {
		fit.execute("delete from tdd_user", "insert into tdd_user(id,first_name) values(1,'darui.wu')", "commit");

		Map map = fit.query("select * from tdd_user where first_name='@{pojo[]}'", Map.class);
		want.map(map).hasEntry("id", 1, "firstName", "darui.wu");
	}
}

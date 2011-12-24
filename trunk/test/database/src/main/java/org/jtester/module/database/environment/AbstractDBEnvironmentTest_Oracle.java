package org.jtester.module.database.environment;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jtester.IAssertion;
import org.jtester.IDatabase;
import org.jtester.annotations.DbFit;
import org.jtester.matcher.property.reflection.EqMode;
import org.jtester.module.database.environment.impl.MySqlEnvironment;
import org.jtester.module.database.environment.impl.OracleEnvironment;
import org.junit.Test;

public class AbstractDBEnvironmentTest_Oracle implements IAssertion, IDatabase {
	@Test
	public void testExtractParamNames_oracle() {
		AbstractDBEnvironment env = new OracleEnvironment(null, null);
		fit.setSymbol("id", "1001");
		String[] vars = env.extractParamNames("update table set date='2011-07-01 14:23:35' where id=:id");
		want.array(vars).sizeEq(1).reflectionEq(new String[] { "id" }, EqMode.IGNORE_ORDER);
	}

	@Test
	public void testExtractParamNames_mysql() {
		AbstractDBEnvironment env = new MySqlEnvironment(null, null);
		fit.setSymbol("id", "1001");
		String[] vars = env.extractParamNames("update table set refence='@myreference' where id=@id");
		want.array(vars).sizeEq(1).reflectionEq(new String[] { "id" }, EqMode.IGNORE_ORDER);
	}

	@Test
	@DbFit(when = "data/AbstractDBEnvironmentTest_Oracle/testCreateStatementWithBoundFixtureSymbols.when.wiki", then = "data/AbstractDBEnvironmentTest_Oracle/testCreateStatementWithBoundFixtureSymbols.then.wiki")
	public void testCreateStatementWithBoundFixtureSymbols() throws SQLException {
		fit.setSymbol("id", "1");
		DBEnvironment env = DBEnvironmentFactory.getCurrentDBEnvironment();
		PreparedStatement statement = env
				.createStatementWithBoundFixtureSymbols("update jtester_user set name='@modified_name' where id=@id");
		statement.execute();
		statement.close();
	}

	@Test
	@DbFit(when = "data/AbstractDBEnvironmentTest_Oracle/oracle.when.wiki", then = "data/AbstractDBEnvironmentTest_Oracle/oracle.then.wiki")
	public void testCreateStatementWithBoundFixtureSymbols_Oracle() throws SQLException {
		fit.setSymbol("id", "123");
		fit.useSpecDataSource("", "eve.properties");
		fit.execute("update MTN_SEND_OVERVIEW set CREATOR=':mycreator' where id=:id", "commit");
		fit.commit();
	}
}

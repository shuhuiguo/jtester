package org.jtester.module.dbfit.db.fixture;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jtester.fit.JTesterFixture;
import org.jtester.module.dbfit.db.DatabaseUtils;
import org.jtester.module.dbfit.db.enviroment.DBEnvironment;
import org.jtester.module.dbfit.db.model.DataTable;

import fit.Parse;

public class StoreQueryTableFixture extends JTesterFixture {
	private DBEnvironment dbEnvironment;
	private String query;
	private String symbolName;

	// public StoreQueryTableFixture() {
	// dbEnvironment = DbFactory.instance().factory();//
	// DbEnvironmentFactory.getDefaultEnvironment();
	// }

	public StoreQueryTableFixture(DBEnvironment environment, String query, String symbolName) {
		this.dbEnvironment = environment;
		this.query = query;
		this.symbolName = symbolName;
	}

	public void doTable(Parse table) {
		if (query == null || symbolName == null) {
			if (args.length < 2) {
				String err = "No query and symbol name specified to StoreQuery constructor or argument list";
				throw new UnsupportedOperationException(err);
			}
			query = args[0];
			symbolName = args[1];
		}
		if (symbolName.startsWith(">>")) {
			symbolName = symbolName.substring(2);
		}
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = dbEnvironment.createStatementWithBoundFixtureSymbols(query);
			rs = st.executeQuery();
			DataTable dt = new DataTable(rs);
			org.jtester.fit.util.SymbolUtil.setSymbol(symbolName, dt);
		} catch (SQLException sqle) {
			throw new Error(sqle);
		} finally {
			DatabaseUtils.closeResultSet(rs);
			rs = null;
			DatabaseUtils.closeStatement(st);
			st = null;
		}
	}
}
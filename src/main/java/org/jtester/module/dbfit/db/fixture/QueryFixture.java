package org.jtester.module.dbfit.db.fixture;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jtester.fit.util.SymbolUtil;
import org.jtester.module.dbfit.db.DatabaseUtils;
import org.jtester.module.dbfit.db.enviroment.DBEnvironment;
import org.jtester.module.dbfit.db.model.DataColumn;
import org.jtester.module.dbfit.db.model.DataTable;

public class QueryFixture extends RowSetFixture {
	private DBEnvironment dbEnvironment;
	private String query;
	private boolean isOrdered;
	private static Log log = LogFactory.getLog(QueryFixture.class);

//	public QueryFixture() {
//		dbEnvironment = DbFactory.instance().factory();// DbEnvironmentFactory.getDefaultEnvironment();
//		isOrdered = false;
//	}

	public QueryFixture(DBEnvironment environment, String query) {
		this(environment, query, false);
	}

	public QueryFixture(DBEnvironment environment, String query, boolean isOrdered) {
		this.dbEnvironment = environment;
		this.query = query;
		this.isOrdered = isOrdered;
	}

	public DataTable getDataTable() throws SQLException {
		if (query == null) {
			query = args[0];
		}
		if (query.startsWith("<<")) {
			return getFromSymbol();
		}
		log.info(String.format("Query: '%s'", query));
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = dbEnvironment.createStatementWithBoundFixtureSymbols(query);
			rs = st.executeQuery();
			DataTable dt = new DataTable(rs);
			return dt;
		} finally {
			DatabaseUtils.closeResultSet(rs);
			rs = null;
			DatabaseUtils.closeStatement(st);
			st = null;
		}
	}

	private DataTable getFromSymbol() throws SQLException {
		Object o = SymbolUtil.getSymbol(query.substring(2).trim());
		if (o instanceof DataTable) {
			return (DataTable) o;
		}
		ResultSet rs = null;

		if (o instanceof ResultSet) {
			rs = (ResultSet) o;
		} else {
			String err = "Stored queries can only be used on symbols that contain result sets";
			throw new UnsupportedOperationException(err);
		}
		try {
			DataTable dt = new DataTable(rs);
			return dt;
		} finally {
			DatabaseUtils.closeResultSet(rs);
			rs = null;
		}
	}

	protected boolean isOrdered() {
		return isOrdered;
	}

	@Override
	protected Class<?> getJavaClassForColumn(DataColumn col) {
		return dbEnvironment.getJavaClass(col.getDbTypeName());
	}
}

package org.jtester.database.executor;

import java.util.List;

import org.jtester.beans.DataMap;
import org.jtester.module.database.environment.DBEnvironment;

public class QueryTableExecutor extends TableExecutor {
	private String query;

	private List<DataMap> expected;

	public QueryTableExecutor(String select, List<DataMap> expected) {
		super("select query");
		this.query = select;
		this.expected = expected;
	}

	public QueryTableExecutor(String table, String where, List<DataMap> expected) {
		super(table);
		if (where == null) {
			this.query = String.format("select * from %s", this.table);
		} else {
			this.query = String.format("select * from %s where %s", this.table, where);
		}
		this.expected = expected;
	}

	@Override
	public void execute(DBEnvironment dbEnvironment) {
		// TODO Auto-generated method stub

	}
}

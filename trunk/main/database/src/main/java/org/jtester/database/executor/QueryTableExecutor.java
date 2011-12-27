package org.jtester.database.executor;

import org.jtester.module.database.environment.DBEnvironment;

public class QueryTableExecutor extends TableExecutor {
	private String query;

	public QueryTableExecutor(String table) {
		super(table);
		this.query = String.format("select * from %s", this.table);
	}

	public QueryTableExecutor(String table, String where) {
		super(table);
		this.query = String.format("select * from %s where %s", this.table, where);
	}

	@Override
	public void execute(DBEnvironment dbEnvironment) {
		// TODO Auto-generated method stub

	}
}

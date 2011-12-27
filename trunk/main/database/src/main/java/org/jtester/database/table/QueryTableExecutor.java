package org.jtester.database.table;

import org.jtester.module.database.environment.DBEnvironment;

public class QueryTableExecutor extends TableExecutor {
	private String query;

	public QueryTableExecutor() {
		this.query = "select * from " + this.table;
	}

	public QueryTableExecutor(String where) {

	}

	@Override
	public void execute(DBEnvironment dbEnvironment) {
		// TODO Auto-generated method stub

	}
}

package org.jtester.database.table;

import org.jtester.module.database.environment.DBEnvironment;

public class QueryTable extends TableExecutor {
	private String query;

	public QueryTable() {
		this.query = "select * from " + this.table;
	}

	public QueryTable(String where) {

	}

	@Override
	public void execute(DBEnvironment dbEnvironment) {
		// TODO Auto-generated method stub

	}
}

package org.jtester.database.executor;

import org.jtester.module.database.environment.DBEnvironment;

public abstract class TableExecutor {
	protected String table;

	public TableExecutor(String table) {
		this.table = table;
	}

	public abstract void execute(DBEnvironment dbEnvironment);
}

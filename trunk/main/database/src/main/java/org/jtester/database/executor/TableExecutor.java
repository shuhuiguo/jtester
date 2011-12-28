package org.jtester.database.executor;

public abstract class TableExecutor {
	protected String table;

	public TableExecutor(String table) {
		this.table = table;
	}

	public abstract void execute();
}

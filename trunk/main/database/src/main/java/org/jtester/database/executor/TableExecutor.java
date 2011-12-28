package org.jtester.database.executor;

public abstract class TableExecutor {
	protected String xmlFile;

	protected String table;

	public TableExecutor(String xmlFile, String table) {
		this.table = table;
	}

	public abstract void execute();
}

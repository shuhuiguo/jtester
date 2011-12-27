package org.jtester.database.table;

import org.jtester.module.database.environment.DBEnvironment;

public abstract class TableExecutor {
	protected String table;

	public abstract void execute(DBEnvironment dbEnvironment);
}

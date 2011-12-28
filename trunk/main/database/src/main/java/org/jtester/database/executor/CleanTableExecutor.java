package org.jtester.database.executor;

import static org.jtester.database.operator.DBOperator.IN_DB_OPERATOR;

import org.jtester.module.database.util.SqlRunner;

public class CleanTableExecutor extends TableExecutor {

	public CleanTableExecutor(String table) {
		super(table);
	}

	@Override
	public void execute() {
		IN_DB_OPERATOR.set(true);
		try {
			String sql = "delete from " + table;
			SqlRunner.execute(sql);
		} finally {
			IN_DB_OPERATOR.set(false);
		}
	}
}

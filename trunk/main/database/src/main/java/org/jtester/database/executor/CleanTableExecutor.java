package org.jtester.database.executor;

import org.jtester.module.database.util.SqlRunner;

public class CleanTableExecutor extends TableExecutor {

	public CleanTableExecutor(String xmlFile, String table) {
		super(xmlFile, table);
	}

	@Override
	public void execute() {
		try {
			String sql = "delete from " + table;
			SqlRunner.execute(sql);
		} catch (Throwable e) {
			throw new RuntimeException("clean table[" + table + "] error in xml file[" + this.xmlFile + "].");
		}
	}
}

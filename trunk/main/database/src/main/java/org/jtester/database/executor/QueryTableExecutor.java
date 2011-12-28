package org.jtester.database.executor;

import static org.jtester.database.operator.DBOperator.IN_DB_OPERATOR;

import java.util.List;

import org.jtester.assertion.object.impl.CollectionAssert;
import org.jtester.beans.DataMap;
import org.jtester.matcher.property.reflection.EqMode;
import org.jtester.module.database.util.SqlRunner;

public class QueryTableExecutor extends TableExecutor {
	private String query;

	private List<DataMap> expected;

	private boolean isOrdered = false;

	public QueryTableExecutor(String select, List<DataMap> expected, boolean ordered) {
		super("select query");
		this.query = select;
		this.expected = expected;
		this.isOrdered = ordered;
	}

	public QueryTableExecutor(String table, String where, List<DataMap> expected, boolean ordered) {
		super(table);
		if (where == null) {
			this.query = String.format("select * from %s", this.table);
		} else {
			this.query = String.format("select * from %s where %s", this.table, where);
		}
		this.expected = expected;
		this.isOrdered = ordered;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute() {
		IN_DB_OPERATOR.set(true);
		try {
			List list = SqlRunner.queryMapList(query);
			if (isOrdered) {
				new CollectionAssert(list).reflectionEqMap(expected);
			} else {
				new CollectionAssert(list).reflectionEqMap(expected, EqMode.IGNORE_ORDER);
			}
		} finally {
			IN_DB_OPERATOR.set(false);
		}
	}
}

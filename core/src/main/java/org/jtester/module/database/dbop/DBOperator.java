package org.jtester.module.database.dbop;

import java.util.List;
import java.util.Map;

import org.jtester.hamcrest.iassert.object.impl.CollectionAssert;
import org.jtester.hamcrest.iassert.object.impl.MapAssert;
import org.jtester.hamcrest.iassert.object.impl.ObjectAssert;
import org.jtester.hamcrest.iassert.object.intf.ICollectionAssert;
import org.jtester.hamcrest.iassert.object.intf.IMapAssert;
import org.jtester.hamcrest.iassert.object.intf.IObjectAssert;
import org.jtester.module.database.environment.DBEnvironment;
import org.jtester.module.database.environment.DBEnvironmentFactory;
import org.jtester.module.database.util.SqlRunner;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DBOperator implements IDBOperator {

	public IDBOperator useDB(String dataSource) {
		DBEnvironment environment = DBEnvironmentFactory.getDBEnvironment(dataSource);
		DBEnvironmentFactory.changeDBEnvironment(environment);
		return this;
	}

	public IDBOperator useDefaultDB() {
		DBEnvironment environment = DBEnvironmentFactory.getDBEnvironment();
		DBEnvironmentFactory.changeDBEnvironment(environment);
		return this;
	}

	public IDBOperator cleanTable(String table, String... more) {
		SqlRunner.execute("delete from " + table);
		for (String item : more) {
			SqlRunner.execute("delete from " + item);
		}
		return this;
	}

	public IDBOperator execute(String sql) {
		SqlRunner.execute(sql);
		return this;
	}

	public IDBOperator commit() {
		SqlRunner.commit();
		return this;
	}

	public IDBOperator rollback() {
		SqlRunner.rollback();
		return this;
	}

	public IMapAssert queryAsMap(String query) {
		Map<String, Object> map = SqlRunner.queryMap(query);
		return new MapAssert(map);
	}

	public IObjectAssert queryAsPoJo(String query, Class objClazz) {
		Object o = SqlRunner.query(query, objClazz);
		return new ObjectAssert(o);
	}

	public ICollectionAssert query(String sql) {
		List list = SqlRunner.queryMapList(sql);
		return new CollectionAssert(list);
	}

	public ICollectionAssert queryList(String query, Class pojo) {
		List list = SqlRunner.queryList(query, pojo);
		return new CollectionAssert(list);
	}

	public ITableOp table(String table) {
		ITableOp tableOperator = new TableOp(table);
		return tableOperator;
	}

	public IDBOperator execute(SqlSet sqlSet) {
		if (sqlSet == null) {
			throw new RuntimeException("the insert sqlSet can't be null.");
		}
		sqlSet.execute();
		return this;
	}

	public List<Map<String, Object>> returnList(String table) {
		String query = "select * from " + table;
		List list = SqlRunner.queryMapList(query);
		return list;
	}

	public List<Object> returnList(String table, Class pojoClazz) {
		String query = "select * from " + table;
		List list = SqlRunner.queryList(query, pojoClazz);
		return list;
	}

	public List<Map<String, Object>> returnQuery(String query) {
		List list = SqlRunner.queryMapList(query);
		return list;
	}

	public List<Object> returnQuery(String query, Class pojoClazz) {
		List list = SqlRunner.queryList(query, pojoClazz);
		return list;
	}
}

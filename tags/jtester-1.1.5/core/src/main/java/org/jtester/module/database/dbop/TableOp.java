package org.jtester.module.database.dbop;

import java.util.List;

import org.jtester.core.IJTester.DataMap;
import org.jtester.hamcrest.iassert.object.impl.CollectionAssert;
import org.jtester.hamcrest.iassert.object.impl.LongAssert;
import org.jtester.hamcrest.iassert.object.impl.ObjectAssert;
import org.jtester.hamcrest.iassert.object.intf.ICollectionAssert;
import org.jtester.hamcrest.iassert.object.intf.INumberAssert;
import org.jtester.hamcrest.iassert.object.intf.IObjectAssert;
import org.jtester.json.JSON;
import org.jtester.module.database.dbop.AbstractDataSet.EmptyDataSet;
import org.jtester.module.database.util.SqlRunner;
import org.jtester.utility.StringHelper;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TableOp implements ITableOp {
	private static final long serialVersionUID = -5859213164627788602L;

	private String table;

	public TableOp(String table) {
		this.table = table;
		if (StringHelper.isBlankOrNull(this.table)) {
			throw new RuntimeException("the table name can't be null.");
		}
	}

	public ITableOp clean() {
		String sql = "delete from " + table;
		SqlRunner.execute(sql);
		return this;
	}

	public void commit() {
		SqlRunner.commit();
	}

	public void rollback() {
		SqlRunner.rollback();
	}

	public ITableOp insert(DataMap data, DataMap... more) {
		InsertOp.insert(table, data);
		for (DataMap map : more) {
			InsertOp.insert(table, map);
		}
		return this;
	}

	public ITableOp insert(String json, String... more) {
		DataMap map = JSON.toObject(json, DataMap.class);
		InsertOp.insert(table, map);
		for (String item : more) {
			map = JSON.toObject(item, DataMap.class);
			InsertOp.insert(table, map);
		}
		return this;
	}

	public ITableOp insert(final int count, final DataMap datas) {
		AbstractDataSet ds = new EmptyDataSet();
		ds.data(count, datas);
		ds.insert(table);
		return this;
	}

	public ITableOp insert(AbstractDataSet dataset) {
		if (dataset == null) {
			throw new RuntimeException("the insert dataset can't be null.");
		}
		dataset.insert(table);
		return this;
	}

	public ICollectionAssert query() {
		String query = "select * from " + table;
		List list = SqlRunner.queryMapList(query);
		return new CollectionAssert(list);
	}

	public ICollectionAssert queryList(Class pojo) {
		String query = "select * from " + table;
		List list = SqlRunner.queryList(query, pojo);
		return new CollectionAssert(list);
	}

	public INumberAssert count() {
		String query = "select count(*) from " + table;
		Number number = (Number) SqlRunner.query(query, Object.class);
		return new LongAssert(number.longValue());
	}

	public IObjectAssert queryAs(Class pojo) {
		String query = "select * from " + table;
		Object o = SqlRunner.query(query, pojo);
		return new ObjectAssert(o);
	}
}

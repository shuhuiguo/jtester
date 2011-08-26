package org.jtester.module.database;

import java.util.List;
import java.util.Map;

import org.jtester.hamcrest.iassert.object.impl.CollectionAssert;
import org.jtester.hamcrest.iassert.object.impl.LongAssert;
import org.jtester.hamcrest.iassert.object.impl.MapAssert;
import org.jtester.hamcrest.iassert.object.impl.ObjectAssert;
import org.jtester.hamcrest.iassert.object.intf.ICollectionAssert;
import org.jtester.hamcrest.iassert.object.intf.IMapAssert;
import org.jtester.hamcrest.iassert.object.intf.INumberAssert;
import org.jtester.hamcrest.iassert.object.intf.IObjectAssert;
import org.jtester.module.database.environment.DBEnvironment;
import org.jtester.module.database.environment.DBEnvironmentFactory;
import org.jtester.module.database.util.DataSet;
import org.jtester.module.database.util.SqlRunner;
import org.jtester.testng.JTester.DataIterator;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DBUtility {
	/**
	 * 使用数据源来执行下列的数据库操作<br>
	 * dataSource的名称在jtester.properties文件中定义
	 * 
	 * @param dataSource
	 * @return
	 */
	public DBUtility useDataSource(String dataSource) {
		DBEnvironment environment = DBEnvironmentFactory.getDBEnvironment(dataSource);
		DBEnvironmentFactory.changeDBEnvironment(environment);
		return this;
	}

	/**
	 * 使用jtester.properties中配置的默认数据源
	 * 
	 * @return
	 */
	public DBUtility useDefaultDataSource() {
		DBEnvironment environment = DBEnvironmentFactory.getDBEnvironment();
		DBEnvironmentFactory.changeDBEnvironment(environment);
		return this;
	}

	/**
	 * 清空指定的若干张表的数据
	 * 
	 * @param table
	 * @param more
	 * @return
	 */
	public DBUtility clean(String table, String... more) {
		SqlRunner.execute("delete from " + table);
		for (String item : more) {
			SqlRunner.execute("delete from " + item);
		}
		return this;
	}

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @param more
	 * @return
	 */
	public DBUtility execute(String sql, String... more) {
		SqlRunner.execute(sql);
		for (String item : more) {
			SqlRunner.execute(item);
		}
		return this;
	}

	/**
	 * 执行一批sql语句
	 * 
	 * @param sqlIterator
	 * @return
	 */
	public DBUtility execute(DataIterator sqlIterator) {
		for (; sqlIterator.hasNext();) {
			Object o = sqlIterator.next();
			if (o instanceof String) {
				SqlRunner.execute((String) o);
			} else {
				throw new RuntimeException(
						"the item of sql iterator can only be string, but actual is " + o == null ? "null" : o
								.getClass().getName() + ".");
			}
		}
		return this;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	public DBUtility commit() {
		SqlRunner.commit();
		return this;
	}

	/**
	 * 回滚未提交的数据操作
	 * 
	 * @return
	 */
	public DBUtility rollback() {
		SqlRunner.rollback();
		return this;
	}

	/**
	 * 查询数据，并且返回的数据只有一行<br>
	 * 将数据自动填充到Map<String,Object>中
	 * 
	 * @param query
	 * @return
	 */
	public IMapAssert queryToMap(String query) {
		Map<String, Object> map = SqlRunner.queryToMap(query);
		return new MapAssert(map);
	}

	/**
	 * 查询数据，并且返回的数据只有一行<br>
	 * 将数据自动填充到objClazz类型的对象中,并且返回对象的断言器
	 * 
	 * @param query
	 * @param objClazz
	 * @return
	 */
	public IObjectAssert queryToPoJo(String query, Class objClazz) {
		Object o = SqlRunner.query(query, objClazz);
		return new ObjectAssert(o);
	}

	/**
	 * 查询单行单列数据,并且返回对象的断言器
	 * 
	 * @param query
	 * @return
	 */
	public IObjectAssert querySingle(String query) {
		Object o = SqlRunner.query(query, Object.class);
		return new ObjectAssert(o);
	}

	/**
	 * 查询表中所有的数据，并且每条数据填充到Map中<br>
	 * 返回 List<Map> 的断言器
	 * 
	 * @param table
	 * @return
	 */
	public ICollectionAssert query(String table) {
		String query = "select * from " + table;
		return this.queryToList(query);
	}

	/**
	 * 查询表中所有的数据，并且每条数据填充到PoJo中<br>
	 * 返回 List<Obejct> 的断言器
	 * 
	 * @param table
	 * @param pojoClazz
	 * @return
	 */
	public ICollectionAssert query(String table, Class pojoClazz) {
		String query = "select * from " + table;
		return this.queryToList(query, pojoClazz);
	}

	/**
	 * 查询表count(*)的值，并且返回断言器
	 * 
	 * @param table
	 * @return
	 */
	public INumberAssert count(String table) {
		String query = "select count(*) from " + table;
		Long number = (Long) SqlRunner.query(query, Object.class);
		return new LongAssert(number);
	}

	/**
	 * 查询数据列表（每条数据存放在map中），并且返回list的断言器
	 * 
	 * @param query
	 * @return
	 */
	public ICollectionAssert queryToList(String query) {
		List list = SqlRunner.queryToMapList(query);
		return new CollectionAssert(list);
	}

	/**
	 * 查询数据列表（每条数据填充到PoJO中），并且返回list的断言器
	 * 
	 * @param query
	 * @param objClazz
	 * @return
	 */
	public ICollectionAssert queryToList(String query, Class objClazz) {
		List list = SqlRunner.queryList(query, objClazz);
		return new CollectionAssert(list);
	}

	/**
	 * 对名称为table的表进行数据操作
	 * 
	 * @param table
	 * @return
	 */
	public IDataSet table(String table) {
		IDataSet ds = new DataSet(table);
		return ds;
	}

	public List<Map<String, Object>> returnList(String table) {
		String query = "select * from " + table;
		List list = SqlRunner.queryToMapList(query);
		return list;
	}

	public List<Object> returnList(String table, Class pojoClazz) {
		String query = "select * from " + table;
		List list = SqlRunner.queryList(query, pojoClazz);
		return list;
	}

	public List<Map<String, Object>> returnQuery(String query) {
		List list = SqlRunner.queryToMapList(query);
		return list;
	}

	public List<Object> returnQuery(String query, Class pojoClazz) {
		List list = SqlRunner.queryList(query, pojoClazz);
		return list;
	}
}

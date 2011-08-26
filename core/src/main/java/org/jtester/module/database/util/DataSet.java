package org.jtester.module.database.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.jtester.exception.ExceptionWrapper;
import org.jtester.module.database.IDataSet;
import org.jtester.module.database.environment.DBEnvironment;
import org.jtester.module.database.environment.DBEnvironmentFactory;
import org.jtester.module.database.environment.TableMeta;
import org.jtester.testng.JTester.DataIterator;
import org.jtester.utility.ArrayHelper;
import org.jtester.utility.StringHelper;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DataSet implements IDataSet {
	private static final long serialVersionUID = -5859213164627788602L;

	private String table;

	private String[] columns;

	public DataSet(String table) {
		this.table = table;
		if (StringHelper.isBlankOrNull(this.table)) {
			throw new RuntimeException("the table name can't be null.");
		}
	}

	public IDataSet clean() {
		String sql = "delete from " + table;
		SqlRunner.execute(sql);
		return this;
	}

	public IDataSet columns(String[] columns) {
		if (columns == null || columns.length == 0) {
			throw new RuntimeException("the insert columns can't be empty.");
		}
		this.columns = columns;
		return this;
	}

	public IDataSet columns(String column, String... columns) {
		this.columns = new String[columns.length + 1];
		this.columns[0] = column;
		for (int index = 0; index < columns.length; index++) {
			this.columns[index + 1] = columns[index];
		}
		return this;
	}

	public IDataSet insert(Object data, Object... more) {
		Object[] datas = ArrayHelper.toArray(data, more);
		try {
			this.insertDataArray(datas);
			return this;
		} catch (Exception e) {
			throw ExceptionWrapper.getUndeclaredThrowableExceptionCaused(e);
		}
	}

	public IDataSet insert(Object[] datas, Object[]... more) {
		try {
			this.insertDataArray(datas);
			for (Object[] array : more) {
				this.insertDataArray(array);
			}
			return this;
		} catch (Exception e) {
			throw ExceptionWrapper.getUndeclaredThrowableExceptionCaused(e);
		}
	}

	private void insertDataArray(Object[] datas) throws Exception {
		if (columns == null) {
			throw new RuntimeException("please specify column names first, use columns(String, String...).");
		}
		if (datas.length != columns.length) {
			throw new RuntimeException("the insert datas size() must equal to the columns size().");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for (int index = 0; index < columns.length; index++) {
			map.put(columns[index], datas[index]);
		}
		this.insertMapData(map);
	}

	public IDataSet insert(String[] columns, DataIterator datas) {
		this.columns(columns);
		for (; datas.hasNext();) {
			Object item = datas.next();
			try {
				if (item instanceof Map) {
					this.insertMapData((Map) item);
				} else {
					Object[] array = ArrayHelper.toArray(item);
					this.insertDataArray(array);
				}
			} catch (Exception e) {
				throw ExceptionWrapper.getUndeclaredThrowableExceptionCaused(e);
			}
		}
		return this;
	}

	public IDataSet insert(DataIterator datas) {
		for (; datas.hasNext();) {
			Object item = datas.next();
			try {
				if (item instanceof Map) {
					this.insertMapData((Map) item);
				} else {
					Object[] array = ArrayHelper.toArray(item);
					this.insertDataArray(array);
				}
			} catch (Exception e) {
				throw ExceptionWrapper.getUndeclaredThrowableExceptionCaused(e);
			}
		}
		return this;
	}

	public IDataSet insert(Map<String, Object> data, Map<String, Object>... more) {
		try {
			this.insertMapData(data);
			for (Map<String, Object> map : more) {
				this.insertMapData(map);
			}
			return this;
		} catch (Exception e) {
			throw ExceptionWrapper.getUndeclaredThrowableExceptionCaused(e);
		}
	}

	private void insertMapData(Map<String, Object> data) throws Exception {
		PreparedStatement statement = this.createPreparedStatement(data);
		statement.clearParameters();
		int index = 1;
		for (String key : data.keySet()) {
			statement.setObject(index++, data.get(key));
		}
		try {
			statement.execute();
		} catch (Exception e) {
			Exception e1 = ExceptionWrapper.getUndeclaredThrowableExceptionCaused(e);
			String msg = "insert data error, data=\n" + data.toString();
			throw new RuntimeException(msg, e1);
		} finally {
			DBHelper.closeStatement(statement);
		}
	}

	/**
	 * 创建数据库插入语句
	 * 
	 * @param dbEnvironment
	 * @param table
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	private PreparedStatement createPreparedStatement(Map<String, Object> map) throws SQLException {
		DBEnvironment dbEnvironment = DBEnvironmentFactory.getCurrentDBEnvironment();
		Connection connection = dbEnvironment.connectIfNeeded();

		TableMeta meta = dbEnvironment.getTableMetaData(table);
		meta.fillData(map, dbEnvironment);

		String text = getInsertCommandText(map);
		PreparedStatement statement = connection.prepareStatement(text);
		return statement;
	}

	/**
	 * 构造map的insert sql语句
	 * 
	 * @param table
	 * @param map
	 * @return
	 */
	private String getInsertCommandText(Map<String, Object> map) {
		StringBuilder text = new StringBuilder();
		StringBuilder values = new StringBuilder();

		text.append("insert into ").append(table).append("(");
		boolean isFirst = true;
		for (String key : map.keySet()) {
			if (isFirst) {
				isFirst = false;
			} else {
				text.append(",");
				values.append(",");
			}
			text.append(key);
			values.append("?");
		}

		text.append(") values(").append(values).append(")");
		return text.toString();
	}
}

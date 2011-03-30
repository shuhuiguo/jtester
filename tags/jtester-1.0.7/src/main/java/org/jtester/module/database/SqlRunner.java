package org.jtester.module.database;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jtester.exception.DbFitException;
import org.jtester.fit.util.SymbolUtil;
import org.jtester.module.database.environment.DBEnvironment;
import org.jtester.module.database.environment.DBEnvironmentFactory;
import org.jtester.module.database.util.DBHelper;
import org.jtester.utility.ResourceHelper;

/**
 * sql 执行器
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SqlRunner {
	/**
	 * 连接数据源，如果先前没有建立过连接的话
	 * 
	 * @param environment
	 * @return 返回数据源连接
	 * @throws SQLException
	 */
	private static Connection connectIfNeeded(DBEnvironment environment) throws SQLException {
		Connection conn = environment.getConnection();
		if (conn == null || conn.isClosed()) {
			conn = environment.connect();
		}
		return conn;
	}

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public static void execute(String sql) throws SQLException {
		DBEnvironment environment = DBEnvironmentFactory.getCurrentDBEnvironment();
		connectIfNeeded(environment);
		PreparedStatement st = environment.createStatementWithBoundFixtureSymbols(sql);
		st.execute();
	}

	/**
	 * 设置变量，执行sql文件
	 * 
	 * @param symbols
	 * @param fileName
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	public static void executeFromFile(Map<String, ?> symbols, String fileName) throws SQLException,
			FileNotFoundException {
		SymbolUtil.setSymbol(symbols);
		executeFromFile(fileName);
	}

	/**
	 * 执行sql文件<br>
	 * 默认从classpath中读取<br>
	 * classpath:前缀开头，表示从classpath中读取<br>
	 * file:前缀开头，表示从文件系统中读取<br>
	 * 
	 * @param fileName
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	public static void executeFromFile(String fileName) throws SQLException, FileNotFoundException {
		DBEnvironment environment = DBEnvironmentFactory.getCurrentDBEnvironment();
		connectIfNeeded(environment);

		String sqls = ResourceHelper.readFromFile(fileName);
		String[] statements = DBHelper.parseSQL(sqls);
		for (String statment : statements) {
			PreparedStatement st = environment.createStatementWithBoundFixtureSymbols(statment);
			st.execute();
		}
	}

	/**
	 * 执行sql文件<br>
	 * 
	 * @see executeFromFile
	 * 
	 * @param clazz
	 *            SQL文件所在的classpath
	 * @param fileName
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	public static void executeFromFile(Class clazz, String fileName) throws SQLException, FileNotFoundException {
		DBEnvironment environment = DBEnvironmentFactory.getCurrentDBEnvironment();
		connectIfNeeded(environment);

		String sqls = ResourceHelper.readFromFile(clazz, fileName);

		String[] statements = DBHelper.parseSQL(sqls);
		for (String statment : statements) {
			PreparedStatement st = null;
			try {
				st = environment.createStatementWithBoundFixtureSymbols(statment);
				st.execute();
			} catch (Exception e) {
				throw new DbFitException("there are some error when execute sql file [" + fileName + "]", e);
			} finally {
				DBHelper.closeStatement(st);
			}
		}
	}

	/**
	 * 根据sql查询数据，如果result是Map.class则返回Map类型<br>
	 * 如果是PoJo，则根据camel name命名方式初始化result
	 * 
	 * @param <T>
	 * @param sql
	 * @param result
	 * @return
	 * @throws SQLException
	 */
	public static <T> T query(String sql, Class<T> claz) throws SQLException {
		DBEnvironment environment = DBEnvironmentFactory.getCurrentDBEnvironment();
		connectIfNeeded(environment);
		PreparedStatement st = environment.createStatementWithBoundFixtureSymbols(sql);
		ResultSet rs = null;
		try {
			rs = st.executeQuery();
			if (rs.next() == false) {
				throw new RuntimeException("there are no result for statement:" + sql);
			}
			ResultSetMetaData rsmd = rs.getMetaData();
			if (Map.class.isAssignableFrom(claz)) {
				Map value = DBHelper.getMapFromResult(rs, rsmd);
				return (T) value;
			} else {
				T pojo = DBHelper.getPoJoFromResult(rs, rsmd, claz);
				return pojo;
			}
		} finally {
			DBHelper.closeResultSet(rs);
		}
	}

	/**
	 * 执行sql，返回查询数据列表，如果result是Map.class则返回Map列表<br>
	 * 如果是PoJo，则根据camel name命名方式初始化result，返回PoJo列表
	 * 
	 * @param <T>
	 * @param sql
	 * @param result
	 * @return
	 * @throws SQLException
	 */
	public static <T> List<T> queryList(String sql, Class<T> clazz) throws SQLException {
		DBEnvironment environment = DBEnvironmentFactory.getCurrentDBEnvironment();
		connectIfNeeded(environment);
		PreparedStatement st = environment.createStatementWithBoundFixtureSymbols(sql);
		ResultSet rs = null;
		try {
			rs = st.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
			if (Map.class.isAssignableFrom(clazz)) {
				List<Map> maps = DBHelper.getListMapFromResult(rs, rsmd);
				return (List<T>) maps;
			} else {
				List<T> list = DBHelper.getListPoJoFromResult(rs, rsmd, clazz);
				return list;
			}
		} finally {
			DBHelper.closeResultSet(rs);
		}
	}
}

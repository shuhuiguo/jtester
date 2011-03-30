package org.jtester.module.dbfit;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jtester.module.database.DatabaseType;
import org.jtester.module.dbfit.db.enviroment.DBEnvironment;
import org.jtester.module.dbfit.db.enviroment.DerbyEnvironment;
import org.jtester.module.dbfit.db.enviroment.MySqlEnvironment;
import org.jtester.module.dbfit.db.enviroment.OracleEnvironment;
import org.jtester.module.dbfit.db.enviroment.SqlServerEnvironment;
import org.jtester.module.dbfit.db.model.DbIdentify;

public class DbFactory {
	/**
	 * 当前正在使用的数据库类型
	 */
	private static ThreadLocal<DbIdentify> localDbIdentify = new ThreadLocal<DbIdentify>();
	/**
	 * 数据库环境列表
	 */
	private static Map<DbIdentify, DBEnvironment> dbEnvironments = new ConcurrentHashMap<DbIdentify, DBEnvironment>();

	public static Collection<DBEnvironment> dbEnvironments() {
		return dbEnvironments.values();
	}

	/**
	 * 当前数据库驱动程序
	 * 
	 * @return
	 */
	public static String currDriver() {
		return currDbIdentify().getDriver();
	}

	public static DBEnvironment factory(DatabaseType dbtype) {
		switch (dbtype) {
		case MYSQL:
			return new MySqlEnvironment();
		case ORACLE:
			return new OracleEnvironment();
		case SQLSERVER:
			return new SqlServerEnvironment();
		case DERBYDB:
			return new DerbyEnvironment();
		default:
			throw new RuntimeException("unsupport database type," + dbtype.getDriveClass());
		}
	}

	private static DbIdentify currDbIdentify() {
		DbIdentify id = localDbIdentify.get();
		if (id == null) {
			id = setDefaultDb();
		}
		return id;
	}

	/**
	 * 获取当前正在使用的数据库环境
	 * 
	 * @return
	 */
	public static DBEnvironment currDBEnvironment() {
		DbIdentify dbID = currDbIdentify();
		DBEnvironment environment = dbEnvironments.get(dbID);
		if (environment == null) {
			environment = DbFactory.factory(dbID.getType());
			dbEnvironments.put(dbID, environment);
		}
		return environment;
	}

	/**
	 * 设置当前数据库环境为默认设置
	 * 
	 * @return
	 */
	public static DbIdentify setDefaultDb() {
		DatabaseType defaultDbType = DatabaseType.type();
		String driver = defaultDbType.getDriveClass();
		String dburl = defaultDbType.getConnUrl();

		DbIdentify id = new DbIdentify(defaultDbType, driver, dburl);
		localDbIdentify.set(id);
		return id;
	}

	/**
	 * 设置当前数据库环境为默认设置
	 * 
	 * @return
	 */
	public static DbIdentify setDefaultDb(String driver, String url) {
		DatabaseType defaultDbType = DatabaseType.type();
		DbIdentify id = new DbIdentify(defaultDbType, driver, url);
		localDbIdentify.set(id);
		return id;
	}

	/**
	 * 设置当前数据库环境
	 * 
	 * @return
	 */
	public static DbIdentify setDbIdentify(String type, String driver, String url) {
		DatabaseType dbType = DatabaseType.type(type);
		DbIdentify id = new DbIdentify(dbType, driver, url);
		localDbIdentify.set(id);
		return id;
	}

	/**
	 * "org.apache.derby.jdbc.EmbeddedDriver" <br>
	 * "org.apache.derby.jdbc.ClientDriver" <br>
	 * "com.ibm.db2.jcc.DB2Driver"<br>
	 * "oracle.jdbc.OracleDriver" <br>
	 * "com.microsoft.sqlserver.jdbc.SQLServerDriver"<br>
	 * "com.mysql.jdbc.Driver"
	 */
}

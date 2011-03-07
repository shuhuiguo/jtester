package org.jtester.module.dbfit;

import static org.jtester.module.utils.ConfigUtil.PROPKEY_DATABASE_TYPE;
import static org.jtester.module.utils.ConfigUtil.PROPKEY_DATASOURCE_DRIVERCLASSNAME;
import static org.jtester.module.utils.ConfigUtil.PROPKEY_DATASOURCE_PASSWORD;
import static org.jtester.module.utils.ConfigUtil.PROPKEY_DATASOURCE_URL;
import static org.jtester.module.utils.ConfigUtil.PROPKEY_DATASOURCE_USERNAME;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jtester.fit.util.SymbolUtil;
import org.jtester.module.dbfit.db.enviroment.DBEnvironment;
import org.jtester.module.dbfit.db.enviroment.Options;
import org.jtester.module.dbfit.db.fixture.CleanFixture;
import org.jtester.module.dbfit.db.fixture.CompareStoredQueriesFixture;
import org.jtester.module.dbfit.db.fixture.DeleteFixture;
import org.jtester.module.dbfit.db.fixture.ExecuteProcedureFixture;
import org.jtester.module.dbfit.db.fixture.InsertFixture;
import org.jtester.module.dbfit.db.fixture.InspectFixture;
import org.jtester.module.dbfit.db.fixture.QueryFixture;
import org.jtester.module.dbfit.db.fixture.QueryStatsFixture;
import org.jtester.module.dbfit.db.fixture.StoreQueryFixture;
import org.jtester.module.dbfit.db.fixture.StoreQueryTableFixture;
import org.jtester.module.dbfit.db.fixture.UpdateFixture;
import org.jtester.module.utils.DbFitModuleHelper;
import org.jtester.utility.DateUtil;
import org.jtester.utility.StringHelper;

import fit.Fixture;
import fitlibrary.SequenceFixture;
import fitlibrary.table.Table;
import fitlibrary.utility.TestResults;

public class DatabaseFixture extends SequenceFixture {
	private static Log log = LogFactory.getLog(DatabaseFixture.class);

	public DatabaseFixture() {
		// DbIdentify id = this.currDbIdentify();
		// this.environment = DbFactory.instance().factory(id.type);
	}

	@Override
	public void setUp(Table firstTable, TestResults testResults) {
		Options.reset();
		super.setUp(firstTable, testResults);
	}

	/**
	 * 在测试的最后结束事务
	 * 
	 * @throws SQLException
	 */
	public static void endTransactional() {
		Collection<DBEnvironment> envs = DbFactory.dbEnvironments();
		StringBuilder err = new StringBuilder();
		for (DBEnvironment env : envs) {
			try {
				Connection conn = env.getConnection();
				if (conn != null && conn.isClosed() == false) {
					env.rollback();
					env.closeConnection();
				}
			} catch (Exception e) {
				err.append(StringHelper.exceptionTrace(e));
			}
		}
		String msg = err.toString();
		if ("".equalsIgnoreCase(msg.trim()) == false) {
			throw new RuntimeException(msg);
		}
	}

	@Override
	public void tearDown(Table firstTable, TestResults testResults) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		
		try {
			log.info("Rolling back");
			if (environment == null) {
				return;
			}
			boolean isCommit = DbFitModuleHelper.isSpringTransaction() == false || DbFitModuleHelper.isTransactionDisabled();
			if (isCommit) {
				environment.commit();
				environment.closeConnection();
			}
			environment.teardown();
		} catch (Exception e) {
			this.exception(firstTable.parse, e);
		}
		super.tearDown(firstTable, testResults);
	}

	/**
	 * 使用jtester默认的数据库配置类连接数据库
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean connect() throws Exception {
		DbFactory.setDefaultDb();
		DBEnvironment environment = DbFactory.currDBEnvironment();
		environment.connect();
		environment.getConnection().setAutoCommit(false);
		return true;
	}

	/**
	 * 使用指定的driver来建立连接
	 * 
	 * @param dataSource
	 * @param username
	 * @param password
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	public boolean connect(String dburl, String username, String password, String driver) throws Exception {
		DbFactory.setDefaultDb(driver, dburl);
		DBEnvironment environment = DbFactory.currDBEnvironment();
		environment.connect(dburl, username, password, driver);
		environment.getConnection().setAutoCommit(false);
		return true;
	}

	public boolean connect(String dburl, String username, String password, String driver, String dbtype)
			throws Exception {
		DbFactory.setDbIdentify(dbtype, driver, dburl);
		DBEnvironment environment = DbFactory.currDBEnvironment();
		environment.connect(dburl, username, password, driver);
		environment.getConnection().setAutoCommit(false);
		return true;
	}

	final static String NO_VALID_VALUE_MESSAGE = "can't find valid value of key[%s] in file[%s]!";

	/**
	 * 使用默认的配置文件jtester.properties建立连接
	 * 
	 * @param dbname
	 *            数据库配置的前缀, ${dbname}.database.type =...
	 * @return
	 * @throws Exception
	 */
	public boolean connectFromFile(String dbname) throws Exception {
		return connectFromFile(dbname, "jtester.properties");
	}

	/**
	 * 使用配置文件建立连接,prefix是数据库连接属性前缀
	 * 
	 * @param propFile
	 * @param dbname
	 *            数据库配置的前缀, ${dbname}.database.type =...
	 * @return
	 * @throws Exception
	 */
	public boolean connectFromFile(String dbname, String propFile) throws Exception {
		InputStream in = ClassLoader.getSystemResourceAsStream(propFile);
		if (in == null) {
			throw new RuntimeException(String.format("can't find file[%s] in classpath!", propFile));
		}
		Properties props = new Properties();

		props.load(in);
		String prevName = StringHelper.isBlankOrNull(dbname) ? "" : dbname + ".";
		String dbType = props.getProperty(prevName + PROPKEY_DATABASE_TYPE);

		String url = props.getProperty(prevName + PROPKEY_DATASOURCE_URL);
		if (StringHelper.isBlankOrNull(url)) {
			throw new RuntimeException(String.format(NO_VALID_VALUE_MESSAGE, prevName + PROPKEY_DATASOURCE_URL,
					propFile));
		}

		String user = props.getProperty(prevName + PROPKEY_DATASOURCE_USERNAME);
		if (StringHelper.isBlankOrNull(user)) {
			throw new RuntimeException(String.format(NO_VALID_VALUE_MESSAGE, prevName + PROPKEY_DATASOURCE_USERNAME,
					propFile));
		}
		String pass = props.getProperty(prevName + PROPKEY_DATASOURCE_PASSWORD);
		if (pass == null) {
			pass = "";
		}
		String driver = props.getProperty(prevName + PROPKEY_DATASOURCE_DRIVERCLASSNAME);
		if (StringHelper.isBlankOrNull(driver)) {
			throw new RuntimeException(String.format(NO_VALID_VALUE_MESSAGE, prevName
					+ PROPKEY_DATASOURCE_DRIVERCLASSNAME, propFile));
		}
		DbFactory.setDbIdentify(dbType, driver, url);

		DBEnvironment environment = DbFactory.currDBEnvironment();
		environment.connect(url, user, pass, driver);
		environment.getConnection().setAutoCommit(false);
		return true;
	}

	public boolean close() throws SQLException {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		environment.rollback();
		environment.closeConnection();
		return true;
	}

	public boolean setParameter(String name, String value) {
		DbFixtureUtil.setParameter(name, value);
		return true;
	}

	/**
	 * query查询结果是单个值，以变量形式存储下来备用
	 * 
	 * @param para
	 * @param query
	 * @return
	 */
	public Fixture storeQuery(String query, String symbolName) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new StoreQueryFixture(environment, query, symbolName);
	}

	/**
	 * 设置当前时间格式
	 * 
	 * @param format
	 * @return
	 */
	public boolean setDateTimeFormat(String format) {
		String datetime = DateUtil.currDateTimeStr(format);
		DbFixtureUtil.setParameter("datetime", datetime);
		return true;
	}

	/**
	 * 设置当前日期格式
	 * 
	 * @param format
	 * @return
	 */
	public boolean setDateFormat(String format) {
		String date = DateUtil.currDateTimeStr(format);
		DbFixtureUtil.setParameter("date", date);
		return true;
	}

	public boolean clearParameters() {
		SymbolUtil.cleanSymbols();
		return true;
	}

	public Fixture query(String query) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new QueryFixture(environment, query);
	}

	public Fixture orderedQuery(String query) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new QueryFixture(environment, query, true);
	}

	public boolean execute(String statement) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return DbFixtureUtil.execute(environment, statement);
	}

	public Fixture executeProcedure(String statement) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new ExecuteProcedureFixture(environment, statement);
	}

	public Fixture executeProcedureExpectException(String statement) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new ExecuteProcedureFixture(environment, statement, true);
	}

	public Fixture executeProcedureExpectException(String statement, int code) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new ExecuteProcedureFixture(environment, statement, code);
	}

	public Fixture insert(String tableName) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new InsertFixture(environment, tableName);
	}

	public Fixture update(String tableName) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new UpdateFixture(environment, tableName);
	}

	public Fixture clean() {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new CleanFixture(environment);
	}

	public boolean cleanTable(String tables) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		String ts[] = tables.split("[;,]");
		for (String table : ts) {
			DbFixtureUtil.cleanTable(environment, table);
		}
		return true;
	}

	/**
	 * 根据表字段删除数据
	 * 
	 * @param table
	 * @return
	 */
	public Fixture delete(String table) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new DeleteFixture(environment, table);
	}

	public boolean rollback() throws SQLException {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		environment.rollback();
		environment.getConnection().setAutoCommit(false);
		return true;
	}

	public boolean commit() throws SQLException {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		environment.commit();
		environment.getConnection().setAutoCommit(false);
		return true;
	}

	public Fixture queryStats() {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new QueryStatsFixture(environment);
	}

	public Fixture inspectProcedure(String procName) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new InspectFixture(environment, InspectFixture.MODE_PROCEDURE, procName);
	}

	public Fixture inspectTable(String tableName) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new InspectFixture(environment, InspectFixture.MODE_TABLE, tableName);
	}

	public Fixture inspectView(String tableName) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new InspectFixture(environment, InspectFixture.MODE_TABLE, tableName);
	}

	public Fixture inspectQuery(String query) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new InspectFixture(environment, InspectFixture.MODE_QUERY, query);
	}

	/**
	 * 把查询结果的整张表作为结果存储下来 <br>
	 * storeQuery把查询结果是单个值，以变量形式存储下来
	 * 
	 * @param query
	 * @param symbolName
	 * @return
	 */
	public Fixture storeQueryTable(String query, String symbolName) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new StoreQueryTableFixture(environment, query, symbolName);
	}

	public Fixture compareStoredQueries(String symbol1, String symbol2) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return new CompareStoredQueriesFixture(environment, symbol1, symbol2);
	}

	public boolean setOption(String option, String value) {
		Options.setOption(option, value);
		return true;
	}

	/**
	 * 执行指定文件中的sql语句
	 * 
	 * @param file
	 * @return
	 */
	public boolean executeFile(String file) {
		DBEnvironment environment = DbFactory.currDBEnvironment();
		return DbFixtureUtil.executeFromFile(environment, file == null ? null : file.trim());
	}

}

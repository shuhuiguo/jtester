package org.jtester.module.database;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jtester.module.utils.ConfigUtil;
import org.jtester.module.utils.TracerModuleHelper;

public class JTesterDataSourceFactory implements DataSourceFactory {
	protected static Log log = LogFactory.getLog(JTesterDataSourceFactory.class);

	public static final String PROPKEY_DATASOURCE_SCHEMANAMES = "database.schemaNames";

	private DatabaseType type = null;

	public void init(Properties configuration) {
		this.type = DatabaseType.type();
	}

	public DataSource createDataSource() {
		this.checkDoesTestDB();
		BasicDataSource dataSource = new BasicDataSource();
		this.initFactualDataSource(dataSource);

//		this.doesDisableDataSource(dataSource);

		return TracerModuleHelper.tracerDataSource(dataSource);
	}

	/**
	 * 判断是否本地数据库或者是test数据库<br>
	 * 如果不是返回RuntimeException
	 */
	private void checkDoesTestDB() {
		if (ConfigUtil.doesOnlyTestDatabase() == false) {
			return;
		}
		if (this.type.isMemoryDB() || this.type.getConnUrl().contains("127.0.0.1")
				|| this.type.getConnUrl().toUpperCase().contains("LOCALHOST")) {
			return;
		}
		String[] schemas = this.type.getSchemas().split(";");
		for (String schema : schemas) {
			if (schema.trim().equals("")) {
				continue;
			}
			String temp = schema.toUpperCase();
			if (!temp.endsWith("TEST") && !temp.startsWith("TEST")) {
				throw new RuntimeException("only local db or test db will be allowed to connect,url:"
						+ this.type.getConnUrl() + ", schemas:" + this.type.getSchemas());
			}
		}
	}

//	/**
//	 * 是否需要去除数据库的外键约束和字段not null约束
//	 * 
//	 * @param dataSource
//	 */
//	protected void doesDisableDataSource(DataSource dataSource) {
//		if (!ConfigUtil.doesDisableConstraints()) {
//			return;
//		}
//		log.info("Disables all foreign key and not-null constraints on the configured schema's.");
//		SQLHandler handler = new DefaultSQLHandler(dataSource);
//		ConstraintsDisabler disabler = DatabaseModuleConfigUtils.getConfiguredDatabaseTaskInstance(
//				ConstraintsDisabler.class, ConfigUtil.unitilscfg, handler);
//		disabler.disableConstraints();
//	}

	/**
	 * 初始化实际的数据库连接
	 * 
	 * @param dataSource
	 */
	protected void initFactualDataSource(BasicDataSource dataSource) {
		log.info("Creating data source. Driver: " + type.getDriveClass() + ", url: " + type.getConnUrl() + ", user: "
				+ type.getUserName() + ", password: <not shown>");
		dataSource.setDriverClassName(type.getDriveClass());
		dataSource.setUsername(type.getUserName());
		dataSource.setPassword(type.getUserPass());
		dataSource.setUrl(type.getConnUrl());
	}
}

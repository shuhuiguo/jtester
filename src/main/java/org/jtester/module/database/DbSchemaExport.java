package org.jtester.module.database;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.jtester.exception.JTesterException;
import org.jtester.module.core.DatabaseModule;
import org.jtester.module.core.helper.ConfigurationHelper;
import org.jtester.module.core.helper.ModulesManager;
import org.jtester.module.database.support.DbSupport;
import org.jtester.module.database.support.DefaultSQLHandler;
import org.jtester.module.database.support.SQLHandler;
import org.jtester.reflector.FieldAccessor;

public class DbSchemaExport {
	private final static Logger log4j = Logger.getLogger(DbSchemaExport.class);

	private DataSourceType type;

	private SchemaExport export;

	private DbSupport dbSupport;

	private final Configuration config;

	public DbSchemaExport() {
		this(DataSourceType.H2DB, "org.h2.Driver", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "public", "sa", "");
	}

	public DbSchemaExport(DataSourceType type, String driver, String url, String schemas, String username,
			String password) {
		this.type = type;
		this.config = config(driver, url, username, password);
		this.export = new SchemaExport(config);

		this.dbSupport = type.getDbSupport();
		SQLHandler sqlHandler = new DefaultSQLHandler(dataSource());
		this.dbSupport.init(ConfigurationHelper.getConfiguration(), sqlHandler, schemas);
	}

	public void export() {
		ConfigurationHelper.disableDbMaintain();
		DatabaseModule module = ModulesManager.getModuleInstance(DatabaseModule.class);
		try {
			FieldAccessor.setFieldValue(module, "updateDatabaseSchemaEnabled", false);
		} catch (Exception e) {
			throw new JTesterException(e);
		}

		log4j.info("call hibernate tool:org.hibernate.tool.hbm2ddl.SchemaExport.create");
		this.export.execute(ConfigurationHelper.isScript(), true, false, true);
	}

	private Configuration config(String driver, String url, String username, String password) {
		Configuration cfg = new AnnotationConfiguration().configure();
		cfg.setProperty("hibernate.show_sql", "false");

		cfg.setProperty("hibernate.connection.username", username);
		cfg.setProperty("hibernate.connection.password", password);

		cfg.setProperty("hibernate.dialect", type.getHibernateDialect());
		cfg.setProperty("hibernate.connection.driver_class", driver);
		cfg.setProperty("hibernate.connection.url", url);
		return cfg;
	}

	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(config.getProperty("hibernate.connection.driver_class"));
		dataSource.setUsername(config.getProperty("hibernate.connection.username"));
		dataSource.setPassword(config.getProperty("hibernate.connection.password"));
		dataSource.setUrl(config.getProperty("hibernate.connection.url"));

		return dataSource;
	}
}

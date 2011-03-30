package org.jtester.module.database;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.jtester.exception.JTesterException;
import org.jtester.module.core.DatabaseModule;
import org.jtester.module.database.support.DbSupport;
import org.jtester.module.database.support.DefaultSQLHandler;
import org.jtester.module.database.support.SQLHandler;
import org.jtester.module.utils.ConfigUtil;
import org.jtester.module.utils.ModuleHelper;
import org.jtester.reflector.ReflectUtil;

public class DbSchemaExport {
	private static Log log = LogFactory.getLog(DbSchemaExport.class);

	private DatabaseType type;

	private SchemaExport export;

	private DbSupport dbSupport;

	public DbSchemaExport() {
		this(DatabaseType.H2DB);
	}

	public DbSchemaExport(DatabaseType type) {
		this.type = type;
		this.export = new SchemaExport(config());

		this.dbSupport = type.getDbSupport();
		SQLHandler sqlHandler = new DefaultSQLHandler(dataSource());
		this.dbSupport.init(ConfigUtil.config(), sqlHandler, type.getSchemas());
	}

	public void export() {
		ConfigUtil.disableDbMaintain();
		DatabaseModule module = ModuleHelper.getInstance().getModulesRepository().getModuleOfType(DatabaseModule.class);
		try {
			ReflectUtil.setFieldValue(module, "updateDatabaseSchemaEnabled", false);
		} catch (Exception e) {
			throw new JTesterException(e);
		}

		log.info("call hibernate tool:org.hibernate.tool.hbm2ddl.SchemaExport.create");
		this.export.execute(ConfigUtil.isScript(), true, false, true);
	}

	public Configuration config() {
		Configuration cfg = new AnnotationConfiguration().configure();
		cfg.setProperty("hibernate.show_sql", "false");

		cfg.setProperty("hibernate.connection.username", type.getUserName());
		cfg.setProperty("hibernate.connection.password", type.getUserPass());

		cfg.setProperty("hibernate.dialect", type.getHibernateDialect());
		cfg.setProperty("hibernate.connection.driver_class", type.getDriveClass());
		cfg.setProperty("hibernate.connection.url", type.getConnUrl());
		return cfg;
	}

	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(type.getDriveClass());
		dataSource.setUsername(type.getUserName());
		dataSource.setPassword(type.getUserPass());
		dataSource.setUrl(type.getConnUrl());

		return dataSource;
	}
}

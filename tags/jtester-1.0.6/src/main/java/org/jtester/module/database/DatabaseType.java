package org.jtester.module.database;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jtester.module.database.support.DbSupport;
import org.jtester.module.database.support.DerbyDbSupport;
import org.jtester.module.database.support.H2DbSupport;
import org.jtester.module.database.support.HsqldbDbSupport;
import org.jtester.module.database.support.MsSqlDbSupport;
import org.jtester.module.database.support.MySqlDbSupport;
import org.jtester.module.database.support.OracleDbSupport;
import org.jtester.module.utils.ConfigUtil;
import org.jtester.utility.StringHelper;

public enum DatabaseType {
	/**
	 * H2Db
	 */
	H2DB("org.h2.Driver", "org.hibernate.dialect.H2Dialect", "public", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "",
			"public") {
		@Override
		public DbSupport getDbSupport() {
			return new H2DbSupport();
		}

		@Override
		public boolean autoExport() {
			return true;
		}

		@Override
		public String getDbUnitDialect() {
			return "h2db";
		}

		@Override
		public boolean isMemoryDB() {
			return true;
		}
	},
	/**
	 * HsqlDb
	 */
	HSQLDB("org.hsqldb.jdbcDriver", "org.hibernate.dialect.HSQLDialect", "public", "jdbc:hsqldb:mem:test", "sa", "",
			"public") {
		@Override
		public DbSupport getDbSupport() {
			return new HsqldbDbSupport();
		}

		@Override
		public boolean autoExport() {
			return true;
		}

		@Override
		public String getDbUnitDialect() {
			return "hsqldb";
		}

		@Override
		public boolean isMemoryDB() {
			return true;
		}
	},
	MYSQL() {
		@Override
		public DbSupport getDbSupport() {
			return new MySqlDbSupport();
		}

		@Override
		public String getDbUnitDialect() {
			return "mysql";
		}
	},
	ORACLE() {
		@Override
		public DbSupport getDbSupport() {
			return new OracleDbSupport();
		}

		@Override
		public String getDbUnitDialect() {
			return "oracle";
		}
	},
	SQLSERVER() {

		@Override
		public DbSupport getDbSupport() {
			return new MsSqlDbSupport();
		}

		@Override
		public String getDbUnitDialect() {
			return "mssql";
		}

	},

	DERBYDB() {
		@Override
		public DbSupport getDbSupport() {
			return new DerbyDbSupport();
		}

		@Override
		public String getDbUnitDialect() {
			return "derby";
		}

	},

	UNSUPPORT() {
		@Override
		public DbSupport getDbSupport() {
			throw new RuntimeException("unsupport database type");
		}

		@Override
		public String getDbUnitDialect() {
			throw new RuntimeException("unsupport database type");
		}
	};

	private static Log log = LogFactory.getLog(DatabaseType.class);

	private String clazz = null;

	private String hibernate_dialect = null;

	private String url = null;

	private String user = null;

	private String pass = null;

	private String infoSchema = null;

	private String schemas = null;

	private DatabaseType() {
	}

	private DatabaseType(String clazz, String hibernate_dialect, String infoSchema, String url, String user,
			String pass, String schema) {
		this.clazz = clazz;
		this.hibernate_dialect = hibernate_dialect;
		this.infoSchema = infoSchema;
		this.url = url;
		this.user = user;
		this.pass = pass;
		this.schemas = schema;
	}

	public String getDriveClass() {
		return this.clazz == null ? ConfigUtil.driverClazzName() : this.clazz;
	}

	public String getHibernateDialect() {
		return this.hibernate_dialect;
	}

	public abstract String getDbUnitDialect();

	public abstract DbSupport getDbSupport();

	public String getInfoSchema() {
		return this.infoSchema;
	}

	public String getConnUrl() {
		return this.url == null ? ConfigUtil.databaseUrl() : this.url;
	}

	public String getUserName() {
		return this.user == null ? ConfigUtil.databaseUserName() : this.user;
	}

	public String getUserPass() {
		return this.pass == null ? ConfigUtil.databasePassword() : this.pass;
	}

	public String getSchemas() {
		return this.schemas == null ? ConfigUtil.property("database.schemaNames") : this.schemas;
	}

	private static DatabaseType type = null;

	/**
	 * 根据jtester.properties中的配置项database.type获得DataSourceType<br>
	 * 此配置只对内存数据库有效
	 * 
	 * @return
	 */
	public static DatabaseType type() {
		if (type != null) {
			return type;
		}
		String typeS = ConfigUtil.databaseType();
		type = type(typeS);
		return type;
	}

	/**
	 * 根据配置查找对应的数据库类型
	 * 
	 * @param type
	 * @return
	 */
	public static DatabaseType type(String type) {
		String typeS = type;
		if (type == null || "".equalsIgnoreCase(type.trim())) {
			typeS = ConfigUtil.databaseType();
		}
		try {
			if (StringHelper.isBlankOrNull(typeS)) {
				throw new RuntimeException("please config property 'database.type'");
			}
			DatabaseType dbType = DatabaseType.valueOf(typeS.toUpperCase());
			ConfigUtil.setDatabaseDialect(dbType);
			if (dbType.isMemoryDB()) {
				ConfigUtil.setMemoryDbConfig(dbType);
			}
			return dbType;
		} catch (Exception e) {
			log.warn(e.getMessage());
			throw new RuntimeException("unknown database type", e);
		}
	}

	public boolean autoExport() {
		return ConfigUtil.autoExport();
	}

	public boolean isMemoryDB() {
		return false;
	}
}

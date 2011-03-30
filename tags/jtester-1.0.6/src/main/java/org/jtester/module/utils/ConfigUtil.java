package org.jtester.module.utils;

import static org.jtester.utility.ReflectionUtils.createInstanceOfType;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jtester.exception.JTesterException;
import org.jtester.module.database.DataSourceFactory;
import org.jtester.module.database.DatabaseType;
import org.jtester.utility.StringHelper;

public class ConfigUtil {
	private static Log logger = LogFactory.getLog(ConfigUtil.class);

	public static final String dbexport_auto = "dbexport.auto";

	public static final String PROPKEY_DATABASE_TYPE = "database.type";

	public static final String PROPKEY_DATASOURCE_DRIVERCLASSNAME = "database.driverClassName";

	public static final String PROPKEY_DATASOURCE_URL = "database.url";

	public static final String PROPKEY_DATASOURCE_USERNAME = "database.userName";

	public static final String PROPKEY_DATASOURCE_PASSWORD = "database.password";

	public static final String DBMAINTAINER_DISABLECONSTRAINTS = "dbMaintainer.disableConstraints.enabled";

	public static final String SPRING_DATASOURCE_NAME = "spring.datasource.name";

	public static final String CONNECT_ONLY_TESTDB = "database.only.testdb.allowing";
	
	public static final String IBATIS_SQLMAP_THROW_EXCEPTION = "ibatis.sqlmap.throw.exception";

	public static final Properties unitilscfg = ModuleHelper.getInstance().getConfiguration();

	public static String property(String key) {
		return unitilscfg.getProperty(key);
	}

	public static boolean boolProperty(String key) {
		String prop = unitilscfg.getProperty(key);
		return "true".equalsIgnoreCase(prop);
	}

	public static int intProperty(String key, int defaultValue) {
		String prop = unitilscfg.getProperty(key);
		try {
			int i = Integer.valueOf(prop);
			return i;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static String driverClazzName() {
		return unitilscfg.getProperty(PROPKEY_DATASOURCE_DRIVERCLASSNAME);
	}

	public static String databaseUrl() {
		return unitilscfg.getProperty(PROPKEY_DATASOURCE_URL);
	}

	public static String databaseUserName() {
		return unitilscfg.getProperty(PROPKEY_DATASOURCE_USERNAME);
	}

	public static String databasePassword() {
		return unitilscfg.getProperty(PROPKEY_DATASOURCE_PASSWORD);
	}

	public static boolean doesDisableConstraints() {
		String disableConstraints = unitilscfg.getProperty(DBMAINTAINER_DISABLECONSTRAINTS);
		return "TRUE".equalsIgnoreCase(disableConstraints);
	}

	/**
	 * 除非显示的声明了database.istest=false，否则jtester认为只能连接测试库
	 * 
	 * @return
	 */
	public static boolean doesOnlyTestDatabase() {
		String onlytest = unitilscfg.getProperty(CONNECT_ONLY_TESTDB);
		return !"FALSE".equalsIgnoreCase(onlytest);
	}

	/**
	 * 判断是否是spring的data source bean name
	 * 
	 * @param beanName
	 * @return
	 */
	public static boolean isSpringDataSourceName(String beanName) {
		String dataSourceName = unitilscfg.getProperty(SPRING_DATASOURCE_NAME);
		return beanName.equals(dataSourceName);
	}

	public static boolean autoExport() {
		String auto = unitilscfg.getProperty(dbexport_auto);
		if (auto != null && auto.equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isScript() {
		String script = unitilscfg.getProperty("dbexport.script");
		if (script != null && script.equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public static String databaseType() {
		String type = System.getProperty(PROPKEY_DATABASE_TYPE);// from vm
		if (!StringHelper.isBlankOrNull(type)) {
			return type;
		}
		type = unitilscfg.getProperty(PROPKEY_DATABASE_TYPE);// from property
		if (type == null) {
			type = "unsupport";
		}
		return type;
	}

	/**
	 * dbfit测试结果文件输出目录
	 * 
	 * @return
	 */
	public static String dbfitDir() {
		String dir = unitilscfg.getProperty("dbfit.dir");
		return StringHelper.isBlankOrNull(dir) ? "target/dbfit" : dir;
	}

	public static Properties config() {
		return unitilscfg;
	}

	public static void disableDbMaintain() {
		// disable dbmaintainer properties
		unitilscfg.setProperty("updateDataBaseSchema.enabled", "false");
		unitilscfg.setProperty("dbMaintainer.dbVersionSource.autoCreateVersionTable", "false");
	}

	public static void setMemoryDbConfig(DatabaseType type) {
		unitilscfg.setProperty("database.driverClassName", type.getDriveClass());
		unitilscfg.setProperty("database.url", type.getConnUrl());
		unitilscfg.setProperty("database.userName", type.getUserName());
		unitilscfg.setProperty("database.password", type.getUserPass());
		unitilscfg.setProperty("database.schemaNames", type.getSchemas());
	}

	public static void setDatabaseDialect(DatabaseType type) {
		unitilscfg.setProperty("database.dialect", type.getDbUnitDialect());
	}

	/**
	 * Retrieves the concrete instance of the class with the given type as
	 * configured by the given <code>Configuration</code>. Tries to retrieve a
	 * specific implementation first (propery key = fully qualified name of the
	 * interface type + '.impl.className.' + implementationDiscriminatorValue).
	 * If this key does not exist, the generally configured instance is
	 * retrieved (same property key without the
	 * implementationDiscriminatorValue).
	 * 
	 * @param type
	 *            The type of the instance
	 * @param configuration
	 *            The configuration containing the necessary properties for
	 *            configuring the instance
	 * @param implementationDiscriminatorValues
	 *            The values that define which specific implementation class
	 *            should be used. This is typically an environment specific
	 *            property, like the DBMS that is used.
	 * @return The configured instance
	 */
	public static <T extends DataSourceFactory> T getConfiguredInstanceOf(Class<? extends T> type,
			Properties configuration, String... implementationDiscriminatorValues) {
		T result = getInstanceOf(type, configuration, implementationDiscriminatorValues);
		result.init(configuration);
		return result;
	}

	/**
	 * Retrieves the concrete instance of the class with the given type as
	 * configured by the given <code>Configuration</code>. Tries to retrieve a
	 * specific implementation first (propery key = fully qualified name of the
	 * interface type + '.impl.className.' + implementationDiscriminatorValue).
	 * If this key does not exist, the generally configured instance is
	 * retrieved (same property key without the
	 * implementationDiscriminatorValue).
	 * 
	 * @param type
	 *            The type of the instance
	 * @param configuration
	 *            The configuration containing the necessary properties for
	 *            configuring the instance
	 * @param implementationDiscriminatorValues
	 *            The values that define which specific implementation class
	 *            should be used. This is typically an environment specific
	 *            property, like the DBMS that is used.
	 * @return The configured instance
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T getInstanceOf(Class<? extends T> type, Properties configuration,
			String... implementationDiscriminatorValues) {
		String implClassName = getConfiguredClassName(type, configuration, implementationDiscriminatorValues);
		logger.debug("Creating instance of " + type + ". Implementation class " + implClassName);
		return (T) createInstanceOfType(implClassName, false);
	}

	/**
	 * Retrieves the class name of the concrete instance of the class with the
	 * given type as configured by the given <code>Configuration</code>. Tries
	 * to retrieve a specific implementation first (propery key = fully
	 * qualified name of the interface type + '.impl.className.' +
	 * implementationDiscriminatorValue). If this key does not exist, the
	 * generally configured instance is retrieved (same property key without the
	 * implementationDiscriminatorValue).
	 * 
	 * @param type
	 *            The type of the instance
	 * @param configuration
	 *            The configuration containing the necessary properties for
	 *            configuring the instance
	 * @param implementationDiscriminatorValues
	 *            The values that define which specific implementation class
	 *            should be used. This is typically an environment specific
	 *            property, like the DBMS that is used.
	 * @return The configured class name
	 */
	public static String getConfiguredClassName(Class<?> type, Properties configuration,
			String... implementationDiscriminatorValues) {
		String propKey = type.getName() + ".implClassName";

		// first try specific instance using the given discriminators
		if (implementationDiscriminatorValues != null) {
			String implementationSpecificPropKey = propKey;
			for (String implementationDiscriminatorValue : implementationDiscriminatorValues) {
				implementationSpecificPropKey += '.' + implementationDiscriminatorValue;
			}
			if (configuration.containsKey(implementationSpecificPropKey)) {
				return PropertyUtils.getString(implementationSpecificPropKey, configuration);
			}
		}

		// specifig not found, try general configured instance
		if (configuration.containsKey(propKey)) {
			return PropertyUtils.getString(propKey, configuration);
		}

		// no configuration found
		throw new JTesterException("Missing configuration for " + propKey);
	}
}

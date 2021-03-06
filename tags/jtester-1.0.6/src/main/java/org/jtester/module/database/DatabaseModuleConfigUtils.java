/*
 * Copyright 2008,  Unitils.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jtester.module.database;

import java.util.Properties;

import org.jtester.module.database.support.SQLHandler;
import org.jtester.module.utils.ConfigUtil;
import org.jtester.module.utils.PropertyUtils;

/**
 * Class containing configuration utility methods specifically for the
 * {@link org.jtester.module.core.DatabaseModule} and related modules
 * 
 * @author Filip Neven
 * @author Tim Ducheyne
 */
public class DatabaseModuleConfigUtils {

	/**
	 * Property key of the SQL dialect of the underlying DBMS implementation
	 */
	public static final String PROPKEY_DATABASE_DIALECT = "database.dialect";

	/**
	 * Retrieves the concrete instance of the class with the given type as
	 * configured by the given <code>Configuration</code>. The concrete instance
	 * must extend the class {@link DatabaseAccessing}.
	 * 
	 * @param <T>
	 *            The type of the DatabaseTask
	 * @param databaseTaskType
	 *            The type of the DatabaseTask, not null
	 * @param configuration
	 *            The config, not null
	 * @param sqlHandler
	 *            The sql handler, not null
	 * @return The configured instance
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T extends DatabaseAccessing> T getConfiguredDatabaseTaskInstance(Class<T> databaseTaskType,
			Properties configuration, SQLHandler sqlHandler) {
		String databaseDialect = PropertyUtils.getString(PROPKEY_DATABASE_DIALECT, configuration);
		DatabaseAccessing instance = ConfigUtil.getInstanceOf(databaseTaskType, configuration, databaseDialect);
		instance.init(configuration, sqlHandler);
		return (T) instance;
	}
}

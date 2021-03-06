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
package org.jtester.module.database.support;

import static ext.jtester.org.apache.commons.dbutils.DbUtils.closeQuietly;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Set;

import org.jtester.exception.JTesterException;

/**
 * Implementation of {@link DbSupport} for a hsqldb database
 * 
 * @author Filip Neven
 * @author Tim Ducheyne
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class HsqldbDbSupport extends DbSupport {

	/**
	 * Creates support for HsqlDb databases.
	 */
	public HsqldbDbSupport() {
		super("hsqldb");
	}

	/**
	 * Returns the names of all tables in the database.
	 * 
	 * @return The names of all tables in the database
	 */
	@Override
	public Set<String> getTableNames() {
		SQLHandler handler = getSQLHandler();
		Set set = handler
				.getItemsAsStringSet("select TABLE_NAME from INFORMATION_SCHEMA.SYSTEM_TABLES where TABLE_TYPE = 'TABLE' AND TABLE_SCHEM = '"
						+ getSchemaName() + "'");
		return set;
	}

	/**
	 * Gets the names of all columns of the given table.
	 * 
	 * @param tableName
	 *            The table, not null
	 * @return The names of the columns of the table with the given name
	 */
	@Override
	public Set<String> getColumnNames(String tableName) {
		SQLHandler handler = getSQLHandler();
		Set set = handler
				.getItemsAsStringSet("select COLUMN_NAME from INFORMATION_SCHEMA.SYSTEM_COLUMNS where TABLE_NAME = '"
						+ tableName + "' AND TABLE_SCHEM = '" + getSchemaName() + "'");
		return set;
	}

	/**
	 * Retrieves the names of all the views in the database schema.
	 * 
	 * @return The names of all views in the database
	 */
	@Override
	public Set<String> getViewNames() {
		SQLHandler handler = getSQLHandler();
		Set set = handler
				.getItemsAsStringSet("select TABLE_NAME from INFORMATION_SCHEMA.SYSTEM_TABLES where TABLE_TYPE = 'VIEW' AND TABLE_SCHEM = '"
						+ getSchemaName() + "'");
		return set;
	}

	/**
	 * Retrieves the names of all the sequences in the database schema.
	 * 
	 * @return The names of all sequences in the database
	 */
	@Override
	public Set<String> getSequenceNames() {
		SQLHandler handler = getSQLHandler();
		Set set = handler
				.getItemsAsStringSet("select SEQUENCE_NAME from INFORMATION_SCHEMA.SYSTEM_SEQUENCES where SEQUENCE_SCHEMA = '"
						+ getSchemaName() + "'");
		return set;
	}

	/**
	 * Retrieves the names of all the triggers in the database schema.
	 * 
	 * @return The names of all triggers in the database
	 */
	@Override
	public Set<String> getTriggerNames() {
		SQLHandler handler = getSQLHandler();
		Set set = handler
				.getItemsAsStringSet("select TRIGGER_NAME from INFORMATION_SCHEMA.SYSTEM_TRIGGERS where TRIGGER_SCHEM = '"
						+ getSchemaName() + "'");
		return set;
	}

	/**
	 * Disables all referential constraints (e.g. foreign keys) on all table in
	 * the schema
	 */
	@Override
	public void disableReferentialConstraints() {
		Connection connection = null;
		Statement queryStatement = null;
		Statement alterStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getSQLHandler().getDataSource().getConnection();
			queryStatement = connection.createStatement();
			alterStatement = connection.createStatement();

			resultSet = queryStatement
					.executeQuery("select TABLE_NAME, CONSTRAINT_NAME from INFORMATION_SCHEMA.SYSTEM_TABLE_CONSTRAINTS where CONSTRAINT_TYPE = 'FOREIGN KEY' AND CONSTRAINT_SCHEMA = '"
							+ getSchemaName() + "'");
			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				String constraintName = resultSet.getString("CONSTRAINT_NAME");
				alterStatement.executeUpdate("alter table " + qualified(tableName) + " drop constraint "
						+ quoted(constraintName));
			}
		} catch (Throwable e) {
			throw new JTesterException(
					"Error while disabling not referential constraints on schema " + getSchemaName(), e);
		} finally {
			closeQuietly(queryStatement);
			closeQuietly(connection, alterStatement, resultSet);
		}
	}

	/**
	 * Disables all value constraints (e.g. not null) on all tables in the
	 * schema
	 */
	@Override
	public void disableValueConstraints() {
		disableCheckAndUniqueConstraints();
		disableNotNullConstraints();
	}

	/**
	 * Disables all check and unique constraints on all tables in the schema
	 */
	protected void disableCheckAndUniqueConstraints() {
		Connection connection = null;
		Statement queryStatement = null;
		Statement alterStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getSQLHandler().getDataSource().getConnection();
			queryStatement = connection.createStatement();
			alterStatement = connection.createStatement();

			resultSet = queryStatement
					.executeQuery("select TABLE_NAME, CONSTRAINT_NAME from INFORMATION_SCHEMA.SYSTEM_TABLE_CONSTRAINTS where CONSTRAINT_TYPE IN ('CHECK', 'UNIQUE') AND CONSTRAINT_SCHEMA = '"
							+ getSchemaName() + "'");
			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				String constraintName = resultSet.getString("CONSTRAINT_NAME");
				alterStatement.executeUpdate("alter table " + qualified(tableName) + " drop constraint "
						+ quoted(constraintName));
			}
		} catch (Throwable e) {
			throw new JTesterException("Error while disabling check and unique constraints on schema "
					+ getSchemaName(), e);
		} finally {
			closeQuietly(queryStatement);
			closeQuietly(connection, alterStatement, resultSet);
		}
	}

	/**
	 * Disables all not null constraints on all tables in the schema
	 */
	protected void disableNotNullConstraints() {
		Connection connection = null;
		Statement queryStatement = null;
		Statement alterStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getSQLHandler().getDataSource().getConnection();
			queryStatement = connection.createStatement();
			alterStatement = connection.createStatement();

			// Do not remove PK constraints
			resultSet = queryStatement
					.executeQuery("select col.TABLE_NAME, col.COLUMN_NAME from INFORMATION_SCHEMA.SYSTEM_COLUMNS col where col.IS_NULLABLE = 'NO' and col.TABLE_SCHEM = '"
							+ getSchemaName()
							+ "' "
							+ "AND NOT EXISTS ( select COLUMN_NAME from INFORMATION_SCHEMA.SYSTEM_PRIMARYKEYS pk where pk.TABLE_NAME = col.TABLE_NAME and pk.COLUMN_NAME = col.COLUMN_NAME and pk.TABLE_SCHEM = '"
							+ getSchemaName() + "' )");
			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				String columnName = resultSet.getString("COLUMN_NAME");
				alterStatement.executeUpdate("alter table " + qualified(tableName) + " alter column "
						+ quoted(columnName) + " set null");
			}
		} catch (Throwable e) {
			throw new JTesterException("Error while disabling not null constraints on schema " + getSchemaName(), e);
		} finally {
			closeQuietly(queryStatement);
			closeQuietly(connection, alterStatement, resultSet);
		}
	}

	/**
	 * Returns the value of the sequence with the given name.
	 * <p/>
	 * Note: this can have the side-effect of increasing the sequence value.
	 * 
	 * @param sequenceName
	 *            The sequence, not null
	 * @return The value of the sequence with the given name
	 */
	@Override
	public long getSequenceValue(String sequenceName) {
		SQLHandler handler = getSQLHandler();
		long item = handler
				.getItemAsLong("select START_WITH from INFORMATION_SCHEMA.SYSTEM_SEQUENCES where SEQUENCE_SCHEMA = '"
						+ getSchemaName() + "' and SEQUENCE_NAME = '" + sequenceName + "'");
		return item;
	}

	/**
	 * Sets the next value of the sequence with the given sequence name to the
	 * given sequence value.
	 * 
	 * @param sequenceName
	 *            The sequence, not null
	 * @param newSequenceValue
	 *            The value to set
	 */
	@Override
	public void incrementSequenceToValue(String sequenceName, long newSequenceValue) {
		SQLHandler handler = getSQLHandler();
		handler.executeUpdate("alter sequence " + qualified(sequenceName) + " restart with " + newSequenceValue);
	}

	/**
	 * Gets the names of all identity columns of the given table.
	 * <p/>
	 * todo check, at this moment the PK columns are returned
	 * 
	 * @param tableName
	 *            The table, not null
	 * @return The names of the identity columns of the table with the given
	 *         name
	 */
	@Override
	public Set<String> getIdentityColumnNames(String tableName) {
		SQLHandler handler = getSQLHandler();
		Set set = handler
				.getItemsAsStringSet("select COLUMN_NAME from INFORMATION_SCHEMA.SYSTEM_PRIMARYKEYS where TABLE_NAME = '"
						+ tableName + "' AND TABLE_SCHEM = '" + getSchemaName() + "'");
		return set;
	}

	/**
	 * Increments the identity value for the specified identity column on the
	 * specified table to the given value.
	 * 
	 * @param tableName
	 *            The table with the identity column, not null
	 * @param identityColumnName
	 *            The column, not null
	 * @param identityValue
	 *            The new value
	 */
	@Override
	public void incrementIdentityColumnToValue(String tableName, String identityColumnName, long identityValue) {
		SQLHandler handler = getSQLHandler();
		handler.executeUpdate("alter table " + qualified(tableName) + " alter column " + quoted(identityColumnName)
				+ " RESTART WITH " + identityValue);
	}

	/**
	 * Sequences are supported.
	 * 
	 * @return True
	 */
	@Override
	public boolean supportsSequences() {
		return true;
	}

	/**
	 * Triggers are supported.
	 * 
	 * @return True
	 */
	@Override
	public boolean supportsTriggers() {
		return true;
	}

	/**
	 * Identity columns are supported.
	 * 
	 * @return True
	 */
	@Override
	public boolean supportsIdentityColumns() {
		return true;
	}

	/**
	 * Cascade are supported.
	 * 
	 * @return True
	 */
	@Override
	public boolean supportsCascade() {
		return true;
	}
}
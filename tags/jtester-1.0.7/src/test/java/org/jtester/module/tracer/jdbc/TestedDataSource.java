package org.jtester.module.tracer.jdbc;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jtester.module.database.DatabaseType;

public class TestedDataSource extends BasicDataSource {
	protected static Log log = LogFactory.getLog(TestedDataSource.class);

	private DatabaseType type;

	public TestedDataSource() {
		this.type = DatabaseType.type();

		this.setUsername(type.getUserName());
		this.setDriverClassName(type.getDriveClass());
		this.setUrl(type.getConnUrl());
		this.setPassword(type.getUserPass());
	}
}

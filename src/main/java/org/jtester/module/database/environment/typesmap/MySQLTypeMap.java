package org.jtester.module.database.environment.typesmap;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({ "rawtypes" })
public interface MySQLTypeMap {
	Map<String, Class> java = new HashMap<String, Class>() {
		private static final long serialVersionUID = -2101070752077610108L;

		{
			this.put("VARCHAR", String.class);
			this.put("CHAR", String.class);
			this.put("TEXT", String.class);
			this.put("TINYINT", Integer.class);
			this.put("SMALLINT", Integer.class);
			this.put("MEDIUMINT", Integer.class);
			this.put("INT", Integer.class);
			this.put("INTEGER", Integer.class);
			this.put("BIGINT", Long.class);
			this.put("INTEGER UNSIGNED", Long.class);
			this.put("INT UNSIGNED", Long.class);
			this.put("FLOAT", Float.class);
			this.put("DOUBLE", Double.class);
			this.put("DECIMAL", BigDecimal.class);
			this.put("DEC", BigDecimal.class);
			this.put("DATE", java.sql.Date.class);
			this.put("TIMESTAMP", java.sql.Timestamp.class);
			this.put("DATETIME", java.sql.Timestamp.class);
			this.put("BIT", Boolean.class);
			// cobar extended
			this.put("CHARACTER VARYING", String.class);
			this.put("NUMERIC", Double.class);
			this.put("DOUBLE PRECISION", Double.class);
			this.put("CHARACTER", String.class);
			this.put("TIME", java.sql.Time.class);
			this.put("BOOLEAN", Boolean.class);
			this.put("TIMESTAMP WITH TIME ZONE", java.sql.Date.class);
			this.put("TIMESTAMP WITHOUT TIME ZONE", java.sql.Date.class);
			this.put("TIME WITH TIME ZONE", java.sql.Time.class);
			this.put("TIME WITHOUT TIME ZONE", java.sql.Time.class);
		}
	};

	Map<String, Integer> sql = new HashMap<String, Integer>() {
		private static final long serialVersionUID = -2101070752077610108L;

		{
			this.put("VARCHAR", Types.VARCHAR);
			this.put("CHAR", Types.VARCHAR);
			this.put("TEXT", Types.VARCHAR);
			this.put("TINYINT", Types.INTEGER);
			this.put("SMALLINT", Types.INTEGER);
			this.put("MEDIUMINT", Types.INTEGER);
			this.put("INT", Types.INTEGER);
			this.put("INTEGER", Types.INTEGER);
			this.put("BIGINT", Types.BIGINT);
			this.put("INTEGER UNSIGNED", Types.BIGINT);
			this.put("INT UNSIGNED", Types.BIGINT);
			this.put("FLOAT", Types.FLOAT);
			this.put("DOUBLE", Types.DOUBLE);
			this.put("DECIMAL", Types.NUMERIC);
			this.put("DEC", Types.NUMERIC);
			this.put("DATE", Types.DATE);
			this.put("TIMESTAMP", Types.TIMESTAMP);
			this.put("DATETIME", Types.TIMESTAMP);
			this.put("BIT", Types.BOOLEAN);
			// cobar extended
			this.put("CHARACTER VARYING", Types.VARCHAR);
			this.put("NUMERIC", Types.NUMERIC);
			this.put("DOUBLE PRECISION", Types.DOUBLE);
			this.put("CHARACTER", Types.CHAR);
			this.put("TIME", Types.TIME);
			this.put("BOOLEAN", Types.BIT);
			this.put("TIMESTAMP WITH TIME ZONE", Types.DATE);
			this.put("TIMESTAMP WITHOUT TIME ZONE", Types.DATE);
			this.put("TIME WITH TIME ZONE", Types.TIME);
			this.put("TIME WITHOUT TIME ZONE", Types.TIME);
		}
	};
}

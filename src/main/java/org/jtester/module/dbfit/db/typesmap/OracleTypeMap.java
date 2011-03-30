package org.jtester.module.dbfit.db.typesmap;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import oracle.jdbc.driver.OracleTypes;

public interface OracleTypeMap {
	Map<String, Class<?>> java = new HashMap<String, Class<?>>() {
		private static final long serialVersionUID = 4142728734422012716L;

		{
			this.put("VARCHAR", String.class);
			this.put("VARCHAR2", String.class);
			this.put("NVARCHAR2", String.class);
			this.put("CHAR", String.class);
			this.put("NCHAR", String.class);
			this.put("CLOB", String.class);
			this.put("ROWID", String.class);
			this.put("BINARY_INTEGER", BigDecimal.class);
			this.put("NUMBER", BigDecimal.class);
			this.put("FLOAT", BigDecimal.class);
			this.put("DATE", Timestamp.class);
			this.put("TIMESTAMP", Timestamp.class);
			this.put("REF", ResultSet.class);
		}
	};

	Map<String, Integer> sql = new HashMap<String, Integer>() {
		private static final long serialVersionUID = 4459866943787154263L;

		{
			this.put("VARCHAR", Types.VARCHAR);
			this.put("VARCHAR2", Types.VARCHAR);
			this.put("NVARCHAR2", Types.VARCHAR);
			this.put("CHAR", Types.VARCHAR);
			this.put("NCHAR", Types.VARCHAR);
			this.put("CLOB", Types.VARCHAR);
			this.put("ROWID", Types.VARCHAR);
			this.put("BINARY_INTEGER", Types.NUMERIC);
			this.put("NUMBER", Types.NUMERIC);
			this.put("FLOAT", Types.NUMERIC);
			this.put("DATE", Types.TIMESTAMP);
			this.put("TIMESTAMP", Types.TIMESTAMP);
			this.put("REF", OracleTypes.CURSOR);
		}
	};
}

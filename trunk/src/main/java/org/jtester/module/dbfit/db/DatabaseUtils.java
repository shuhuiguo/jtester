package org.jtester.module.dbfit.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseUtils {
	/**
	 * 关闭数据库statement句柄
	 */
	public static void closeStatement(Statement st) {
		if (st == null) {
			return;
		}
		try {
			st.close();
			st = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs == null) {
			return;
		}
		try {
			rs.close();
			rs = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

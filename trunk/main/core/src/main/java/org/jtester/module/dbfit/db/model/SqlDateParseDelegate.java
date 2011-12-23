package org.jtester.module.dbfit.db.model;

import java.util.Date;

import org.jtester.helper.DateHelper;

public class SqlDateParseDelegate {

	public static Object parse(String s) throws Exception {
		Date date = DateHelper.parse(s);
		return new java.sql.Date(date.getTime());
	}
}

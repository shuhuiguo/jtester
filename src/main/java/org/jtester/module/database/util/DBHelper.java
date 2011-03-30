package org.jtester.module.database.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jtester.exception.NoSuchFieldRuntimeException;
import org.jtester.module.dbfit.db.model.DbParameterAccessor;
import org.jtester.reflector.FieldAccessor;
import org.jtester.reflector.helper.ClazzHelper;
import org.jtester.utility.StringHelper;

@SuppressWarnings({ "rawtypes", "unchecked" })
public final class DBHelper {
	private final static Logger log4j = Logger.getLogger(DBHelper.class);

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

	/**
	 * 将ResultSet的当前行转换为Map数据返回
	 * 
	 * @param rs
	 *            数据库结果ResultSet
	 * @param rsmd
	 *            ResultSet的meta数据
	 * @return
	 * @throws SQLException
	 */
	public static Map getMapFromResult(ResultSet rs, ResultSetMetaData rsmd) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = rsmd.getColumnCount();
		for (int index = 1; index <= count; index++) {
			String key = getCamelFieldName(rsmd, index);
			Object o = rs.getObject(index);
			Object value = DbParameterAccessor.normaliseValue(o);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 将ResultSet的转换为Map List数据返回
	 * 
	 * @param rs
	 * @param rsmd
	 * @return
	 * @throws SQLException
	 */
	public static List<Map> getListMapFromResult(ResultSet rs, ResultSetMetaData rsmd) throws SQLException {
		List<Map> list = new ArrayList<Map>();
		while (rs.next()) {
			Map map = getMapFromResult(rs, rsmd);
			list.add(map);
		}

		return list;
	}

	/**
	 * 将ResultSet的当前行转换为PoJo数据返回
	 * 
	 * @param <T>
	 * @param rs
	 * @param rsmd
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	public static <T> T getPoJoFromResult(ResultSet rs, ResultSetMetaData rsmd, Class<T> clazz) throws SQLException {
		T pojo = ClazzHelper.createInstanceOfType(clazz, true);
		int count = rsmd.getColumnCount();
		for (int index = 1; index <= count; index++) {
			String key = getCamelFieldName(rsmd, index);

			try {
				FieldAccessor accessor = new FieldAccessor(key, clazz);
				Object o = rs.getObject(index);
				Object value = DbParameterAccessor.normaliseValue(o);
				accessor.set(pojo, value);
			} catch (NoSuchFieldRuntimeException e) {
				log4j.warn("set pojo property errro: " + e.getMessage());
			}
		}
		return pojo;
	}

	/**
	 * 将ResultSet的当前行转换为PoJo列表数据返回
	 * 
	 * @param <T>
	 * @param rs
	 * @param rsmd
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	public static <T> List<T> getListPoJoFromResult(ResultSet rs, ResultSetMetaData rsmd, Class<T> clazz)
			throws SQLException {
		List list = new ArrayList();
		while (rs.next()) {
			T o = getPoJoFromResult(rs, rsmd, clazz);
			list.add(o);
		}
		return list;
	}

	/**
	 * 过滤非法字符后<br>
	 * 返回数据库查询结果集第index个字段的camel名称
	 * 
	 * @param rsmd
	 * @param index
	 * @return
	 * @throws SQLException
	 */
	private static String getCamelFieldName(ResultSetMetaData rsmd, int index) throws SQLException {
		String key = rsmd.getColumnName(index);
		key = key.replaceAll("[^a-zA-Z0-9]", " ");
		String result = StringHelper.camel(key);
		return result;
	}

	/**
	 * 分解sql语句为单条可执行的sql语句集合,并过滤注释
	 * 
	 * @param content
	 *            多条sql语句(可能包含注释，换行等信息)
	 * @return
	 */
	public static String[] parseSQL(String content) {
		char[] chars = content.toCharArray();
		List<String> statements = new ArrayList<String>();

		StamentStatus status = StamentStatus.NORMAL;
		StringBuffer buff = new StringBuffer();
		for (int index = 0; index < chars.length; index++) {
			char ch = chars[index];
			char next = '\0';
			switch (status) {
			case SINGLE_NOTE:
				if (ch == '\n' || ch == '\r') {
					buff.append(' ');
					status = StamentStatus.NORMAL;
				}
				break;
			case MULTI_NOTE:
				next = (index == chars.length - 1) ? '/' : chars[index + 1];
				if (ch == '*' && next == '/') {
					index++;
					status = StamentStatus.NORMAL;
				}
				break;
			case SINGLE_QUOTATION:
				buff.append(ch);
				if (ch == '\'') {
					status = StamentStatus.NORMAL;
				}
				break;
			case DOUBLE_QUOTATION:
				buff.append(ch);
				if (ch == '"') {
					status = StamentStatus.NORMAL;
				}
				break;
			case NORMAL:
				next = (index == chars.length - 1) ? ';' : chars[index + 1];
				if (ch == '-' && next == '-') {
					index++;
					status = StamentStatus.SINGLE_NOTE;
				} else if (ch == '/' && next == '*') {
					index++;
					status = StamentStatus.MULTI_NOTE;
				} else if (ch == '\'') {
					buff.append(ch);
					status = StamentStatus.SINGLE_QUOTATION;
				} else if (ch == '"') {
					buff.append(ch);
					status = StamentStatus.DOUBLE_QUOTATION;
				} else if (ch == ';') {
					String statement = buff.toString().trim();
					if ("".equals(statement) == false) {
						statements.add(statement);
					}
					buff = new StringBuffer();
				} else if (ch == '\n' || ch == '\r') {
					buff.append(' ');
				} else {
					buff.append(ch);
				}
				break;
			}
		}
		String statement = buff.toString().trim();
		if ("".equals(statement) == false) {
			statements.add(statement);
		}

		String[] stmts = new String[statements.size()];
		statements.toArray(stmts);
		return stmts;
	}
}

package org.jtester.module.database.environment;

import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jtester.utility.PrimitiveHelper;

public class TableMeta {
	/**
	 * 表名
	 */
	String tableName;

	Map<String, ColumnMeta> columns;

	public TableMeta(String table, ResultSetMetaData meta) throws Exception {
		this.tableName = table;
		this.columns = new HashMap<String, ColumnMeta>();
		int count = meta.getColumnCount();
		for (int index = 1; index <= count; index++) {
			ColumnMeta columnMeta = new ColumnMeta();

			columnMeta.columnName = meta.getColumnName(index);
			columnMeta.size = meta.getColumnDisplaySize(index);
			columnMeta.typeName = meta.getColumnTypeName(index);
			columnMeta.isNullable = meta.isNullable(index) == 1;

			this.columns.put(columnMeta.columnName, columnMeta);
		}
	}

	public Map<String, ColumnMeta> getColumns() {
		return columns;
	}

	/**
	 * 填充在data中未指定字段的默认值
	 * 
	 * @param data
	 */
	public void fillData(Map<String, Object> data, DBEnvironment dbEnvironment) {
		Set<String> keys = data.keySet();
		for (String key : this.columns.keySet()) {
			if (keys.contains(key)) {
				continue;
			}
			ColumnMeta column = this.columns.get(key);
			Object value = column.getDefaultValue(dbEnvironment);
			data.put(key, value);
		}
	}

	public static class ColumnMeta {
		/**
		 * 字段名称
		 */
		String columnName;
		/**
		 * 字段大小
		 */
		int size;
		/**
		 * 字段类型名称
		 */
		String typeName;
		/**
		 * 是否允许null?
		 */
		boolean isNullable;

		/**
		 * 默认值
		 */
		Object defaultValue;

		@Override
		public String toString() {
			return "[columnName=" + columnName + ", size=" + size + ", typeName=" + typeName + ", isNullable="
					+ isNullable + ", defaultValue=" + defaultValue + "]";
		}

		@SuppressWarnings("rawtypes")
		public Object getDefaultValue(DBEnvironment dbEnvironment) {
			if (this.isNullable()) {
				return null;
			}
			Class clazz = dbEnvironment.getJavaClass(typeName);
			if (clazz == String.class) {
				return "jtester".subSequence(0, size > 7 ? 7 : size);
			}
			if (Number.class.isAssignableFrom(clazz)) {
				if (PrimitiveHelper.isPrimitiveTypeOrRelative(clazz)) {
					return PrimitiveHelper.getPrimitiveDefaultValue(clazz);
				} else if (BigDecimal.class.isAssignableFrom(clazz)) {
					return BigDecimal.valueOf(0);
				}
			}
			if (Date.class.isAssignableFrom(clazz)) {
				return new Date();
			}

			return null;
		}

		public String getColumnName() {
			return columnName;
		}

		public int getSize() {
			return size;
		}

		public String getTypeName() {
			return typeName;
		}

		public boolean isNullable() {
			return isNullable;
		}

		public Object getDefaultValue() {
			return defaultValue;
		}
	}
}

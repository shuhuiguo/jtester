package org.jtester.module.database;

import java.util.Map;

import org.jtester.testng.JTester.DataIterator;

public interface IDataSet {
	/**
	 * 清空数据表
	 * 
	 * @return
	 */
	IDataSet clean();

	/**
	 * 设置要插入数据的字段名称
	 * 
	 * @param column
	 * @param more
	 * @return
	 */
	IDataSet columns(String column, String... more);

	/**
	 * 设置要插入数据的字段名称
	 * 
	 * @param columns
	 * @return
	 */
	IDataSet columns(String[] columns);

	/**
	 * 根据字段插入数据
	 * 
	 * @param columns
	 * @param datas
	 * @return
	 */
	IDataSet insert(String[] columns, DataIterator datas);

	/**
	 * 根据预设字段或Map的key插入数据
	 * 
	 * @param datas
	 * @return
	 */
	IDataSet insert(DataIterator datas);

	/**
	 * 根据预设的字段，插入数据
	 * 
	 * @param data
	 * @param more
	 * @return
	 */
	IDataSet insert(Object data, Object... more);

	/**
	 * 根据预设的字段，插入数据
	 * 
	 * @param datas
	 * @param more
	 * @return
	 */
	IDataSet insert(Object[] datas, Object[]... more);

	/**
	 * 根据Map的key（表字段）插入数据
	 * 
	 * @param data
	 * @param more
	 * @return
	 */
	IDataSet insert(Map<String, Object> data, Map<String, Object>... more);
}

package org.jtester.module.database.datagenerator;

import java.util.Map;

public abstract class AbastractDataGenerator {
	private Map<String, Object> dataMap;

	/**
	 * 生成第n个数据<br>
	 * index计数从0开始
	 * 
	 * @param index
	 * @return
	 */
	public abstract Object generate(int index);

	public Object value(String key) {
		if (this.dataMap == null) {
			throw new RuntimeException("the data map can't be null.");
		}
		if (this.dataMap.containsKey(key) == false) {
			throw new RuntimeException("unexist the key[" + key + "] of data map.");
		}
		return this.dataMap.get(key);
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * 循环遍历objects对象列表
	 * 
	 * @param objects
	 * @return
	 */
	public static AbastractDataGenerator repeat(Object... objects) {
		return new RepeatDataGenerator(objects);
	}

	/**
	 * 生成随机对象
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static AbastractDataGenerator random(Class type) {
		return new RandomDataGenerator(type);
	}

	/**
	 * 步进生成数据
	 * 
	 * @param from
	 * @param step
	 * @return
	 */
	public static AbastractDataGenerator increase(Number from, Number step) {
		return new IncreaseDataGenerator(from, step);
	}
}

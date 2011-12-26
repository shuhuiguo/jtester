package org.jtester.beans;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.jtester.helper.ArrayHelper;

@SuppressWarnings("serial")
public class DataMap extends LinkedHashMap<String, Object> {
	/**
	 * put(String,Object[])的快捷写法
	 * 
	 * @param key
	 * @param value1
	 * @param value2
	 * @param more
	 */
	public void put(String key, Object value1, Object value2, Object... more) {
		List<Object> list = new ArrayList<Object>();
		list.add(value1);
		list.add(value2);
		for (Object item : more) {
			list.add(item);
		}
		super.put(key, list.toArray(new Object[0]));
	}

	/**
	 * 根据要插入数据的数量count和数据集合datas，分解出count条待插入的数据集
	 * 
	 * @param count
	 * @param datas
	 * @return
	 */
	public static List<DataMap> parseMapList(int count, DataMap datas) {
		List<DataMap> list = new ArrayList<DataMap>();
		for (int index = 0; index < count; index++) {
			DataMap data = new DataMap();
			for (String key : datas.keySet()) {
				Object dataGenerator = datas.get(key);
				Object value = getObjectFromDataGenerator(data, dataGenerator, index);
				data.put(key, value);
			}
			list.add(data);
		}
		return list;
	}

	private static Object getObjectFromDataGenerator(DataMap dataMap, Object dataGenerator, int index) {
		if (ArrayHelper.isCollOrArray(dataGenerator)) {
			Object[] oa = ArrayHelper.toArray(dataGenerator);
			int count = oa.length;
			Object value = index < count ? oa[index] : oa[count - 1];
			return value;
		} else if (dataGenerator instanceof DataGenerator) {
			DataGenerator generator = (DataGenerator) dataGenerator;
			generator.setDataMap(dataMap);
			return generator.generate(index);
		} else {
			return dataGenerator;
		}
	}
}

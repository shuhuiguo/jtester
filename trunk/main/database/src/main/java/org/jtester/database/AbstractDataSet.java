package org.jtester.database;

import java.util.ArrayList;
import java.util.List;

import org.jtester.beans.DataMap;
import org.jtester.json.JSON;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractDataSet {
	protected List<DataMap> datas = new ArrayList<DataMap>();

	public AbstractDataSet() {
	}

	public void data(int count, DataMap datas) {
		List list = DataMap.parseMapList(count, datas);
		this.datas.addAll(list);
	}

	public void data(DataMap data) {
		this.datas.add(data);
	}

	public void data(String json) {
		DataMap data = JSON.toObject(json, DataMap.class);
		this.datas.add(data);
	}
}

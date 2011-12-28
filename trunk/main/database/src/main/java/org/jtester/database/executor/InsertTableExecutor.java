package org.jtester.database.executor;

import java.util.ArrayList;
import java.util.List;

import org.jtester.beans.DataMap;
import org.jtester.database.operator.InsertOp;

public class InsertTableExecutor extends TableExecutor {
	private List<DataMap> datas;

	public InsertTableExecutor(String xmlFile, String table) {
		super(xmlFile, table);
	}

	public InsertTableExecutor(String xmlFile, String table, List<DataMap> datas) {
		super(xmlFile, table);
		this.datas = datas;
	}

	public void setDatas(List<DataMap> datas) {
		this.datas = datas;
	}

	public void setDatas(int count, DataMap datas) {
		this.datas = DataMap.parseMapList(count, datas);
	}

	public void setDatas(DataMap data) {
		this.datas = new ArrayList<DataMap>();
		this.datas.add(data);
	}

	@Override
	public void execute() {
		try {
			for (DataMap data : datas) {
				InsertOp.insert(table, data);
			}
		} catch (Throwable e) {
			throw new RuntimeException("", e);
		}
	}
}

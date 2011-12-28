package org.jtester.database.executor;

import java.util.ArrayList;
import java.util.List;

import org.jtester.beans.DataMap;
import org.jtester.database.operator.InsertOp;
import org.jtester.exception.ExceptionWrapper;

public class InsertTableExecutor extends TableExecutor {
	private List<DataMap> datas;

	public InsertTableExecutor(String table) {
		super(table);
	}

	public InsertTableExecutor(String table, List<DataMap> datas) {
		super(table);
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
		} catch (Exception e) {
			throw ExceptionWrapper.getUndeclaredThrowableExceptionCaused(e);
		}
	}
}

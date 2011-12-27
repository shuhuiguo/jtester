package org.jtester.database.table;

import java.util.ArrayList;
import java.util.List;

import org.jtester.beans.DataMap;
import org.jtester.module.database.environment.DBEnvironment;

public class InsertTableExecutor extends TableExecutor {
	private List<DataMap> datas;

	public InsertTableExecutor(List<DataMap> datas) {
		this.datas = datas;
	}

	public InsertTableExecutor(int count, DataMap datas) {
		this.datas = DataMap.parseMapList(count, datas);
	}

	public InsertTableExecutor(DataMap data) {
		this.datas = new ArrayList<DataMap>();
		this.datas.add(data);
	}

	@Override
	public void execute(DBEnvironment dbEnvironment) {
		// TODO Auto-generated method stub

	}
}

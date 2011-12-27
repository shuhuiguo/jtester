package org.jtester.database.table;

import java.util.ArrayList;
import java.util.List;

import org.jtester.beans.DataMap;
import org.jtester.module.database.environment.DBEnvironment;

public class InsertTable extends TableExecutor {
	private List<DataMap> datas;

	public InsertTable(List<DataMap> datas) {
		this.datas = datas;
	}

	public InsertTable(int count, DataMap datas) {
		this.datas = DataMap.parseMapList(count, datas);
	}

	public InsertTable(DataMap data) {
		this.datas = new ArrayList<DataMap>();
		this.datas.add(data);
	}

	@Override
	public void execute(DBEnvironment dbEnvironment) {
		// TODO Auto-generated method stub

	}
}

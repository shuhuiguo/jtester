package org.jtester.database;

import org.jtester.beans.AbstractDataSet;
import org.jtester.module.database.dbop.InsertOp;

public class DataSet extends AbstractDataSet {
	/**
	 * 插入列表中的数据集<br>
	 * 插入完毕后列表不做清空，方便重用
	 * 
	 * @param table
	 */
	public void insert(String table) {
		for (org.jtester.beans.DataMap map : this.datas) {
			InsertOp.insert(table, map);
		}
	}
}

package org.jtester.module.database;

import org.jtester.module.ICore;
import org.jtester.module.database.dbop.DBOperator;
import org.jtester.module.database.dbop.IDBOperator;
import org.jtester.module.database.dbop.InsertOp;
import org.jtester.tools.datagen.AbstractDataSet;

public interface IDatabase extends ICore {

	final IDBOperator db = new DBOperator();

	public static abstract class DataSet extends AbstractDataSet {
		/**
		 * 插入列表中的数据集<br>
		 * 插入完毕后列表不做清空，方便重用
		 * 
		 * @param table
		 */
		public void insert(String table) {
			for (DataMap map : this.datas) {
				InsertOp.insert(table, map);
			}
		}
	}

	public final static class EmptyDataSet extends DataSet {
	}
}

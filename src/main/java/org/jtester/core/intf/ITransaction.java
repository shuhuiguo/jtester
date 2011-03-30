package org.jtester.core.intf;

import org.jtester.module.database.transaction.TestedObjectTransaction;

public interface ITransaction {
	/**
	 * 获得当前测试类的事务管理
	 * 
	 * @return
	 */
	TestedObjectTransaction getTestedObjectTransaction();
}

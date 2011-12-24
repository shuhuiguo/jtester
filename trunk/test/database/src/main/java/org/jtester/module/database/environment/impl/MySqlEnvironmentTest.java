package org.jtester.module.database.environment.impl;

import org.jtester.IAssertion;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.DbFit.AUTO;
import org.junit.Test;

public class MySqlEnvironmentTest implements IAssertion {

	/**
	 * 在dbfit文件中测试mysql bigint数据类型
	 */
	@Test
	@DbFit(auto = AUTO.AUTO)
	public void testBigInt() {

	}
}

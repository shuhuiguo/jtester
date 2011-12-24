package org.jtester.module.database.environment.impl;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.DbFit.AUTO;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "mysql")
public class MySqlEnvironmentTest extends JTester {

	/**
	 * 在dbfit文件中测试mysql bigint数据类型
	 */
	@DbFit(auto = AUTO.AUTO)
	public void testBigInt() {

	}
}

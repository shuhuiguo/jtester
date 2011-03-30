package org.jtester.fit.dbfit.environment;

import org.jtester.testng.JTester;
import org.jtester.unitils.dbfit.DbFit;
import org.jtester.unitils.dbfit.DbFit.AUTO;
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

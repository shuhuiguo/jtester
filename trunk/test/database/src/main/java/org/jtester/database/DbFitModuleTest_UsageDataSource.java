package org.jtester.database;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.DbFit.AUTO;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class DbFitModuleTest_UsageDataSource extends JTester {

	@DbFit(dataSource = "jtester_another", auto = AUTO.AUTO)
	public void testDbFit() {

	}
}

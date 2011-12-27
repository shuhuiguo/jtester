package org.jtester.database;

import org.jtester.IAssertion;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.DbFit.AUTO;
import org.junit.Test;

public class DbFitModuleTest_UsageDataSource implements IAssertion {
	@Test
	@DbFit(dataSource = "jtester_another", auto = AUTO.AUTO)
	public void testDbFit() {

	}
}

package org.jtester.fit.dbfit;

import org.jtester.testng.JTester;
import org.jtester.unitils.dbfit.DbFit;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class DbFixtureTest_ForPlugin extends JTester {

	@DbFit(when = "DbFixtureTest_ForPlugin.nullValueInsert.wiki")
	public void nullValueInsert() {
	}
}

package org.jtester.module.dbfit.db.fixture;

import org.jtester.testng.JTester;
import org.jtester.unitils.dbfit.DbFit;
import org.jtester.unitils.dbfit.DbFit.AUTO;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class InsertFixtureTest extends JTester {

	@DbFit(auto = AUTO.AUTO)
	public void testKeyGenerateFeedback() {

	}

	@DbFit(auto = AUTO.AUTO)
	public void testKeyGenerateFeedback_oracle() {

	}
}

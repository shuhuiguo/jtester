package org.jtester.module.dbfit.db.fixture;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.DbFit.AUTO;
import org.jtester.testng.JTester;
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

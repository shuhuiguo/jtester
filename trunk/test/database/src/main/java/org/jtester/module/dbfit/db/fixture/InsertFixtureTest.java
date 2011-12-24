package org.jtester.module.dbfit.db.fixture;

import org.jtester.IAssertion;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.DbFit.AUTO;
import org.junit.Test;

public class InsertFixtureTest implements IAssertion {
	@Test
	@DbFit(auto = AUTO.AUTO)
	public void testKeyGenerateFeedback() {

	}

	@Test
	@DbFit(auto = AUTO.AUTO)
	public void testKeyGenerateFeedback_oracle() {

	}
}

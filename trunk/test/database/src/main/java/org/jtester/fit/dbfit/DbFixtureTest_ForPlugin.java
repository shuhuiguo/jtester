package org.jtester.fit.dbfit;

import org.jtester.IAssertion;
import org.jtester.annotations.DbFit;
import org.junit.Test;

public class DbFixtureTest_ForPlugin implements IAssertion {
	@Test
	@DbFit(when = "DbFixtureTest_ForPlugin.nullValueInsert.wiki")
	public void nullValueInsert() {
	}
}

package org.jtester.fit.dbfit;

import org.jtester.IAssertion;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.DbFit.AUTO;
import org.junit.Test;

@DbFit(auto = AUTO.AUTO)
public class BigIntegerTest implements IAssertion {

	@Test
	public void testInsertBigInteger() {

	}

	@Test
	public void testOracleBinDecimal() {

	}
}

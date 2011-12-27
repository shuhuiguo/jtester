package org.jtester.database;

import org.jtester.IAssertion;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.FitVar;
import org.jtester.exception.DbFitException;
import org.junit.Test;

public class DbFitModuleTest_WithSQL implements IAssertion {
	@Test
	@DbFit(when = "org/jtester/module/core/data/DbFitModuleTest_WithSQL/testDbFit.when.sql", then = "data/DbFitModuleTest_WithSQL/testDbFit.then.wiki")
	public void testDbFit() {
	}

	@Test
	@DbFit(when = "data/DbFitModuleTest_WithSQL/testDbFit.when.sql", then = "data/DbFitModuleTest_WithSQL/testDbFit.then.wiki")
	public void testDbFit2() {
	}

	@Test
	@DbFit(vars = { @FitVar(key = "firstName", value = "darui.wu") })
	public void userSymbols() {
	}

	@Test(expected = DbFitException.class)
	public void testRunSqlError() {

	}
}

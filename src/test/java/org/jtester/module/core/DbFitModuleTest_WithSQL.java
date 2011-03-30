package org.jtester.module.core;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.FitVar;
import org.jtester.exception.DbFitException;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester" })
public class DbFitModuleTest_WithSQL extends JTester {

	@DbFit(when = "org/jtester/module/core/data/DbFitModuleTest_WithSQL/testDbFit.when.sql", then = "data/DbFitModuleTest_WithSQL/testDbFit.then.wiki")
	public void testDbFit() {
	}

	@DbFit(when = "data/DbFitModuleTest_WithSQL/testDbFit.when.sql", then = "data/DbFitModuleTest_WithSQL/testDbFit.then.wiki")
	public void testDbFit2() {
	}

	@DbFit(vars = { @FitVar(key = "firstName", value = "darui.wu") })
	public void userSymbols() {
	}

	@Test(expectedExceptions = DbFitException.class)
	public void testRunSqlError() {

	}
}

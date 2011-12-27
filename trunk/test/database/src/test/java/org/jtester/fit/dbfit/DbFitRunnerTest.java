package org.jtester.fit.dbfit;

import java.util.HashMap;
import java.util.Map;

import org.jtester.IAssertion;
import org.jtester.IDatabase;
import org.jtester.annotations.DbFit;
import org.jtester.database.DbFitModuleTest;
import org.jtester.module.dbfit.DbFitRunner;
import org.junit.Test;

public class DbFitRunnerTest implements IAssertion, IDatabase {
	@Test
	@DbFit(when = "org/jtester/fit/dbfit/SimpleQuery_Init.wiki")
	public void runTest_HasName() throws Exception {
		DbFitRunner tdd = new DbFitRunner("test-output");

		tdd.runDbFitTest(DbFitRunnerTest.class, "SimpleQuery.wiki");
	}

	@Test
	@DbFit(when = "org/jtester/fit/dbfit/SimpleQuery_Init.wiki")
	public void runTest() throws Exception {
		fit.runDbFit(null, "org/jtester/fit/dbfit/SimpleQuery.wiki");
	}

	@Test
	public void testPrepareData() {
		Map<String, String> symbols = new HashMap<String, String>();
		symbols.put("name", "darui.wu");
		symbols.put("myid", "2");

		fit.runDbFit(DbFitModuleTest.class, symbols, "DbFitModuleTest.exactFitVar.when.wiki",
				"DbFitModuleTest.exactFitVar.then.wiki");
	}

	/**
	 * 测试wiki文件query中使用变量
	 * 
	 * @throws Exception
	 */
	@Test
	@DbFit(when = "org/jtester/fit/dbfit/SimpleQuery_Init.wiki")
	public void test_WikiFileHasVariable() throws Exception {
		fit.setSymbol("first_name1", "aaa");
		fit.setSymbol("first_name2", "bbb");
		fit.runDbFit(null, "org/jtester/fit/dbfit/SimpleQuery_userVar.wiki");
	}
}

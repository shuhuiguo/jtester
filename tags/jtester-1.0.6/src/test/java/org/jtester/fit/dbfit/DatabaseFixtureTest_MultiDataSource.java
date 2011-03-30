package org.jtester.fit.dbfit;

import org.jtester.testng.JTester;
import org.jtester.unitils.dbfit.DbFit;
import org.jtester.utility.JTesterLogger;
import org.testng.annotations.Test;

/**
 * 在dbfit文件中的多数据源连接
 * 
 * @author darui.wudr
 * 
 */
@Test(groups = "jtester")
public class DatabaseFixtureTest_MultiDataSource extends JTester {

	@DbFit(when = "DatabaseFixtureTest_MultiDataSource.wiki")
	public void multiDataSource() {
		JTesterLogger.info("test");
	}

	@DbFit(when = "DatabaseFixtureTest_connectFromFile.wiki")
	public void connectFromFile() {
		JTesterLogger.info("test");
	}

	@DbFit(when = "DatabaseFixtureTest_connectFromFile_oracle.wiki")
	@Test(groups = "broken-install")
	public void connectFromFile_orcle() {
		JTesterLogger.info("test");
	}
}

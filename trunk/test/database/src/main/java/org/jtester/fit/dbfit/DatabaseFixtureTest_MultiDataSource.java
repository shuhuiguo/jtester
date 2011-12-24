package org.jtester.fit.dbfit;

import org.apache.log4j.Logger;
import org.jtester.IAssertion;
import org.jtester.annotations.DbFit;
import org.junit.Test;

/**
 * 在dbfit文件中的多数据源连接
 * 
 * @author darui.wudr
 * 
 */
public class DatabaseFixtureTest_MultiDataSource implements IAssertion {

	private final static Logger log4j = Logger.getLogger(DatabaseFixtureTest_MultiDataSource.class);

	@Test
	@DbFit(when = "DatabaseFixtureTest_MultiDataSource.wiki")
	public void multiDataSource() {
		log4j.info("test");
	}

	@Test
	@DbFit(when = "DatabaseFixtureTest_connectFromFile.wiki")
	public void connectFromFile() {
		log4j.info("test");
	}

	@DbFit(when = "DatabaseFixtureTest_connectFromFile_oracle.wiki")
	@Test
	public void connectFromFile_orcle() {
		log4j.info("test");
	}
}

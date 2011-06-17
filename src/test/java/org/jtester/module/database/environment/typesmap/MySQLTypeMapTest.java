package org.jtester.module.database.environment.typesmap;

import org.jtester.annotations.DbFit;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class MySQLTypeMapTest extends JTester {

	@DbFit(when = "mysqltype.when.wiki", then = "mysqltype.then.wiki")
	public void testMySqlType() {

	}
}

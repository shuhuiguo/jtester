package org.jtester.module.database.environment.typesmap;

import org.jtester.IAssertion;
import org.jtester.annotations.DbFit;
import org.junit.Test;

public class MySQLTypeMapTest implements IAssertion {
	@Test
	@DbFit(when = "mysqltype.when.wiki", then = "mysqltype.then.wiki")
	public void testMySqlType() {

	}
}

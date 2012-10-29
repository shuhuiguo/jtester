package org.jtester.module.database.dbop;

import java.io.File;

import org.jtester.junit.JTester;
import org.jtester.module.database.IDatabase;
import org.junit.Test;

public class SqlSetTest extends JTester implements IDatabase {

	@Test
	public void testReadFrom() {
		final String file = System.getProperty("user.dir")
				+ "/src/test/resources/org/jtester/module/database/dbop/sql-demo.sql";
		db.cleanTable("tdd_user").execute(new SqlSet() {
			{
				this.readFrom(new File(file));
			}
		});

		db.table("tdd_user").count().isEqualTo(2);
	}
}

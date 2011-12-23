package org.jtester.module.database.dbop;

import java.io.File;

import org.jtester.annotations.DbFit;
import org.jtester.matcher.property.reflection.EqMode;
import org.jtester.module.database.bean.TddUser;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "database" })
public class DBOperatorTest extends JTester {

	@Test
	@DbFit(when = "data/TableOpTest/testClean.when.wiki")
	public void testClean() {
		db.table("tdd_user").count().isEqualTo(3);
		db.table("jtester_user").count().isEqualTo(1);

		db.cleanTable("tdd_user", "jtester_user").commit();
		db.table("tdd_user").count().isEqualTo(0);
		db.table("jtester_user").count().isEqualTo(0);
	}

	@Test
	@DbFit(when = "data/TableOpTest/testClean.when.wiki")
	public void testQueryList() {
		db.table("tdd_user").count().isEqualTo(3);

		db.table("tdd_user").query()
				.propertyEq("first_name", new String[] { "2323", "asdf", "adfe" }, EqMode.IGNORE_ORDER);
	}

	@Test
	@DbFit(when = "data/TableOpTest/testClean.when.wiki")
	public void testCount() {
		db.table("tdd_user").count().isEqualTo(3);
	}

	@Test
	@DbFit(when = "data/TableOpTest/testClean.when.wiki")
	public void testQueryListToList() {
		db.table("tdd_user").queryList(TddUser.class)
				.propertyEq("firstName", new String[] { "2323", "asdf", "adfe" }, EqMode.IGNORE_ORDER);
	}

	@Test
	@DbFit(when = "data/TableOpTest/testClean.when.wiki")
	public void testExecute() {
		db.table("tdd_user").count().isEqualTo(3);
		db.execute("delete from tdd_user").commit();
		db.table("tdd_user").count().isEqualTo(0);
	}

	@Test
	public void testExecute_UseSqlSet() {
		db.table("tdd_user").clean().count().isEqualTo(0);
		db.execute(new SqlSet() {
			{
				sql("insert tdd_user(id, first_name) values(1, \"darui.wu\")");
				sql("insert tdd_user(id, first_name) values(2, \"jobs.he\")");
			}
		}).commit();
		db.table("tdd_user").count().isEqualTo(2);
	}

	public void testExecute_FromFile() {
		final String file = System.getProperty("user.dir")
				+ "/src/main/resources/org/jtester/module/database/dbop/sql-demo.sql";
		db.cleanTable("tdd_user").execute(new File(file));

		db.table("tdd_user").count().isEqualTo(2);
	}
}

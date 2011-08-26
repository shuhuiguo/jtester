package org.jtester.module.database;

import org.jtester.annotations.DbFit;
import org.jtester.hamcrest.matcher.property.reflection.EqMode;
import org.jtester.module.database.bean.TddUser;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "database" })
public class DBUtilityTest extends JTester {

	@Test
	@DbFit(when = "data/DBUtilityTest/testClean.when.wiki")
	public void testClean() {
		db.count("tdd_user").isEqualTo(3);
		db.count("jtester_user").isEqualTo(1);

		db.clean("tdd_user", "jtester_user").commit();
		db.count("tdd_user").isEqualTo(0);
		db.count("jtester_user").isEqualTo(0);
	}

	@Test
	@DbFit(when = "data/DBUtilityTest/testClean.when.wiki")
	public void testQuery() {
		db.count("tdd_user").isEqualTo(3);

		db.query("tdd_user").propertyEq("first_name", new String[] { "2323", "asdf", "adfe" }, EqMode.IGNORE_ORDER);
	}

	@Test
	@DbFit(when = "data/DBUtilityTest/testClean.when.wiki")
	public void testCount() {
		db.count("tdd_user").isEqualTo(3);
	}

	@Test
	@DbFit(when = "data/DBUtilityTest/testClean.when.wiki")
	public void testQueryToList() {
		db.query("tdd_user", TddUser.class).propertyEq("firstName", new String[] { "2323", "asdf", "adfe" },
				EqMode.IGNORE_ORDER);
	}
}

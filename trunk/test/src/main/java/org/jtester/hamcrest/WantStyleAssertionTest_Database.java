package org.jtester.hamcrest;

import org.jtester.annotations.DbFit;
import org.jtester.beans.DataMap;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings({ "serial", "unchecked" })
@Test(groups = { "jtester", "assertion", "database" })
public class WantStyleAssertionTest_Database extends JTester {

	@DbFit(when = "data/WantStyleAssertionTest_Database/testDatabase.when.wiki")
	@Test
	public void testDatabase() {
		db.query("select id,first_name,last_name from tdd_user").reflectionEqMap(toList(new DataMap() {
			{
				this.put("id", 1);
				this.put("first_name", "darui");
			}
		}, new DataMap() {
			{
				this.put("id", 2);
				this.put("last_name", "he");
			}
		}));
	}
}

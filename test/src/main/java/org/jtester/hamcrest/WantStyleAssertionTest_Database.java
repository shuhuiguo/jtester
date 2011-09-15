package org.jtester.hamcrest;

import java.util.HashMap;

import org.jtester.annotations.DbFit;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
@Test(groups = { "jtester", "assertion", "database" })
public class WantStyleAssertionTest_Database extends JTester {

	@DbFit(when = "data/WantStyleAssertionTest_Database/testDatabase.when.wiki")
	@Test
	public void testDatabase() {
		db.query("select id,first_name,last_name from tdd_user").reflectionEqMap(toList(new HashMap() {
			{
				this.put("id", 1);
				this.put("first_name", "darui");
			}
		}, new HashMap() {
			{
				this.put("id", 2);
				this.put("last_name", "he");
			}
		}));
	}
}

package org.jtester.module.database.utility;

import org.jtester.junit.JTester;
import org.jtester.module.database.IDatabase;
import org.jtester.tools.commons.ListHelper;
import org.junit.Test;

@SuppressWarnings({ "serial", "unchecked" })
public class WantStyleAssertionTest_Database extends JTester implements IDatabase {

	@Test
	public void testDatabase() {
		db.table("tdd_user").clean().insert(2, new DataMap() {
			{
				this.put("id", "1", "2");
				this.put("first_name", "darui", "jobs");
				this.put("last_name", "wu", "he");
			}
		});

		db.query("select id,first_name,last_name from tdd_user").reflectionEqMap(ListHelper.toList(new DataMap() {
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

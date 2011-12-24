package org.jtester.module.dbfit.db.fixture;

import org.jtester.IAssertion;
import org.jtester.IDatabase;
import org.jtester.annotations.DbFit;
import org.jtester.beans.DataMap;
import org.junit.Test;

@SuppressWarnings("serial")
public class QueryFixtureTest implements IAssertion, IDatabase {
	@Test
	@DbFit(then = "testQueryFixture.then.wiki")
	public void testQueryFixture() {
		db.table("demo_big_int_id").clean().insert(new DataMap() {
			{
				put("id", 123456);
				put("is_delete", "Y");
			}
		});
		db.query("select * from demo_big_int_id where id=123456").reflectionEqMap(new DataMap() {
			{
				put("id", 123456);
				put("is_delete", "Y");
			}
		});

		db.table("demo_big_int_id").query().reflectionEqMap(new DataMap() {
			{
				put("id", 123456);
				put("is_delete", "Y");
			}
		});
	}
}

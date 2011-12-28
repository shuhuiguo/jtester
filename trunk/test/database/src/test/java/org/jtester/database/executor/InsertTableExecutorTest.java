package org.jtester.database.executor;

import org.jtester.IAssertion;
import org.jtester.IDatabase;
import org.jtester.beans.DataMap;
import org.junit.Test;

@SuppressWarnings("serial")
public class InsertTableExecutorTest implements IAssertion, IDatabase {

	@Test
	public void testExecute() {
		db.cleanTable("tdd_user").executeXML("data/InsertTableExecutorTest/testInsert.xml");
		db.table("tdd_user").query().propertyEqMap(2, new DataMap() {
			{
				this.put("id", 1, 2);
				this.put("first_name", "bbbb", "dddd");
			}
		});
	}
}

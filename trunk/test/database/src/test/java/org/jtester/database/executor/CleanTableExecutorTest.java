package org.jtester.database.executor;

import org.jtester.IAssertion;
import org.jtester.IDatabase;
import org.jtester.beans.DataMap;
import org.junit.Test;

@SuppressWarnings("serial")
public class CleanTableExecutorTest implements IAssertion, IDatabase {

	@Test
	public void testExecute() {
		db.table("tdd_user").insert(new DataMap() {
			{
				this.put("id", "1");
				this.put("first_name", "zzzz");
			}
		});
		db.executeXML("data/CleanTableExecutorTest/testCleanExecutor.xml").table("tdd_user").count().isEqualTo(0);
	}
}

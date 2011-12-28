package org.jtester.database.executor;

import org.jtester.IAssertion;
import org.jtester.IDatabase;
import org.jtester.beans.DataMap;
import org.junit.Test;

@SuppressWarnings("serial")
public class QueryTableExecutorTest implements IAssertion, IDatabase {

	@Test
	public void testExecute() {
		db.table("tdd_user").clean().insert(3, new DataMap() {
			{
				this.put("id", "1", "2", "3");
				this.put("first_name", "zzzz", "aaaa", "tttt");
			}
		});
		db.executeXML("data/QueryTableExecutorTest/testQuery.xml");
	}
}

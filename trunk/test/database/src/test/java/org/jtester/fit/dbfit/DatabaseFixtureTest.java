package org.jtester.fit.dbfit;

import org.jtester.IAssertion;
import org.jtester.annotations.DbFit;
import org.junit.Test;

public class DatabaseFixtureTest implements IAssertion {
	@Test
	@DbFit(when = "DatabaseFixtureTest.execute_success.when.wiki")
	public void execute() {
	}

	@Test
	@DbFit(when = "DatabaseFixtureTest.delete.when.wiki", then = "DatabaseFixtureTest.delete.then.wiki")
	public void delete() {

	}

	@Test
	@DbFit(when = "DatabaseFixtureTest.execute_bug_v090.when.wiki")
	public void execute_bug_v090() {

	}

	@Test
	@DbFit(when = "DatabaseFixtureTest.setDateTimeFormat.when.wiki")
	public void setDateTimeFormat() {

	}

	@Test
	@DbFit(when = "DatabaseFixtureTest.userAtDateVar.wiki")
	public void userAtDateVar() {

	}

	@Test
	@DbFit(when = "DatabaseFixtureTest.storeQuery.wiki")
	public void storeQuery() {

	}

	@Test
	@DbFit(when = "DatabaseFixtureTest.insertSpace.wiki")
	public void insertSpace() {

	}
}

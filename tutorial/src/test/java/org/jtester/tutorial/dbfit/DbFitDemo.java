package org.jtester.tutorial.dbfit;

import org.jtester.annotations.DbFit;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

public class DbFitDemo extends JTester {

	@DbFit
	@Test
	public void demo() {

	}
}

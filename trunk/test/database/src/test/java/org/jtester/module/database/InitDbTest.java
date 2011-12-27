package org.jtester.module.database;

import org.jtester.IAssertion;
import org.jtester.annotations.SpringApplicationContext;
import org.junit.Test;

@SpringApplicationContext({ "classpath:/org/jtester/fortest/hibernate/project-placeholder.xml",
		"classpath:/org/jtester/fortest/hibernate/project-datasource.xml" })
public class InitDbTest implements IAssertion {

	@Test
	public void testInitDb() {

	}
}

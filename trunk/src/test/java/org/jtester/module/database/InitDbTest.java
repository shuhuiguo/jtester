package org.jtester.module.database;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;

@SpringApplicationContext( { "classpath:/org/jtester/fortest/hibernate/project-placeholder.xml",
		"classpath:/org/jtester/fortest/hibernate/project-datasource.xml" })
@Test(groups = "jtester")
public class InitDbTest extends JTester {

	@Test
	public void testInitDb() {

	}
}

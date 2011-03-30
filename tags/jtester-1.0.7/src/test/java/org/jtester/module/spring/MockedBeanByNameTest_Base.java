package org.jtester.module.spring;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "mockbean" })
@SpringApplicationContext( { "org/jtester/fortest/spring/beans.xml",
		"org/jtester/fortest/spring/data-source.xml" })
public class MockedBeanByNameTest_Base extends JTester {

}

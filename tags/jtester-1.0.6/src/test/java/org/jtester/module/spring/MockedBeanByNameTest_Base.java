package org.jtester.module.spring;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;

@Test(groups = { "jtester", "mockbean" })
@SpringApplicationContext( { "org/jtester/fortest/spring/beans.xml",
		"org/jtester/fortest/spring/data-source.xml" })
public class MockedBeanByNameTest_Base extends JTester {

}

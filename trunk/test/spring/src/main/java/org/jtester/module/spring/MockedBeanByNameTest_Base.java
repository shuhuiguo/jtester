package org.jtester.module.spring;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "mockbean" })
@SpringApplicationContext( { "org/jtester/module/spring/testedbeans/xml/beans.xml",
		"org/jtester/module/spring/testedbeans/xml/data-source.xml" })
public class MockedBeanByNameTest_Base implements IAssertion {

}

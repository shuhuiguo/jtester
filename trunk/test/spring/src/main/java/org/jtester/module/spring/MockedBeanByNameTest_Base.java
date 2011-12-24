package org.jtester.module.spring;

import org.jtester.ISpring;
import org.jtester.annotations.SpringApplicationContext;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/beans.xml",
		"org/jtester/module/spring/testedbeans/xml/data-source.xml" })
public class MockedBeanByNameTest_Base implements ISpring {

}

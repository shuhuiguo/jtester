package org.jtester.module.jmockit.mockbug;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;

@SpringApplicationContext({ "org/jtester/module/jmockit/mockbug/sayhello.xml" })
@Test(groups = "for-test")
public class JMockitMockManageBaseTest extends JTester {

}

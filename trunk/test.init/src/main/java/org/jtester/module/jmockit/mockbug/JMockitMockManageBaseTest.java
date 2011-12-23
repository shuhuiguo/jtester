package org.jtester.module.jmockit.mockbug;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SpringApplicationContext({ "org/jtester/module/jmockit/mockbug/sayhello.xml" })
@Test(groups = "for-test")
public class JMockitMockManageBaseTest extends JTester {

}

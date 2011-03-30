package org.jtester.module.core.helper;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

//@SpringApplicationContext({ "classpath:org/jtester/fortest/spring/beans.xml",
//		"classpath:org/jtester/fortest/spring/data-source.xml" })
@Test(groups = "debug")
public class DatabaseModuleHelperTest extends JTester {
	/**
	 * 测试数据库的disabled功能
	 */

	public void testDisableConstraints() {
		// DatabaseUnitils.disableConstraints();
	}
}

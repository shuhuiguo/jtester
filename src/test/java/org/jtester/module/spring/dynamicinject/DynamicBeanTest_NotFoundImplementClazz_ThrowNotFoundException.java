package org.jtester.module.spring.dynamicinject;

import org.jtester.exception.JTesterException;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.jtester.unitils.spring.AutoBeanInject;
import org.jtester.unitils.spring.AutoBeanInject.BeanMap;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

/**
 * @Scene :使用@AutoBeanInject 来自动注入spring bean，但是无法查找到属性的实现类<br>
 *        抛出异常
 * @author darui.wudr
 * 
 */
@SpringApplicationContext({ "org/jtester/fortest/spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl") }, ignoreNotFound = false)
@Test(groups = { "jtester" })
public class DynamicBeanTest_NotFoundImplementClazz_ThrowNotFoundException extends JTester {
	@SpringBeanByName
	UserService userService;

	@Test(expectedExceptions = JTesterException.class)
	public void throwNotFoundException_测试AutoBeanInject找不到属性的实现类时_抛出初始化spring异常() {

	}

	@Test(expectedExceptions = JTesterException.class)
	public void throwNotFoundException_测试JTester的BeforeClass异常会导致该类中所有测试方法失败_但不会skip所有的测试() {

	}
}

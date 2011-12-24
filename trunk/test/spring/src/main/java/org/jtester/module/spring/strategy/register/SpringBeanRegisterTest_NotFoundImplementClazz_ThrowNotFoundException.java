package org.jtester.module.spring.strategy.register;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

/**
 * @Scene :使用@AutoBeanInject 来自动注入spring bean，但是无法查找到属性的实现类<br>
 *        抛出异常
 * @author darui.wudr
 * 
 */
@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl") }, ignoreNotFound = false)
@Test(groups = { "jtester" })
public class SpringBeanRegisterTest_NotFoundImplementClazz_ThrowNotFoundException implements IAssertion {
	@SpringBeanByName
	UserService userService;

	@Test(expectedExceptions = RuntimeException.class)
	public void throwNotFoundException_测试AutoBeanInject找不到属性的实现类时_抛出初始化spring异常() {

	}

	@Test(expectedExceptions = RuntimeException.class)
	public void throwNotFoundException_测试JTester的BeforeClass异常会导致该类中所有测试方法失败_但不会skip所有的测试() {

	}
}

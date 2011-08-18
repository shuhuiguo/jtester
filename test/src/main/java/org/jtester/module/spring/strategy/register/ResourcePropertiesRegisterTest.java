package org.jtester.module.spring.strategy.register;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.fortest.service.UserService;
import org.jtester.module.spring.testedbeans.resource.UserServiceResourceImpl;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/data-source.xml",
		"org/jtester/module/spring/testedbeans/xml/annotation-config.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*", impl = "**.*Impl") })
public class ResourcePropertiesRegisterTest extends JTester {

	@SpringBeanByName(claz = UserServiceResourceImpl.class)
	UserService userService;

	@Test(description = "测试@Resource属性的自动注入")
	public void testRegisterProperties() {
		want.object(userService).notNull();
		Object bean1 = spring.getBean("userDao");
		want.object(bean1).notNull();
		Object bean2 = reflector.getField(userService, "userDao");
		want.object(bean2).notNull().same(bean1);
	}
}

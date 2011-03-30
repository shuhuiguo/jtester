package org.jtester.module.spring.dynamicinject;

import mockit.NonStrict;

import org.jtester.fortest.service.UserAnotherDao;
import org.jtester.fortest.service.UserDao;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.jtester.unitils.spring.AutoBeanInject;
import org.jtester.unitils.spring.AutoBeanInject.BeanMap;
import org.jtester.unitils.spring.SpringBeanFor;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

/**
 * @Scene :使用@AutoBeanInject 来自动注入spring bean，但是无法查找到属性的实现类<br>
 *        忽略错误，改属性的bean不注入到spring容器
 * @author darui.wudr
 * 
 */
@SpringApplicationContext({ "org/jtester/fortest/spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl") })
@Test(groups = "jtester")
public class DynamicBeanTest_NotFoundImplementClazz extends JTester {
	@SpringBeanByName
	UserService userService;

	@SpringBeanFor
	@NonStrict
	UserAnotherDao userAnotherDao;

	public void getSpringBean_测试AutoBeanInject找不到属性的实现类时_不注入该springbean() {
		want.object(userService).notNull();

		UserDao userDao = reflector.getField(userService, "userDao");
		want.object(userDao).isNull();

		UserAnotherDao userAnotherDao = reflector.getField(userService, "userAnotherDao");
		want.object(userAnotherDao).notNull();
	}
}

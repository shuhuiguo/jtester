package org.jtester.module.spring.strategy.register;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

/**
 * 验证多个表达符合接口类型时获取实现
 * 
 * @author darui.wudr
 * 
 */
@SpringApplicationContext( { "org/jtester/module/spring/testedbeans/xml/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.impl.*DaoImpl"), /** <br> */
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
@Test(groups = "jtester")
public class SpringBeanRegisterTest_MultiPattern extends JTester {
	@SpringBeanByName
	private UserService userService;

	public void checkUserAnotherDao_NotFound() {
		Object userDao = spring.getBean("userAnotherDao");
		want.object(userDao).notNull();
	}

	public void checkUserServiceBean() {
		want.object(userService).notNull();
		Object o = spring.getBean("userService");
		want.object(o).same(userService);
	}
}

package org.jtester.module.spring.dynamicinject;

import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.jtester.unitils.spring.AutoBeanInject;
import org.jtester.unitils.spring.AutoBeanInject.BeanMap;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

/**
 * 验证多个表达符合接口类型时获取实现
 * 
 * @author darui.wudr
 * 
 */
@SpringApplicationContext( { "org/jtester/fortest/spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.impl.*DaoImpl"), /** <br> */
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
@Test(groups = "jtester")
public class DynamicBeanTest_MultiPattern extends JTester {
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

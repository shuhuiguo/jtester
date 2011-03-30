package org.jtester.module.spring.dynamicinject;

import org.jtester.fortest.service.UserAnotherDao;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.jtester.unitils.spring.AutoBeanInject;
import org.jtester.unitils.spring.AutoBeanInject.BeanMap;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SpringApplicationContext({ "org/jtester/fortest/spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
@Test(groups = "jtester")
public class SpringBeanRegisterTest extends JTester {
	@SpringBeanByName
	UserService userService;

	@SpringBeanByName
	UserAnotherDao userAnotherDao;

	public void getSpringBean() {
		want.object(userService).notNull();
		Object o = spring.getBean("userAnotherDao");
		want.object(o).same(userAnotherDao);

		Object userDao = spring.getBean("userDao");
		want.object(userDao).notNull();
	}

	/**
	 * 测试深度嵌套的setProperty() 时，bean的自动注入
	 */
	public void callCascadedDao() {
		this.userAnotherDao.callCascadedDao();
	}

	/**
	 * 测试自动注入spring bean的时候调用spring init method
	 */
	public void testSpringInitMethod() {
		int springinit = (Integer)reflector.getField(userAnotherDao, "springinit");
		want.number(springinit).isEqualTo(100);
	}
}

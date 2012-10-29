package org.jtester.module.spring.strategy.register;

import org.jtester.fortest.service.UserAnotherDao;
import org.jtester.fortest.service.UserServiceNoIntf;
import org.jtester.junit.JTester;
import org.jtester.module.database.IDatabase;
import org.jtester.module.spring.ISpring;
import org.jtester.module.spring.annotations.AutoBeanInject;
import org.jtester.module.spring.annotations.AutoBeanInject.BeanMap;
import org.jtester.module.spring.annotations.SpringContext;
import org.jtester.module.spring.annotations.SpringBeanByName;
import org.junit.Test;

@SpringContext({ "org/jtester/module/spring/testedbeans/xml/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
public class SpringBeanRegisterTest_UseImpl extends JTester implements IDatabase, ISpring {
	@SpringBeanByName
	private UserServiceNoIntf userService;

	@SpringBeanByName
	private UserAnotherDao userAnotherDao;

	@Test
	public void getSpringBean() {
		want.object(userService).notNull();
		Object o = spring.getBean("userAnotherDao");
		want.object(o).same(userAnotherDao);

		Object userDao = spring.getBean("userDao");
		want.object(userDao).notNull();
	}
}

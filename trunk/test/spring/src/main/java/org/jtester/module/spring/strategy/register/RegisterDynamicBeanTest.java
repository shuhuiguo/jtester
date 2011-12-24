package org.jtester.module.spring.strategy.register;

import org.jtester.ISpring;
import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fortest.service.UserAnotherDao;
import org.jtester.fortest.service.UserService;
import org.junit.Test;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
public class RegisterDynamicBeanTest implements ISpring {
	@SpringBeanByName
	protected UserService userService;

	@SpringBeanByName
	protected UserAnotherDao userAnotherDao;

	@Test
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
	@Test
	public void callCascadedDao() {
		this.userAnotherDao.callCascadedDao();
	}

	/**
	 * 测试自动注入spring bean的时候调用spring init method
	 */
	@Test
	public void testSpringInitMethod() {
		int springinit = (Integer) reflector.getField(userAnotherDao, "springinit");
		want.number(springinit).isEqualTo(100);
	}
}

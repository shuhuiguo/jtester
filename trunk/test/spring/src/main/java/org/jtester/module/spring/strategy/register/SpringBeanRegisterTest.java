package org.jtester.module.spring.strategy.register;

import org.jtester.ISpring;
import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.Property;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.fortest.service.UserAnotherDaoImpl;
import org.jtester.fortest.service.UserService;
import org.junit.Test;

@SpringApplicationContext({ "org/jtester/module/spring/testedbeans/xml/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
		@BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
public class SpringBeanRegisterTest implements ISpring {
	@SpringBeanByName(properties = {
			@Property(name = "userAnotherDao", ref = "userRefDao", clazz = UserAnotherDaoImpl.class),
			@Property(name = "myName", value = "I am test") })
	private UserService userService;

	@Test
	public void testRegister() {
		Object bean = spring.getBean("userRefDao");
		want.object(bean).notNull();

		Object ref = reflector.getField(userService, "userAnotherDao");
		want.object(ref).notNull().same(bean);
	}

	@Test
	public void testRegister2() {
		String value = reflector.getField(userService, "myName");
		want.string(value).isEqualTo("I am test");
	}
}

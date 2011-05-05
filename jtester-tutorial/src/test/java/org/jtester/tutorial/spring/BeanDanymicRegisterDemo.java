package org.jtester.tutorial.spring;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.jtester.tutorial.biz.service.CustomerService;
import org.testng.annotations.Test;

@Test(description = "动态注册spring bean演示")
@SpringApplicationContext({ "spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*", impl = "**.*Impl"),// <br>
		@BeanMap(intf = "**.*", impl = "**.impl.*Impl") })
public class BeanDanymicRegisterDemo extends JTester {
	/**
	 * 此时的@SpringBeanByName还扮演了另外一重功能，自动注册的bean入口
	 */
	@SpringBeanByName
	CustomerService customerService;

	@Test(description = "演示动态注册spring bean功能")
	public void testDanymicRegister() {
		want.object(customerService).notNull();
		String result = this.customerService.doNothing();
		want.string(result).start("this is a service");
	}
}

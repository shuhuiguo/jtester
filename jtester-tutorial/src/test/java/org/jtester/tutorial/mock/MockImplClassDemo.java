package org.jtester.tutorial.mock;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.jtester.tutorial.biz.service.CustomerService;
import org.jtester.tutorial.biz.service.irregular.CustomerServiceIrregularImpl;
import org.testng.annotations.Test;

@SpringApplicationContext({ "spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*", impl = "**.*Impl"),// <br>
		@BeanMap(intf = "**.*", impl = "**.impl.*Impl") })
public class MockImplClassDemo extends JTester {
	@SpringBeanByName(claz = CustomerServiceIrregularImpl.class)
	CustomerService customerService;

	@Test(description = "演示如何静态的mock一个实现类的方法")
	public void testMockUserDaoImpl() {
		

		String result = this.customerService.doNothing();
		want.string(result).isEqualTo("ddd");
	}

}

package org.jtester.tutorial.spring;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.jtester.tutorial.biz.service.CustomerService;
import org.testng.annotations.Test;

@Test(description = "基本spring加载case")
@SpringApplicationContext({ "spring/data-source.xml", "spring/biz-service.xml" })
public class SpringContextTest extends JTester {

	@SpringBeanByName
	CustomerService customerService;

	@Test(description = "测试spring容器正常启动，其bean被注入到测试类中")
	public void testSpringBeanByName() {
		want.object(customerService).notNull();
	}
}

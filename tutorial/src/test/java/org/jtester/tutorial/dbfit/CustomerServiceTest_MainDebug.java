package org.jtester.tutorial.dbfit;

import org.jtester.core.IJTester;
import org.jtester.tutorial.biz.model.Customer;
import org.jtester.tutorial.biz.service.CustomerService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomerServiceTest_MainDebug implements IJTester {
	public static void main(String[] args) throws Exception {
		// fit.execute("delete from customer", "commit");
		// 初始化spring环境
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"classpath:spring/data-source.xml", "classpath:spring/biz-service.xml" });
		CustomerService customerService = (CustomerService) context.getBean("customerService");

		// 准备数据
		customerService.newCustomer(new Customer("darui.wu", "my address"));

		// 开始测试
		Customer customer = customerService.findCustomerByName("darui.wu");

		// 将消息打印出来肉眼验证
		System.out.println(customer.getName());
	}
}

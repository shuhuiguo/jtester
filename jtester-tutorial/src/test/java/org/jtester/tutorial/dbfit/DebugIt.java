package org.jtester.tutorial.dbfit;

import java.util.List;

import org.jtester.module.dbfit.DbFitRunner;
import org.jtester.tutorial.biz.model.Invoice;
import org.jtester.tutorial.biz.service.CustomerService;
import org.jtester.tutorial01.beans.PhoneGroup;
import org.jtester.tutorial01.beans.PhoneItem;
import org.jtester.tutorial01.daos.PhoneGroupDao;
import org.jtester.tutorial01.daos.PhoneItemDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DebugIt {
	public static void main(String[] args) {
		DbFitRunner.runDbFit(DebugIt.class, "clean table.wiki");
		// 初始化spring环境
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"spring/data-source.xml", "spring/beans.xml" });
		PhoneGroupDao phoneGroupDao = (PhoneGroupDao) context.getBean("phoneGroupDao");
		PhoneItemDao phoneItemDao = (PhoneItemDao) context.getBean("phoneItemDao");

		// 准备数据
		long groupId = phoneGroupDao.insertPhoneGroup(new PhoneGroup("classmate"));
		long phoneId1 = phoneItemDao.insertPhoneItem(new PhoneItem("darui.wu", "15900001111"));
		phoneGroupDao.addPhoneItemToGroup(phoneId1, groupId);

		long phoneId2 = phoneItemDao.insertPhoneItem(new PhoneItem("jobs.he", "13900001111"));
		phoneGroupDao.addPhoneItemToGroup(phoneId2, groupId);

		// 开始测试
		CustomerService customerService = (CustomerService) context.getBean("phoneBookService");
		List<Invoice> invoices = customerService.getInvoiceByCustomerName("darui.wu");

		// 将消息打印出来肉眼验证
		System.out.println(invoices.size());
		for (Invoice invoice : invoices) {
			System.out.println("customer id:" + invoice.getCustomer().getId() + ", address:" + invoice.getAddress());
		}
	}
}

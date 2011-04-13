package org.jtester.tutorial01.debugit;

import java.util.List;

import org.jtester.module.dbfit.DbFitRunner;
import org.jtester.tutorial01.beans.PhoneGroup;
import org.jtester.tutorial01.beans.PhoneItem;
import org.jtester.tutorial01.daos.PhoneGroupDao;
import org.jtester.tutorial01.daos.PhoneItemDao;
import org.jtester.tutorial01.services.PhoneBookService;
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
		PhoneBookService phoneBookService = (PhoneBookService) context.getBean("phoneBookService");
		List<PhoneItem> items = phoneBookService.findPhoneItemsByGroupName("classmate");

		// 将消息打印出来肉眼验证
		System.out.println(items.size());
		for (PhoneItem item : items) {
			System.out.println("user name:" + item.getUsername() + ", mobile:" + item.getMobile());
		}
	}
}

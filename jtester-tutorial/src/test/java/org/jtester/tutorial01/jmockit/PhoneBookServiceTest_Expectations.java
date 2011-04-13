package org.jtester.tutorial01.jmockit;

import java.util.ArrayList;
import java.util.List;

import mockit.Mocked;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanFrom;
import org.jtester.testng.JTester;
import org.jtester.tutorial01.beans.PhoneItem;
import org.jtester.tutorial01.daos.PhoneGroupDao;
import org.jtester.tutorial01.services.PhoneBookService;
import org.testng.annotations.Test;

@Test
@SpringApplicationContext( { "spring/data-source.xml", "spring/beans.xml", "spring/sqlmap-config.xml" })
public class PhoneBookServiceTest_Expectations extends JTester {
	@SpringBeanByName
	PhoneBookService phoneBookService;

	@SpringBeanFrom
	@Mocked
	PhoneGroupDao phoneGroupDao;

	@Test
	public void findPhoneItemsByGroupName() {
		final String groupname = "classmate";
		final List<PhoneItem> items = new ArrayList<PhoneItem>() {
			private static final long serialVersionUID = 1899462556039219035L;
			{
				add(new PhoneItem("darui.wu", "13900001111"));
				add(new PhoneItem("jobs.he", "15922221111"));
				add(new PhoneItem("matt.chen", "057188814545"));
			}
		};
		new Expectations() {
			{
				when(phoneGroupDao.getGroupIdByName(groupname)).thenReturn(1L);
				when(phoneGroupDao.findPhoneItemsByGroupId(1L)).thenReturn(items);
			}
		};
		List<PhoneItem> results = phoneBookService.findPhoneItemsByGroupName(groupname);
		want.collection(results).reflectionEq(items);
	}

	@Test
	public void findPhoneItemsByGroupName2() {
		final String groupname = "classmate";
		final List<PhoneItem> items = new ArrayList<PhoneItem>() {
			private static final long serialVersionUID = 1899462556039219035L;
			{
				add(new PhoneItem("darui.wu", "13900001111"));
				add(new PhoneItem("jobs.he", "15922221111"));
				add(new PhoneItem("matt.chen", "057188814545"));
			}
		};
		new Expectations() {
			{
				phoneGroupDao.getGroupIdByName(groupname);
				result = 1L;
				phoneGroupDao.findPhoneItemsByGroupId(1L);
				result = items;
			}
		};
		List<PhoneItem> results = phoneBookService.findPhoneItemsByGroupName(groupname);
		want.collection(results).reflectionEq(items);
	}
}

package org.jtester.tutorial01.jmockit;

import java.util.ArrayList;
import java.util.List;

import mockit.Mock;
import mockit.MockClass;
import mockit.UsingMocksAndStubs;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.jtester.tutorial01.beans.PhoneItem;
import org.jtester.tutorial01.daos.PhoneGroupDao;
import org.jtester.tutorial01.daos.impl.PhoneGroupDaoImpl;
import org.jtester.tutorial01.jmockit.DateUtilTest.MockDateUtil;
import org.jtester.tutorial01.utils.DateUtil;
import org.testng.annotations.Test;

@SpringApplicationContext( { "spring/data-source.xml", "spring/beans.xml", "spring/sqlmap-config.xml" })
@UsingMocksAndStubs( { MockDateUtil.class, UsingMocksAndStubsDemo.MockPhoneGroupDaoImpl.class })
public class UsingMocksAndStubsDemo extends JTester {
	@SpringBeanByName
	PhoneGroupDao phoneGroupDao;

	@Test
	public void testCustomDateTimeStr() {
		String datetime = DateUtil.currDateTimeStr("yy-MM-dd hh/mm/ss");
		want.string(datetime).isEqualTo("10-01-01 07/20/31");
	}

	@Test
	public void paySalary() {
		List<PhoneItem> list = this.phoneGroupDao.findPhoneItemsByGroupId(1L);
		want.collection(list).reflectionEq(items);
	}

	@MockClass(realClass = PhoneGroupDaoImpl.class)
	public static class MockPhoneGroupDaoImpl {
		@Mock
		public List<PhoneItem> findPhoneItemsByGroupId(long groupId) {
			want.number(groupId).isEqualTo(1L);
			return items;
		}
	}

	final static List<PhoneItem> items = new ArrayList<PhoneItem>() {
		private static final long serialVersionUID = 1899462556039219035L;
		{
			add(new PhoneItem("darui.wu", "13900001111"));
			add(new PhoneItem("jobs.he", "15922221111"));
			add(new PhoneItem("matt.chen", "057188814545"));
		}
	};
}

package org.jtester.tutorial01.debugit.testng;

import org.testng.annotations.Test;

/**
 * �����������ڲ�ͬ�����±ȽϿ��ٵ����в�ͬ���͵Ĳ���<br>
 * 1�����翪��ʱ,���߱���ֻ��Ҫ�����Լ��Ĳ��Դ���<br>
 * 2��SVN checkin��ʱ��Ҫ����checkin�����<br>
 * 3��ÿ����������ȫ���ԵĲ���<br>
 * 
 * @author darui.wudr
 * 
 */
@Test(groups = { "global", "presentation" })
public class TestNgGroupsDemo {
	@Test(groups = { "darui.wu", "checkin" })
	public void test1() {
		System.out.println("groups:global,darui.wu,checkin");
	}

	@Test(groups = { "davey.wu", "checkin" })
	public void test2() {
		System.out.println("groups:global,davey.wu,checkin");
	}

	@Test(groups = { "checkin" })
	public void test3() {
		System.out.println("groups:global,davey.wu,checkin");
	}

	@Test(groups = { "darui.wu" })
	public void test4() {
		System.out.println("groups:global,davey.wu,checkin");
	}
}

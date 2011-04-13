package org.jtester.tutorial01.debugit.testng;

import org.testng.annotations.Test;

/**
 * 分组有利于在不同条件下比较快速的运行不同类型的测试<br>
 * 1、比如开发时,作者本人只需要运行自己的测试代码<br>
 * 2、SVN checkin的时候要运行checkin组测试<br>
 * 3、每天检查是运行全局性的测试<br>
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

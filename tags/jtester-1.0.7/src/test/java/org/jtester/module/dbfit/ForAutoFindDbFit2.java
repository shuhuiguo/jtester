package org.jtester.module.dbfit;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.DbFit.AUTO;
import org.jtester.core.IJTester;
import org.jtester.core.testng.JTesterTestNG;
import org.testng.annotations.Test;

/**
 * 非测试类，仅用于协助AutoFindDbFitTest测试类
 * 
 * @author darui.wudr
 * 
 */
@DbFit(when = "data/clazz.wiki", auto = AUTO.UN_AUTO)
public class ForAutoFindDbFit2 extends JTesterTestNG implements IJTester {

	@Test(groups = "jtester")
	@DbFit(then = "data/ForAutoFindDbFit2/验证不自动加载wiki文件.wiki")
	public void test_不自动加载wiki文件_在这个例子中class不加载ForAutoFindDbFit2_wiki文件() {

	}

	@DbFit
	public void test1() {

	}

	@DbFit(when = { "1.wiki", "2.wiki" }, auto = AUTO.AUTO)
	public void test2() {

	}
}

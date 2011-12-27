package org.jtester.module.dbfit;

import org.jtester.IAssertion;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.DbFit.AUTO;
import org.junit.Test;

/**
 * 非测试类，仅用于协助AutoFindDbFitTest测试类
 * 
 * @author darui.wudr
 * 
 */
@DbFit(when = "data/clazz.wiki", auto = AUTO.UN_AUTO)
// (groups = "for-test")
public class ForAutoFindDbFit2 implements IAssertion {

	@Test
	// (groups = "jtester", description =
	// "在这个例子中class不加载ForAutoFindDbFit2_wiki文件")
	@DbFit(then = "data/ForAutoFindDbFit2/unauto_load_wiki.wiki")
	public void test_unautoLoad() {

	}

	@Test
	@DbFit
	public void test1() {

	}

	@Test
	@DbFit(when = { "1.wiki", "2.wiki" }, auto = AUTO.AUTO)
	public void test2() {

	}
}

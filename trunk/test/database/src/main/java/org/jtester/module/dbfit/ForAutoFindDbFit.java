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
@DbFit(when = "data/clazz.wiki", auto = AUTO.AUTO)
public class ForAutoFindDbFit implements IAssertion {

	@Test
	@DbFit(then = "data/ForAutoFindDbFit/test_classwiki_autoLoader.wiki")
	public void test_classwiki_autoLoader() {

	}

	@Test
	@DbFit
	public void test1() {

	}

	@Test
	public void test2() {

	}

	@Test
	@DbFit
	public void test3() {

	}

	@Test
	public void test4() {

	}

	@Test
	@DbFit(when = { "1.when.wiki", "2.when.wiki" }, then = { "1.then.wiki" })
	public void test5() {

	}
}

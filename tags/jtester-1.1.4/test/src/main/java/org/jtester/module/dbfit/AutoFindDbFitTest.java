package org.jtester.module.dbfit;

import java.lang.reflect.Method;

import org.jtester.module.dbfit.AutoFindDbFit;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class AutoFindDbFitTest extends JTester {

	@Test
	public void testAutoFindMethodWhen_DBFit已定义_文件可以找到() throws Exception {
		Method m = ForAutoFindDbFit.class.getMethod("test1");
		String[] whens = AutoFindDbFit.autoFindMethodWhen(ForAutoFindDbFit.class, m);
		want.array(whens).sizeEq(1).isEqualTo(new String[] { "data/ForAutoFindDbFit/test1.when.wiki" });
	}

	@Test
	public void testAutoFindMethodThen_DbFit已定义_文件可以找到() throws Exception {
		Method m = ForAutoFindDbFit.class.getMethod("test1");
		String[] whens = AutoFindDbFit.autoFindMethodThen(ForAutoFindDbFit.class, m);
		want.array(whens).sizeEq(1).isEqualTo(new String[] { "data/ForAutoFindDbFit/test1.then.wiki" });
	}

	@Test
	public void testAutoFindMethodWhen_DBFit未定义_文件可以找到() throws Exception {
		Method m = ForAutoFindDbFit.class.getMethod("test2");
		String[] whens = AutoFindDbFit.autoFindMethodWhen(ForAutoFindDbFit.class, m);
		want.array(whens).sizeEq(1).isEqualTo(new String[] { "data/ForAutoFindDbFit/test2.when.wiki" });
	}

	@Test
	public void testAutoFindMethodThen_DbFit未定义_文件可以找到() throws Exception {
		Method m = ForAutoFindDbFit.class.getMethod("test2");
		String[] whens = AutoFindDbFit.autoFindMethodThen(ForAutoFindDbFit.class, m);
		want.array(whens).sizeEq(1).isEqualTo(new String[] { "data/ForAutoFindDbFit/test2.then.wiki" });
	}

	@Test
	public void testAutoFindMethodWhen_DBFit已定义_文件找不到() throws Exception {
		Method m = ForAutoFindDbFit.class.getMethod("test3");
		String[] whens = AutoFindDbFit.autoFindMethodWhen(ForAutoFindDbFit.class, m);
		want.array(whens).sizeEq(0);
	}

	@Test
	public void testAutoFindMethodThen_DbFit已定义_文件找不到() throws Exception {
		Method m = ForAutoFindDbFit.class.getMethod("test3");
		String[] thens = AutoFindDbFit.autoFindMethodThen(ForAutoFindDbFit.class, m);
		want.array(thens).sizeEq(0);
	}

	@Test
	public void testAutoFindMethodWhen_DBFit未定义_文件找不到() throws Exception {
		Method m = ForAutoFindDbFit.class.getMethod("test4");
		String[] thens = AutoFindDbFit.autoFindMethodWhen(ForAutoFindDbFit.class, m);
		want.array(thens).sizeEq(0);
	}

	@Test
	public void testAutoFindMethodThen_DbFit未定义_文件找不到() throws Exception {
		Method m = ForAutoFindDbFit.class.getMethod("test4");
		String[] thens = AutoFindDbFit.autoFindMethodThen(ForAutoFindDbFit.class, m);
		want.array(thens).sizeEq(0);
	}

	@Test
	public void testAutoFindMethodWhen_DBFit已定义_文件可以找到_且DbFitWhen定义了文件() throws Exception {
		Method m = ForAutoFindDbFit.class.getMethod("test5");
		String[] whens = AutoFindDbFit.autoFindMethodWhen(ForAutoFindDbFit.class, m);
		want.array(whens).sizeEq(3)
				.isEqualTo(new String[] { "1.when.wiki", "2.when.wiki", "data/ForAutoFindDbFit/test5.when.wiki" });
	}

	@Test
	public void testAutoFindMethodThen_DBFit已定义_文件可以找到_且DbFitThen定义了文件() throws Exception {
		Method m = ForAutoFindDbFit.class.getMethod("test5");
		String[] thens = AutoFindDbFit.autoFindMethodThen(ForAutoFindDbFit.class, m);
		want.array(thens).sizeEq(2).isEqualTo(new String[] { "1.then.wiki", "data/ForAutoFindDbFit/test5.then.wiki" });
	}

	@Test
	public void testAutoFindClassWhen() {
		String[] wikis = AutoFindDbFit.autoFindClassWhen(ForAutoFindDbFit.class);
		want.array(wikis).sizeEq(2);
	}

	@Test
	public void testAutoFindClassWhen_不自动查找() {
		String[] wikis = AutoFindDbFit.autoFindClassWhen(ForAutoFindDbFit2.class);
		want.array(wikis).sizeEq(1);
	}
}

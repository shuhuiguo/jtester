package org.jtester.module.dbfit;

import java.lang.reflect.Method;

import org.jtester.module.dbfit.AutoFindDbFit;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class ForAutoFindDbFit2Test extends JTester {
	public void test_when_class上定义了禁止自动查找_方法上未覆盖定义() throws Exception {
		Method m = ForAutoFindDbFit2.class.getMethod("test1");
		String[] wikis = AutoFindDbFit.autoFindMethodWhen(ForAutoFindDbFit2.class, m);

		want.array(wikis).sizeEq(0);
	}

	public void test_then_class上定义了禁止自动查找_方法上未覆盖定义() throws Exception {
		Method m = ForAutoFindDbFit2.class.getMethod("test1");
		String[] wikis = AutoFindDbFit.autoFindMethodThen(ForAutoFindDbFit2.class, m);

		want.array(wikis).sizeEq(0);
	}

	public void test_when_class上定义了禁止自动查找_方法上覆盖定义() throws Exception {
		Method m = ForAutoFindDbFit2.class.getMethod("test2");
		String[] wikis = AutoFindDbFit.autoFindMethodWhen(ForAutoFindDbFit2.class, m);

		want.array(wikis).sizeEq(3);
	}

	public void test_then_class上定义了禁止自动查找_方法上覆盖定义() throws Exception {
		Method m = ForAutoFindDbFit2.class.getMethod("test2");
		String[] wikis = AutoFindDbFit.autoFindMethodThen(ForAutoFindDbFit2.class, m);

		want.array(wikis).sizeEq(1);
	}
}

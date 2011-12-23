package org.jtester.bytecode.reflector.helper;

import java.util.List;

import org.jtester.fortest.hibernate.UserService;
import org.jtester.reflector.finder.ClazzFinder;
import org.jtester.reflector.finder.MethodFinder;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

/**
 * this test will break for EclEmma Test
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings({ "rawtypes" })
@Test(groups = { "JTester", "broken-install" })
public class ClazzFinderTest extends JTester {
	public void findClazz_1() {
		String packagename = ClazzFinder.class.getPackage().getName();
		want.string(packagename).isEqualTo("org.jtester.bytecode.reflector.helper");
		List<String> clazz = ClazzFinder.findClazz(packagename);
		want.collection(clazz).sizeGe(7);
	}

	public void findClazz() {
		Class claz = UserService.class;
		List<String> clazz = ClazzFinder.findClazz(claz);
		want.collection(clazz).sizeGe(4);
		want.collection(clazz).allItemsMatchAll(the.string().regular("org\\.jtester\\.fortest\\.hibernate\\..*"));
	}

	public void findTestClaz() {
		Class claz = UserService.class;
		List<String> clazz = MethodFinder.findTestClaz(claz);
		int size = clazz.size();
		want.number(size).in(1, 2);
		want.collection(clazz).allItemsMatchAll(the.string().regular("org\\.jtester\\.fortest\\.hibernate\\..*"));
	}
}

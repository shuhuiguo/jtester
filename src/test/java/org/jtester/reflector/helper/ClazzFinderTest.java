package org.jtester.reflector.helper;

import java.util.List;

import org.jtester.fortest.hibernate.UserService;
import org.jtester.reflector.helper.ClazzFinder;
import org.jtester.reflector.helper.MethodFinder;
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
		want.string(packagename).isEqualTo("org.jtester.reflector.helper");
		List<String> clazz = ClazzFinder.findClazz(packagename);
		want.collection(clazz).sizeGe(7);
	}

	public void findClazz() {
		Class claz = UserService.class;
		List<String> clazz = ClazzFinder.findClazz(claz);
		want.collection(clazz).sizeGe(4);
		want.collection(clazz).allItemMatch("org\\.jtester\\.fortest\\.hibernate\\..*");
	}

	public void findTestClaz() {
		Class claz = UserService.class;
		List<String> clazz = MethodFinder.findTestClaz(claz);
		int size = clazz.size();
		want.number(size).in(1, 2);
		want.collection(clazz).allItemMatch("org\\.jtester\\.fortest\\.hibernate\\..*");
	}
}

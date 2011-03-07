package org.jtester.utility;

import java.util.List;

import org.jtester.fortest.hibernate.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

/**
 * this test will break for EclEmma Test
 * 
 * @author darui.wudr
 * 
 */
@Test(groups = { "JTester", "broken-install" })
public class FindClazUtilTest extends JTester {
	public void findClazz_1() {
		String packagename = FindClazUtil.class.getPackage().getName();
		want.string(packagename).isEqualTo("org.jtester.utility");
		List<String> clazz = FindClazUtil.findClazz(packagename);
		want.collection(clazz).sizeGe(9);
	}

	public void findClazz() {
		Class<?> claz = UserService.class;
		List<String> clazz = FindClazUtil.findClazz(claz);
		want.collection(clazz).sizeGe(4);
		want.collection(clazz).allItemMatch("org\\.jtester\\.fortest\\.hibernate\\..*");
	}

	public void findTestClaz() { 
		Class<?> claz = UserService.class;
		List<String> clazz = FindMethodUtil.findTestClaz(claz);
		int size = clazz.size();
		want.number(size).in(1,2);
		want.collection(clazz).allItemMatch("org\\.jtester\\.fortest\\.hibernate\\..*");
	}
}

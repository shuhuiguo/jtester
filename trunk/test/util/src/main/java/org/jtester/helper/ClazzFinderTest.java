package org.jtester.helper;

import java.util.List;

import org.jtester.IAssertion;
import org.jtester.beans.UserService;
import org.jtester.reflector.finder.ClazzFinder;
import org.jtester.reflector.finder.MethodFinder;
import org.junit.Ignore;
import org.junit.Test;

/**
 * this test will break for EclEmma Test
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings({ "rawtypes" })
public class ClazzFinderTest implements IAssertion {
	@Test
	public void findClazz_1() {
		String packagename = ClazzFinder.class.getPackage().getName();
		want.string(packagename).isEqualTo("org.jtester.reflector.finder");
		List<String> clazz = ClazzFinder.findClazz(packagename);
		want.collection(clazz).sizeGe(2);
	}

	@Test
	public void findClazz() {
		Class claz = UserService.class;
		List<String> clazz = ClazzFinder.findClazz(claz);
		want.collection(clazz).sizeGe(4);
		want.collection(clazz).allItemsMatchAll(the.string().regular("org\\.jtester\\.beans\\..*"));
	}

	@Test
	@Ignore
	public void findTestClaz() {
		Class claz = UserService.class;
		List<String> clazz = MethodFinder.findTestClaz(claz);
		int size = clazz.size();
		want.number(size).in(1, 2);
		want.collection(clazz).allItemsMatchAll(the.string().regular("org\\.jtester\\.fortest\\.hibernate\\..*"));
	}
}

package org.jtester.helper;

import java.util.List;

import org.jtester.IAssertion;
import org.jtester.beans.MyService;
import org.jtester.reflector.finder.MethodFinder;
import org.junit.Ignore;
import org.junit.Test;

/**
 * this test is broken for EclEmma Test
 * 
 * @author darui.wudr
 * 
 */

public class MethodFinderTest implements IAssertion {
	@Test
	public void findTestMethod_1() {
		List<String> methods = MethodFinder.findTestMethod(MyService.class, "mySay");
		want.collection(methods).sizeEq(1);
		want.collection(methods).hasAllItems("org.jtester.beans.MyServiceImpl.mySay");
	}

	@Test
	@Ignore
	public void findTestMethod_2() {
		List<String> methods = MethodFinder.findTestMethod(MethodFinderTest.class, "getUser");
		want.collection(methods).sizeEq(1);
		want.collection(methods).hasAllItems("org.jtester.beans.UserServiceImpl.getUser");
	}
}

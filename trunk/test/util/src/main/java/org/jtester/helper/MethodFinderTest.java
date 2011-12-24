package org.jtester.helper;

import java.util.List;

import org.jtester.IAssertion;
import org.jtester.beans.UserService;
import org.jtester.reflector.finder.MethodFinder;
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
		List<String> methods = MethodFinder.findTestMethod(UserService.class, "findAddress");
		want.collection(methods).sizeEq(1);
		want.collection(methods).hasAllItems("org.jtester.fortest.hibernate.UserServiceImpl.findAddress");
	}

	@Test
	public void findTestMethod_2() {
		List<String> methods = MethodFinder.findTestMethod(UserService.class, "getUser");
		want.collection(methods).sizeEq(1);
		want.collection(methods).hasAllItems("org.jtester.fortest.hibernate.UserServiceImpl.getUser");
	}
}

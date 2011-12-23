package org.jtester.bytecode.reflector.helper;

import java.util.List;

import org.jtester.fortest.hibernate.UserService;
import org.jtester.reflector.utility.MethodFinder;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

/**
 * this test is broken for EclEmma Test
 * 
 * @author darui.wudr
 * 
 */
@Test(groups = { "broken" })
public class MethodFinderTest extends JTester {
	public void findTestMethod_1() {
		List<String> methods = MethodFinder.findTestMethod(UserService.class, "findAddress");
		want.collection(methods).sizeEq(1);
		want.collection(methods).hasAllItems("org.jtester.fortest.hibernate.UserServiceImpl.findAddress");
	}

	public void findTestMethod_2() {
		List<String> methods = MethodFinder.findTestMethod(UserService.class, "getUser");
		want.collection(methods).sizeEq(1);
		want.collection(methods).hasAllItems("org.jtester.fortest.hibernate.UserServiceImpl.getUser");
	}
}

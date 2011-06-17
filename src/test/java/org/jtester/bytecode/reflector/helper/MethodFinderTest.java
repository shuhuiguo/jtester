package org.jtester.bytecode.reflector.helper;

import java.util.Arrays;
import java.util.List;

import org.jtester.bytecode.reflector.helper.MethodFinder;
import org.jtester.fortest.hibernate.UserService;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

/**
 * this test is broken for EclEmma Test
 * 
 * @author darui.wudr
 * 
 */
@Test(groups = { "JTester", "broken-install" })
public class MethodFinderTest extends JTester {
	public void findTestMethod_1() {
		List<String> methods = MethodFinder.findTestMethod(UserService.class, "findAddress");
		want.collection(methods).sizeEq(1);
		want.collection(methods).hasItems(Arrays.asList("org.jtester.fortest.hibernate.UserServiceImpl.findAddress"));
	}

	public void findTestMethod_2() {
		List<String> methods = MethodFinder.findTestMethod(UserService.class, "getUser");
		want.collection(methods).sizeEq(1);
		want.collection(methods).hasItems(Arrays.asList("org.jtester.fortest.hibernate.UserServiceImpl.getUser"));
	}
}

package org.jtester.testng;

import java.lang.reflect.Method;

import org.jtester.annotations.SpringInitMethod;
import org.jtester.bytecode.reflector.helper.MethodHelper;
import org.jtester.core.IJTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class JTesterTest_IsSpringInitMethod implements IJTester {

	@Test
	public void testIsSpringInitMethod_PrivateMethod() throws Exception {
		Method method = MethodHelper.getMethod(JTesterTest_IsSpringInitMethod.class, "privateMethod");
		boolean isSpringInitMethod = (Boolean) reflector.invokeStatic(JTester.class, "isSpringInitMethod", method);
		want.bool(isSpringInitMethod).isEqualTo(true);
	}

	@Test
	public void testIsSpringInitMethod_ProtectedeMethod() throws Exception {
		Method method = MethodHelper.getMethod(JTesterTest_IsSpringInitMethod.class, "protectedMethod");
		boolean isSpringInitMethod = (Boolean) reflector.invokeStatic(JTester.class, "isSpringInitMethod", method);
		want.bool(isSpringInitMethod).isEqualTo(true);
	}

	@Test
	public void testIsSpringInitMethod_publicMethod() throws Exception {
		Method method = MethodHelper.getMethod(JTesterTest_IsSpringInitMethod.class, "publicMethod");
		try {
			reflector.invokeStatic(JTester.class, "isSpringInitMethod", method);
		} catch (Exception e) {
			String message = e.getMessage();
			want.string(message).contains("being regarded as @Test method").contains("can't be public");
		}
	}

	@Test
	public void testIsSpringInitMethod_HasParameter() throws Exception {
		Method method = MethodHelper.getMethod(JTesterTest_IsSpringInitMethod.class, "hasParameter", int.class);
		try {
			reflector.invokeStatic(JTester.class, "isSpringInitMethod", method);
		} catch (Exception e) {
			String message = e.getMessage();
			want.string(message).contains("can't have any parameter");
		}
	}

	@Test
	public void testIsSpringInitMethod_NormalMethod() throws Exception {
		Method method = MethodHelper.getMethod(JTesterTest_IsSpringInitMethod.class, "normalMethod");
		boolean isSpringInitMethod = (Boolean) reflector.invokeStatic(JTester.class, "isSpringInitMethod", method);
		want.bool(isSpringInitMethod).isEqualTo(false);
	}

	@SuppressWarnings("unused")
	@SpringInitMethod
	private void privateMethod() {
	}

	@SpringInitMethod
	protected void protectedMethod() {
	}

	@SpringInitMethod
	protected void hasParameter(int pi) {

	}

	@SpringInitMethod
	public void publicMethod() {

	}

	public void normalMethod() {

	}
}

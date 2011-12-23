package org.jtester.testng;

import org.jtester.annotations.SpringInitMethod;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class JTesterTest extends JTester {

	@Test
	public void testInvokeSpringInitMethod() {
		want.string(privateMethod).isEqualTo("privateMethod");
		want.string(protectedMethod).isEqualTo("protectedMethod");
	}

	private static String privateMethod = "unknown";

	@SuppressWarnings("unused")
	@SpringInitMethod
	private void privateMethod() {
		privateMethod = "privateMethod";
	}

	private static String protectedMethod = "unknown";

	@SpringInitMethod
	protected void protectedMethod() {
		protectedMethod = "protectedMethod";
	}
}

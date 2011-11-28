package org.jtester.tutorial.asserter;

import java.io.IOException;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

/**
 * 异常测试演示
 * 
 * @author darui.wudr
 * 
 */
@Test(groups = "tutorial")
public class ExceptionAssertion extends JTester {

	public void testDemo_OnlyThrowing() throws IOException {
		ExceptionDemo bean = new ExceptionDemo();
		bean.sayHello();
	}

	@Test(expectedExceptions = { IOException.class })
	public void testDemo_AssertException() throws IOException {
		ExceptionDemo bean = new ExceptionDemo();
		bean.sayHello();
	}

	public void testDemo_TryCatch() throws IOException {
		ExceptionDemo bean = new ExceptionDemo();
		try {
			bean.sayHello();
		} catch (Exception e) {
			want.object(e).eqToString(the.string().contains("excepton demo."));
		}
	}
}

class ExceptionDemo {
	void sayHello() throws IOException {
		throw new IOException("excepton demo.");
	}
}

package org.jtester.core.testng;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TestNgUtil {
	/**
	 * 运行testng的单个测试方法
	 * 
	 * @param clazz
	 *            测试类
	 * @param method
	 *            方法名称
	 * @param isThrowException
	 *            是否抛出测试异常消息
	 * @return
	 */
	public static boolean run(String clazz, String method, boolean isThrowException) {
		TestNG tng = getTestRunner(clazz, method, isThrowException);
		TestListenerAdapter listener = new TestListenerAdapter();
		tng.addListener(listener);
		tng.run();
		int success = listener.getPassedTests().size();
		int failure = listener.getFailedTests().size();
		if (isThrowException) {
			for (ITestResult rt : listener.getFailedTests()) {
				throw new RuntimeException(rt.getThrowable());
			}
		}
		return success == 1 && failure == 0;
	}

	/**
	 * 构造单个方法的testng runner
	 * 
	 * @param clazz
	 * @param method
	 * @param isThrowException
	 * @return
	 */
	public static TestNG getTestRunner(String clazz, String method, boolean isThrowException) {
		TestNG tng = new TestNG();
		XmlSuite suite = new XmlSuite();
		XmlTest test = new XmlTest(suite);
		test.setName("run testng");
		XmlClass xmlClazz = new XmlClass(clazz);
		try {
			/**
			 * 为了兼容testng 5.11(以下版)和5.12(以上版)，采用了try catch
			 */
			List includes = xmlClazz.getIncludedMethods();
			try {
				// 下列代码其实就是includes.add(new XmlInclude(method)); <br>
				// 采用反射构造是为了在testNg5.11下也可以编译通过
				Class xmlInclude = Class.forName("org.testng.xml.XmlInclude");
				Constructor constructor = xmlInclude.getConstructor(String.class);
				includes.add(constructor.newInstance(method));
			} catch (Exception e) {
				includes.add(method);
			}
			xmlClazz.getExcludedMethods().add(method + ".+");
		} catch (Exception e) {

		}
		test.getXmlClasses().add(xmlClazz);

		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		tng.setXmlSuites(suites);

		return tng;
	}

//	/**
//	 * 根据suite文件运行testng测试
//	 * 
//	 * @param suiteXml
//	 * @return
//	 */
//	public static TestNG getTestRunner(String suiteXml) {
//		TestNG tng = new TestNG();
//
//		XmlSuite suite = new XmlSuite();
//		suite.setFileName(suiteXml);
//		List<XmlSuite> suites = new ArrayList<XmlSuite>();
//		suites.add(suite);
//		tng.setXmlSuites(suites);
//		return tng;
//	}
}

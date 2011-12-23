package org.jtester.testng;

import java.lang.reflect.Method;
import java.util.List;

import mockit.internal.RedefinitionEngine;

import org.jtester.core.CoreModule;
import org.jtester.core.JTesterHelper;
import org.jtester.core.TestedContext;
import org.jtester.core.helper.ListenerExecutor;
import org.jtester.core.testng.JTesterHookable;
import org.jtester.core.testng.MockTestNGMethodFinder;
import org.jtester.exception.ExceptionWrapper;
import org.jtester.fit.ErrorRecorder;
import org.jtester.helper.ArrayHelper;
import org.jtester.helper.ListHelper;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("rawtypes")
@Test(groups = "all-test")
public abstract class JTester extends JTesterHookable {

	static {
		CoreModule.initSingletonInstance();
		ErrorRecorder.createNewErrorFile();
		new RedefinitionEngine(null, MockTestNGMethodFinder.class).setUpStartupMock();
	}

	@BeforeClass(alwaysRun = true)
	public void aBeforeClass(ITestContext context) {
		TestedContext.setContext(this, null);
		JTesterHelper.invokeSpringInitMethod(this);
		this.dealJMockitTestDecorator(context);
		this.error_setup_class = ListenerExecutor.executeBeforeClassEvents(this.getClass());
	}

	@BeforeMethod(alwaysRun = true)
	public void aBeforeMethod(Method testedMethod) {
		this.error_setup_method = ListenerExecutor.executeBeforeMethodEvents(this, testedMethod);
	}

	@AfterMethod(alwaysRun = true)
	public void zAfterMethod(Method testedMethod) {
		Throwable throwable = ListenerExecutor.executeAfterMethodEvents(this, testedMethod);
		ExceptionWrapper.throwRuntimeException(throwable);
	}

	@AfterClass(alwaysRun = true)
	public void zAfterClass() {
		Throwable throwable = ListenerExecutor.executeAfterClassEvents(this);
		ExceptionWrapper.throwRuntimeException(throwable);
	}

	/**
	 * 构造对象数组
	 * 
	 * @param objs
	 * @return
	 */
	public Object[] toArray(Object... objs) {
		Object[] arr = ArrayHelper.toArray(objs);
		return arr;
	}

	/**
	 * 构造list列表
	 * 
	 * @param objs
	 * @return
	 */
	public List toList(Object... objs) {
		List list = ListHelper.toList(objs);
		return list;
	}
}

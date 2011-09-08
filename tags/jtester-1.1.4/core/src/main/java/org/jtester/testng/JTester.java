package org.jtester.testng;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.jtester.annotations.SpringInitMethod;
import org.jtester.core.ListenerExecutor;
import org.jtester.core.testng.DataProviderIterator;
import org.jtester.core.testng.JTesterHookable;
import org.jtester.exception.ExceptionWrapper;
import org.jtester.fit.ErrorRecorder;
import org.jtester.module.core.CoreModule;
import org.jtester.utility.ArrayHelper;
import org.jtester.utility.ListHelper;
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
	}

	/**
	 * 用来在jTester初始化之前工作<br>
	 * 比如spring加载前的mock工作等
	 */
	private void invokeSpringInitMethod() {
		Class claz = this.getClass();
		Method[] methods = claz.getDeclaredMethods();
		for (Method method : methods) {
			boolean isSpringInitMethod = isSpringInitMethod(method);
			if (isSpringInitMethod == false) {
				continue;
			}
			boolean accessible = method.isAccessible();
			method.setAccessible(true);
			try {
				method.invoke(this);
			} catch (Exception e) {
				throw new RuntimeException("invoke @SpringInitMethod " + method.getName() + " error.", e);
			} finally {
				method.setAccessible(accessible);
			}
		}
	}

	/**
	 * 判断是否是@SpringInitMethod方法
	 * 
	 * @param method
	 * @return
	 */
	private static boolean isSpringInitMethod(Method method) {
		SpringInitMethod springInitMethod = method.getAnnotation(SpringInitMethod.class);
		if (springInitMethod == null) {
			return false;
		}
		int mod = method.getModifiers();
		if (Modifier.isPublic(mod)) {
			throw new RuntimeException(
					"to avoid being regarded as @Test method, the @SpringInitMethod can't be public.");
		}

		Class<?>[] paras = method.getParameterTypes();
		if (paras.length != 0) {
			throw new RuntimeException("the @SpringInitMethod can't have any parameter.");
		}
		return true;
	}

	@BeforeClass(alwaysRun = true)
	public void aBeforeClass(ITestContext context) {
		this.invokeSpringInitMethod();
		this.dealJMockitTestDecorator(context);
		this.error_setup_class = ListenerExecutor.executeSetupClass(this);
	}

	@BeforeMethod(alwaysRun = true)
	public void aBeforeMethod(Method testedMethod) {
		this.error_setup_method = ListenerExecutor.executeSetupMethod(this, testedMethod);
	}

	@AfterMethod(alwaysRun = true)
	public void zAfterMethod(Method testedMethod) {
		Throwable throwable = ListenerExecutor.executeTeardownMethod(this, testedMethod);
		ExceptionWrapper.throwRuntimeException(throwable);
	}

	@AfterClass(alwaysRun = true)
	public void zAfterClass() {
		Throwable throwable = ListenerExecutor.executeTeardownClass(this);
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

	public static class DataIterator extends DataProviderIterator {
	}
}

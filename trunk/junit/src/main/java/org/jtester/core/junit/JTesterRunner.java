package org.jtester.core.junit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jtester.core.junit.internal.FrameworkMethodWithParameters;
import org.jtester.core.junit.internal.MethodAroundStatement;
import org.jtester.core.junit.internal.ParameterDataFromHelper;
import org.jtester.core.junit.internal.TestAroundStatement;
import org.jtester.module.TestListener;
import org.jtester.module.core.CoreModule;
import org.jtester.reflector.utility.MethodHelper;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

@SuppressWarnings({ "rawtypes" })
public class JTesterRunner extends BlockJUnit4ClassRunner {
	private Object test = null;

	private final Class testClazz;

	/**
	 * Creates a test runner that runs all test methods in the given class.
	 * 
	 * @param testClass
	 *            the class, not null
	 * @throws org.junit.runners.model.InitializationError
	 * @throws InitializationError
	 */
	public JTesterRunner(Class testClass) throws InitializationError {
		super(testClass);
		this.testClazz = testClass;
	}

	@Override
	protected Object createTest() throws Exception {
		if (this.test == null) {
			this.test = super.createTest();
		}
		return this.test;
	}

	@Override
	protected Statement childrenInvoker(final RunNotifier notifier) {
		return new Statement() {
			@Override
			public void evaluate() {
				JTesterRunner.this.getTestListener().beforeClass(JTesterRunner.this.testClazz);
				MethodHelper.invokeUnThrow(JTesterRunner.this, "runChildren", notifier);
				JTesterRunner.this.getTestListener().afterClass(JTesterRunner.this.test);
			}
		};
	}

	@Override
	protected Statement methodBlock(FrameworkMethod method) {
		Statement statement = super.methodBlock(method);
		statement = new TestAroundStatement(statement, getTestListener(), test, method.getMethod());
		return statement;
	}

	@Override
	public Statement methodInvoker(FrameworkMethod method, Object test) {
		Statement statement = super.methodInvoker(method, test);
		statement = new MethodAroundStatement(statement, getTestListener(), test, method.getMethod());
		return statement;
	}

	@Override
	protected String testName(FrameworkMethod method) {
		if (method instanceof FrameworkMethodWithParameters) {
			return method.toString();
		} else {
			return super.testName(method);
		}
	}

	private List<FrameworkMethod> testMethods;

	/**
	 * {@inheritDoc}<br>
	 * 构造有参和无参的测试方法列表
	 */
	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		if (this.testMethods == null) {
			List<FrameworkMethod> initTestMethods = super.computeTestMethods();
			testMethods = new ArrayList<FrameworkMethod>();
			for (FrameworkMethod frameworkMethod : initTestMethods) {
				Method testMethod = frameworkMethod.getMethod();
				DataFrom dataFrom = testMethod.getAnnotation(DataFrom.class);
				if (dataFrom == null) {
					testMethods.add(frameworkMethod);
				} else {
					List<FrameworkMethodWithParameters> parameterizedTestMethods = ParameterDataFromHelper
							.computeParameterizedTestMethods(frameworkMethod.getMethod(), dataFrom);
					testMethods.addAll(parameterizedTestMethods);
				}
			}
		}
		return this.testMethods;
	}

	/**
	 * @return The unitils test listener
	 */
	protected TestListener getTestListener() {
		return CoreModule.getTestListener();
	}
}

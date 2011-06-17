package org.jtester.testng;

import java.lang.reflect.Method;

import org.jtester.core.ListenerExecutor;
import org.jtester.core.testng.JTesterHookable;
import org.jtester.exception.ExceptionWrapper;
import org.jtester.fit.ErrorRecorder;
import org.jtester.module.core.CoreModule;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = "all-test")
public abstract class JTester extends JTesterHookable {

	static {
		CoreModule.initSingletonInstance();

		ErrorRecorder.createNewErrorFile();
	}

	@BeforeClass(alwaysRun = true)
	public void setupJTesterClass(ITestContext context) {
		this.dealJMockitTestDecorator(context);
		this.error_setup_class = ListenerExecutor.executeSetupClass(this);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupJTesterMethod(Method testedMethod) {
		this.error_setup_method = ListenerExecutor.executeSetupMethod(this, testedMethod);
	}

	@AfterMethod(alwaysRun = true)
	public void teardownJTesterMethod(Method testedMethod) {
		Throwable throwable = ListenerExecutor.executeTeardownMethod(this, testedMethod);
		ExceptionWrapper.throwRuntimeException(throwable);
	}

	@AfterClass(alwaysRun = true)
	public void teardownJTesterClass() {
		Throwable throwable = ListenerExecutor.executeTeardownClass(this);
		ExceptionWrapper.throwRuntimeException(throwable);
	}
}

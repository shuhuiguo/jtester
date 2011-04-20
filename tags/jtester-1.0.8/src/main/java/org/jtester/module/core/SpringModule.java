package org.jtester.module.core;

import org.jtester.core.TestContext;
import org.jtester.module.TestListener;
import org.jtester.module.core.helper.ConfigurationHelper;
import org.jtester.module.core.helper.SpringModuleHelper;
import org.jtester.module.jmock.MockBeanRegister;
import org.jtester.module.spring.ApplicationContextFactory;
import org.springframework.context.support.AbstractApplicationContext;

@SuppressWarnings("rawtypes")
public class SpringModule implements Module {
	private ApplicationContextFactory contextFactory;

	/**
	 * 根据配置初始化ApplicationContextFactory <br>
	 * <br> {@inheritDoc}
	 */
	public void init() {
		contextFactory = ConfigurationHelper.getInstance(SPRING_APPLICATION_CONTEXT_FACTORY_CLASS_NAME);
	}

	public void afterInit() {
	}

	/**
	 * 强制让springapplicationcontext失效，重新初始化
	 * 
	 * @param testedObject
	 */
	public void invalidateApplicationContext() {
		Object testedObject = TestContext.currTestedObject();
		TestContext.removeSpringContext();
		AbstractApplicationContext context = SpringModuleHelper.initSpringContext(testedObject, this.contextFactory);
		TestContext.setSpringContext(context);
	}

	public TestListener getTestListener() {
		return new SpringTestListener();
	}

	/**
	 * The {@link TestListener} for this module
	 */
	protected class SpringTestListener extends TestListener {
		public void beforeTestClass(Object testObject) throws Exception {
			Class claz = testObject.getClass();
			MockBeanRegister.registerAllMockBean(claz);

			AbstractApplicationContext context = SpringModuleHelper.initSpringContext(testObject, contextFactory);
			TestContext.setSpringContext(context);
		}

		@Override
		public void afterTestClass(Object testedObject) throws Exception {
			TestContext.removeSpringContext();
		}

		@Override
		protected String getName() {
			return "SpringTestListener";
		}
	}
}

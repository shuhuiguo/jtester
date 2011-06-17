package org.jtester.module.core;

import java.lang.reflect.Method;

import org.jtester.core.TestContext;
import org.jtester.module.TestListener;
import org.jtester.module.core.helper.ConfigurationHelper;
import org.jtester.module.core.helper.SpringModuleHelper;
import org.jtester.module.spring.ApplicationContextFactory;
import org.jtester.module.spring.strategy.injector.SpringBeanInjector;
import org.springframework.context.support.AbstractApplicationContext;

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
		public void setupClass(Object testObject) throws Exception {
			AbstractApplicationContext context = SpringModuleHelper.initSpringContext(testObject, contextFactory);
			TestContext.setSpringContext(context);
		}

		/**
		 * 重新注入spring bean,避免字段的值受上个测试的影响<br>
		 * <br> {@inheritDoc}
		 */
		@Override
		public void setupMethod(Object testObject, Method testMethod) throws Exception {
			AbstractApplicationContext context = (AbstractApplicationContext) TestContext.getSpringContext();
			if (context != null) {
				SpringBeanInjector.injectSpringBeans(context, testObject);
			}
		}

		@Override
		public void teardownClass(Object testedObject) throws Exception {
			TestContext.removeSpringContext();
		}

		@Override
		protected String getName() {
			return "SpringTestListener";
		}
	}
}

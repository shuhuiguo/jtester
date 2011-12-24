package org.jtester.module.core;

import java.lang.reflect.Method;

import org.jtester.core.helper.ConfigurationHelper;
import org.jtester.database.TransactionHelper;
import org.jtester.module.Module;
import org.jtester.module.TestListener;
import org.jtester.module.TestedContext;
import org.jtester.module.core.helper.SpringModuleHelper;
import org.jtester.module.spring.ApplicationContextFactory;
import org.jtester.module.spring.JTesterBeanFactory;
import org.jtester.module.spring.JTesterSpringContext;
import org.jtester.module.spring.strategy.injector.SpringBeanInjector;

@SuppressWarnings("rawtypes")
public class SpringModule implements Module {
	private ApplicationContextFactory contextFactory;

	/**
	 * 根据配置初始化ApplicationContextFactory <br>
	 * <br>
	 * {@inheritDoc}
	 */
	public void init() {
		contextFactory = ConfigurationHelper.getInstance(SPRING_APPLICATION_CONTEXT_FACTORY_CLASS_NAME);
		SpringModuleHelper.mockCglibAopProxy();
	}

	public void afterInit() {
	}

	/**
	 * 强制让SpringApplicationContext失效，重新初始化
	 * 
	 * @param testedObject
	 */
	public void invalidateApplicationContext() {
		Class testClazz = TestedContext.currTestedClazz();
		TransactionHelper.removeSpringContext();
		JTesterSpringContext springContext = SpringModuleHelper.initSpringContext(testClazz, this.contextFactory);
		TransactionHelper.setSpringContext(springContext);
	}

	public TestListener getTestListener() {
		return new SpringTestListener();
	}

	/**
	 * The {@link TestListener} for this module
	 */
	protected class SpringTestListener extends TestListener {
		@Override
		public void beforeClass(Class testClazz) {
			SpringModuleHelper.resetDumpReference();

			JTesterSpringContext springContext = SpringModuleHelper.initSpringContext(testClazz, contextFactory);
			TransactionHelper.setSpringContext(springContext);
		}

		/**
		 * 重新注入spring bean,避免字段的值受上个测试的影响<br>
		 * <br>
		 * {@inheritDoc}
		 */
		@Override
		public void beforeMethod(Object testObject, Method testMethod) {
			JTesterBeanFactory beanFactory = (JTesterBeanFactory) TransactionHelper.getSpringBeanFactory();
			if (beanFactory != null) {
				SpringBeanInjector.injectSpringBeans(beanFactory, testObject);
			}
		}

		@Override
		public void afterClass(Object testedObject) {
			TransactionHelper.removeSpringContext();
		}

		@Override
		protected String getName() {
			return "SpringTestListener";
		}
	}
}

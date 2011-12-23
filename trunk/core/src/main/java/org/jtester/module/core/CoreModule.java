package org.jtester.module.core;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import org.jtester.core.TestedContext;
import org.jtester.module.TestListener;
import org.jtester.module.core.helper.ConfigurationHelper;
import org.jtester.module.core.helper.ModulesManager;
import org.jtester.module.core.loader.ConfigurationLoader;
import org.jtester.module.core.loader.ModulesLoader;
import org.jtester.utility.JTesterLogger;

/**
 * jtester的核心类，所有事件监听器的总入口<br>
 * 
 * Core class of the jtester library, and the main entry point that gives access
 * to the {@link TestContext} and the different {@link Module}s.
 * <p/>
 * An instance of jtester is configured with a certain configuration using the
 * {@link #init(Properties)} method. Normally, only one instance of jtester
 * exists at any time. The default instance can be obtained using the
 * {@link #getInstance()} method. This default instance can be set to a custom
 * initialized instance or instance of a custom subclass using
 * {@link #setInstance(CoreModule)}.
 * <p/>
 * If not set, the singleton instance is initialized by default using
 * {@link #initSingletonInstance()}. This method uses the
 * {@link ConfigurationLoader} to load the configuration. An instance of
 * {@link ModulesManager} is used to initialize and maintain the modules.
 * <p/>
 * jtester itself is also implemented as a module. In fact, an instance of
 * jtester behaves like a module who's behaviour is defined by the added
 * behaviour of all modules.
 */
public class CoreModule {
	private static CoreModule instance;

	/**
	 * Returns the singleton instance
	 * 
	 * @return the singleton instance, not null
	 */
	public static synchronized CoreModule getInstance() {
		if (instance == null) {
			initSingletonInstance();
		}
		return instance;
	}

	/**
	 * 初始化jTester,要保证这个方法在使用jTester功能之前被调用<br>
	 * <br>
	 * Initializes the singleton instance to the default value, loading the
	 * configuration using the {@link ConfigurationLoader}
	 */
	public static void initSingletonInstance() {
		ConfigurationLoader.loading();
		JTesterLogger.level = ConfigurationHelper.logLevel();
		instance = new CoreModule();
	}

	/* Listener that observes the execution of tests */
	private TestListener testListener;

	/**
	 * Creates a new instance.
	 */
	public CoreModule() {
		List<Module> modules = ModulesLoader.loading();
		this.testListener = new CoreModuleListener();
		for (Module module : modules) {
			module.afterInit();
		}
	}

	/**
	 * Returns the single instance of {@link TestListener}. This instance
	 * provides hook callback methods that enable intervening during the
	 * execution of unit tests.
	 * 
	 * @return The single {@link TestListener}
	 */
	public static TestListener getTestListener() {
		return getInstance().testListener;
	}

	/**
	 * Implementation of {@link TestListener} that ensures that at every point
	 * during the run of a test, every {@link Module} gets the chance of
	 * performing some behavior, by calling the {@link TestListener} of each
	 * module in turn. Also makes sure that the state of the instance of
	 * {@link TestContext} returned by {@link CoreModule#getTestContext()} is
	 * correctly set to the current test class, test object and test method.
	 */
	private class CoreModuleListener extends TestListener {
		@SuppressWarnings("rawtypes")
		@Override
		public void beforeClass(Class testClazz) {
			JTesterLogger.resetLog4jLevel();

			TestedContext.setContext(testClazz);
			List<TestListener> listeners = ModulesManager.getTestListeners();
			for (TestListener listener : listeners) {
				listener.beforeClass(testClazz);
			}
		}

		@Override
		public void beforeMethod(Object testObject, Method testMethod) {
			TestedContext.setContext(testObject, testMethod);

			List<TestListener> listeners = ModulesManager.getTestListeners();
			for (TestListener listener : listeners) {
				listener.beforeMethod(testObject, testMethod);
			}
		}

		@Override
		public void beforeRunning(Object testObject, Method testMethod) {
			TestedContext.setContext(testObject, testMethod);

			List<TestListener> listeners = ModulesManager.getTestListeners();
			for (TestListener listener : listeners) {
				listener.beforeRunning(testObject, testMethod);
			}
		}

		@Override
		public void afterRunned(Object testObject, Method testMethod, Throwable throwable) {
			TestedContext.setContext(testObject, testMethod);

			List<TestListener> listeners = ModulesManager.getTestListeners_Reverse();
			for (TestListener listener : listeners) {
				listener.afterRunned(testObject, testMethod, throwable);
			}
		}

		@Override
		public void afterMethod(Object testObject, Method testMethod) {
			TestedContext.setContext(testObject, testMethod);

			List<TestListener> listeners = ModulesManager.getTestListeners_Reverse();
			for (TestListener listener : listeners) {
				listener.afterMethod(testObject, testMethod);
			}
		}

		@Override
		public void afterClass(Object testObject) {
			List<TestListener> listeners = ModulesManager.getTestListeners_Reverse();
			for (TestListener listener : listeners) {
				listener.afterClass(testObject);
			}
			TestedContext.cleanContext();
		}

		@Override
		protected String getName() {
			return "CoreModuleListener";
		}
	}
}
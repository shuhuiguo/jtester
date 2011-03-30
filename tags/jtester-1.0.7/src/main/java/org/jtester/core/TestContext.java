package org.jtester.core;

import static org.jtester.annotations.Transactional.TransactionMode.COMMIT;
import static org.jtester.annotations.Transactional.TransactionMode.DEFAULT;
import static org.jtester.annotations.Transactional.TransactionMode.ROLLBACK;
import static org.jtester.module.core.ConfigurationConst.TRANSACTIONAL_MODE_DEFAULT;
import static org.jtester.utility.AnnotationUtils.getMethodOrClassLevelAnnotationProperty;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jtester.annotations.Transactional;
import org.jtester.annotations.Transactional.TransactionMode;
import org.jtester.core.context.TransactionManager;
import org.jtester.module.core.helper.ConfigurationHelper;
import org.jtester.module.core.helper.SpringModuleHelper;
import org.jtester.module.spring.strategy.SpringBeanCleaner;
import org.jtester.module.spring.strategy.SpringBeanInjector;

/**
 * jTester测试上下文信息<br>
 * 单列模式 <br>
 */
@SuppressWarnings("rawtypes")
public class TestContext {
	private final static Logger log4j = Logger.getLogger(TestContext.class);

	private final static TestContext context = new TestContext();

	public final static TestContext context() {
		return context;
	}

	/**
	 * 设置测试上下文的测试类<br>
	 * 测试实例和测试方法置空
	 * 
	 * @param claz
	 */
	public final static void setContext(Class claz) {
		context.testedClazz = claz;
		context.testedObject = null;
		context.testedMethod = null;
	}

	/**
	 * 设置测试上下文信息
	 * 
	 * @param testedObject
	 * @param testedMethod
	 */
	public final static void setContext(Object testedObject, Method testedMethod) {
		if (testedObject == null) {
			throw new RuntimeException("tested object can't be null.");
		}
		context.testedClazz = testedObject.getClass();
		context.testedObject = testedObject;
		context.testedMethod = testedMethod;
	}

	/**
	 * 清空测试上下文信息
	 */
	public final static void cleanContext() {
		context.testedClazz = null;
		context.testedObject = null;
		context.testedMethod = null;
	}

	private TestContext() {
	}

	/**
	 * 当前测试类名称
	 * 
	 * @return
	 */
	public static String currTestedClazzName() {
		if (context.testedClazz == null) {
			throw new RuntimeException("tested class can't be null.");
		} else {
			return context.testedClazz.getName();
		}
	}

	public static String currTestedMethodName() {
		if (context.testedClazz == null) {
			throw new RuntimeException("tested class can't be null.");
		} else {
			return context.testedClazz.getName() + "."
					+ (context.testedMethod == null ? "<init>" : context.testedMethod.getName());
		}
	}

	/**
	 * 当前测试类的类名称
	 * 
	 * @return
	 */
	public static Class currTestedClazz() {
		if (context.testedClazz == null) {
			throw new RuntimeException("tested class can't be null.");
		} else {
			return context.testedClazz;
		}
	}

	/**
	 * 当前测试类
	 * 
	 * @return
	 */
	public static Object currTestedObject() {
		if (context.testedObject == null) {
			throw new RuntimeException("tested object can't be null.");
		} else {
			return context.testedObject;
		}
	}

	/**
	 * 当前正在运行的测试类
	 */
	private Class testedClazz;

	/**
	 * 当前正在运行的测试类实例
	 */
	private Object testedObject;

	/**
	 * 当前正在运行的测试方法
	 */
	private Method testedMethod;

	/**
	 * 当前测试方法的事务模式
	 * 
	 * @param testedObject
	 * @param testedMethod
	 * @return
	 */
	public static TransactionMode getTransactionMode() {
		if (context.testedObject == null || context.testedMethod == null) {
			return TransactionMode.DISABLED;
		}
		TransactionMode transactionMode = getMethodOrClassLevelAnnotationProperty(Transactional.class, "value",
				DEFAULT, context.testedMethod, context.testedClazz);
		if (transactionMode == TransactionMode.DEFAULT) {
			String mode = ConfigurationHelper.getString(TRANSACTIONAL_MODE_DEFAULT, "DISABLED");
			transactionMode = TransactionMode.valueOf(mode.toUpperCase());
		}
		if (transactionMode == null || transactionMode == TransactionMode.DEFAULT) {
			return TransactionMode.DISABLED;
		} else {
			return transactionMode;
		}
	}

	/**
	 * 当前测试方法上的事务是否生效
	 * 
	 * @param testedObject
	 * @param testedMethod
	 * @return
	 */
	public static boolean isTransactionsEnabled() {
		if (context.testedObject == null || context.testedMethod == null) {
			return false;
		} else {
			TransactionMode mode = getTransactionMode();
			return mode == COMMIT || mode == ROLLBACK;
		}
	}

	private static ThreadLocal<TransactionManager> localTransactionManager = new ThreadLocal<TransactionManager>();

	/**
	 * 返回当前线程的事务
	 * 
	 * @return
	 */
	public static TransactionManager getLocalTransactionManager() {
		TransactionManager transaction = localTransactionManager.get();
		return transaction;
	}

	/**
	 * 设置当前线程的事务
	 */
	public static void setLocalTransactionManager() {
		TransactionManager transaction = new TransactionManager();
		localTransactionManager.set(transaction);
	}

	/**
	 * 移除当前线程的事务
	 */
	public static void removeLocalTransactionManager() {
		localTransactionManager.remove();
	}

	/**
	 * AbstractApplicationContext类<br>
	 * <br>
	 * <br>
	 * 第一个Object是测试类实例<br>
	 * 第二个Object是AbstractApplicationContext实例，这里定义为Object类型是为了兼容没有使用spring容器的测试
	 */
	private static Map<Object, Object> springContextMap = new HashMap<Object, Object>();

	/**
	 * 存放当前测试实例下的spring application context实例
	 * 
	 * @param springContext
	 */
	public static void setSpringContext(Object springContext) {
		if (springContext == null) {
			log4j.info("no spring application context for test:" + currTestedClazzName());
			return;
		}
		if (context.testedObject == null) {
			throw new RuntimeException("the tested object can't be null.");
		} else {
			springContextMap.put(context.testedObject, springContext);
			SpringBeanInjector.injectSpringBeans(springContext, context.testedObject);
		}
	}

	/**
	 * 获取当前测试实例的spring application context实例
	 * 
	 * @return
	 */
	public static Object getSpringContext() {
		if (context.testedObject == null) {
			throw new RuntimeException("the tested object can't be null.");
		} else {
			return springContextMap.get(context.testedObject);
		}
	}

	/**
	 * 释放spring context，清空测试类中的spring bean实例
	 */
	public static void removeSpringContext() {
		if (context.testedObject == null) {
			throw new RuntimeException("the tested object can't be null.");
		} else {
			SpringBeanCleaner.cleanSpringBeans(context.testedObject);
			Object spring = springContextMap.remove(context.testedObject);
			if (spring != null) {
				SpringModuleHelper.closeSpringContext(spring);
			}
		}
	}
}

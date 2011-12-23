package org.jtester.core;

import static org.jtester.annotations.Transactional.TransactionMode.COMMIT;
import static org.jtester.annotations.Transactional.TransactionMode.DEFAULT;
import static org.jtester.annotations.Transactional.TransactionMode.ROLLBACK;
import static org.jtester.module.core.ConfigurationConst.TRANSACTIONAL_MODE_DEFAULT;
import static org.jtester.utility.AnnotationUtils.getMethodOrClassLevelAnnotationProperty;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jtester.annotations.Transactional;
import org.jtester.annotations.Transactional.TransactionMode;
import org.jtester.core.context.TransactionManager;
import org.jtester.exception.ExceptionWrapper;
import org.jtester.exception.MultipleException;
import org.jtester.module.core.helper.ConfigurationHelper;
import org.jtester.module.core.helper.SpringModuleHelper;
import org.jtester.module.spring.JTesterBeanFactory;
import org.jtester.module.spring.JTesterSpringContext;
import org.jtester.module.spring.strategy.cleaner.SpringBeanCleaner;
import org.jtester.utility.LogHelper;

/**
 * jTester测试上下文信息<br>
 * 单列模式 <br>
 */
@SuppressWarnings("rawtypes")
public class TestedContext {

	private final static TestedContext context = new TestedContext();

	public final static TestedContext context() {
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
		// context.testedObject = null;
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
		context.errors = new ArrayList<Throwable>();
	}

	/**
	 * 用来收集测试过程中可能被其它异常吞噬的异常
	 * 
	 * @param e
	 */
	public static final void addThrowable(Throwable e) {
		if (context.errors == null) {
			context.errors = new ArrayList<Throwable>();
		}
		context.errors.add(e);
	}

	/**
	 * 封装多个异常，如果当前测试没有记录的历史异常，则直接返回cause<br>
	 * 否则返回一个MutipleException
	 * 
	 * @param cause
	 * @return
	 */
	public static RuntimeException getMultipleException(Throwable cause) {
		if (context.errors == null || context.errors.size() == 0) {
			return ExceptionWrapper.wrapWithRuntimeException(cause);
		}
		MultipleException exception = new MultipleException(cause);
		for (Throwable e : context.errors) {
			exception.addException(e);
		}
		return exception;
	}

	/**
	 * 清空测试上下文信息
	 */
	public final static void cleanContext() {
		context.testedClazz = null;
		context.testedObject = null;
		context.testedMethod = null;
		context.errors = null;
	}

	private TestedContext() {
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
	 * 异常收集
	 */
	private List<Throwable> errors;

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

	private static Map<Class, ThreadLocal<TransactionManager>> localTransactionManager = new ConcurrentHashMap<Class, ThreadLocal<TransactionManager>>();

	/**
	 * 返回当前线程的事务
	 * 
	 * @return
	 */
	public static TransactionManager getLocalTransactionManager() {
		Class testedClazz = TestedContext.currTestedClazz();
		ThreadLocal<TransactionManager> clazzLocal = localTransactionManager.get(testedClazz);
		if (clazzLocal == null) {
			return null;
		} else {
			TransactionManager transaction = clazzLocal.get();
			return transaction;
		}
	}

	/**
	 * 设置当前线程的事务
	 */
	public static void setLocalTransactionManager() {
		Class testedClazz = TestedContext.currTestedClazz();
		TransactionManager transaction = new TransactionManager();
		ThreadLocal<TransactionManager> clazzLocal = new ThreadLocal<TransactionManager>();
		{
			clazzLocal.set(transaction);
		}
		localTransactionManager.put(testedClazz, clazzLocal);
	}

	/**
	 * 移除当前线程的事务
	 */
	public static void removeLocalTransactionManager() {
		Class testedClazz = TestedContext.currTestedClazz();
		ThreadLocal<TransactionManager> clazzLocal = localTransactionManager.remove(testedClazz);
		if (clazzLocal == null) {
			return;
		}
		try {
			// 强行结束已经存在的事务, 如果有？
			TransactionManager transactionManager = clazzLocal.get();
			transactionManager.forceEnd();
		} catch (Throwable e) {
			e.printStackTrace();
			// do nothing
		}
		clazzLocal.remove();
	}

	/**
	 * AbstractApplicationContext类<br>
	 * <br>
	 * <br>
	 * 第一个Object是测试类实例<br>
	 * 第二个Object是AbstractApplicationContext实例，这里定义为Object类型是为了兼容没有使用spring容器的测试
	 */
	private static Map<Class, JTesterSpringContext> springBeanFactories = new HashMap<Class, JTesterSpringContext>();

	/**
	 * 存放当前测试实例下的spring application context实例
	 * 
	 * @param springContext
	 */
	public static void setSpringContext(JTesterSpringContext springContext) {
		if (springContext == null) {
			LogHelper.info("no spring application context for test:" + currTestedClazzName());
			return;
		}
		if (TestedContext.currTestedClazz() == null) {
			throw new RuntimeException("the tested object can't be null.");
		} else {
			springBeanFactories.put(currTestedClazz(), springContext);
		}
	}

	/**
	 * 获取当前测试实例的spring application context实例
	 * 
	 * @return
	 */
	public static JTesterBeanFactory getSpringBeanFactory() {
		if (TestedContext.currTestedClazz() == null) {
			throw new RuntimeException("the tested object can't be null.");
		} else {
			JTesterSpringContext springContext = springBeanFactories.get(currTestedClazz());
			return springContext == null ? null : springContext.getJTesterBeanFactory();
		}
	}

	public static JTesterSpringContext getSpringContext() {
		if (TestedContext.currTestedClazz() == null) {
			throw new RuntimeException("the tested object can't be null.");
		} else {
			JTesterSpringContext springContext = springBeanFactories.get(currTestedClazz());
			return springContext;
		}
	}

	/**
	 * 释放spring context，清空测试类中的spring bean实例
	 */
	public static void removeSpringContext() {
		if (TestedContext.currTestedClazz() == null) {
			throw new RuntimeException("the tested object can't be null.");
		} else {
			SpringBeanCleaner.cleanSpringBeans(context.testedObject);
			Object springContext = springBeanFactories.remove(currTestedClazz());
			if (springContext != null) {
				SpringModuleHelper.closeSpringContext(springContext);
			}
		}
	}
}

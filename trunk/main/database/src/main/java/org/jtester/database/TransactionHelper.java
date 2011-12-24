package org.jtester.database;

import static org.jtester.core.ConfigurationConst.TRANSACTIONAL_MODE_DEFAULT;
import static org.jtester.helper.AnnotationHelper.getMethodOrClassLevelAnnotationProperty;
import static org.jtester.annotations.Transactional.TransactionMode.DISABLED;
import static org.jtester.annotations.Transactional.TransactionMode.COMMIT;
import static org.jtester.annotations.Transactional.TransactionMode.ROLLBACK;
import static org.jtester.annotations.Transactional.TransactionMode.DEFAULT;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jtester.annotations.Transactional;
import org.jtester.annotations.Transactional.TransactionMode;
import org.jtester.core.TestedContext;
import org.jtester.core.helper.ConfigurationHelper;
import org.jtester.helper.LogHelper;
import org.jtester.module.core.helper.SpringModuleHelper;
import org.jtester.module.spring.JTesterBeanFactory;
import org.jtester.module.spring.JTesterSpringContext;
import org.jtester.module.spring.strategy.cleaner.SpringBeanCleaner;

public class TransactionHelper {
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
			LogHelper.info("no spring application context for test:" + TestedContext.currTestedClazzName());
			return;
		}
		if (TestedContext.currTestedClazz() == null) {
			throw new RuntimeException("the tested object can't be null.");
		} else {
			springBeanFactories.put(TestedContext.currTestedClazz(), springContext);
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
			JTesterSpringContext springContext = springBeanFactories.get(TestedContext.currTestedClazz());
			return springContext == null ? null : springContext.getJTesterBeanFactory();
		}
	}

	public static JTesterSpringContext getSpringContext() {
		if (TestedContext.currTestedClazz() == null) {
			throw new RuntimeException("the tested object can't be null.");
		} else {
			JTesterSpringContext springContext = springBeanFactories.get(TestedContext.currTestedClazz());
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
			SpringBeanCleaner.cleanSpringBeans(TestedContext.currTestedObject());
			Object springContext = springBeanFactories.remove(TestedContext.currTestedClazz());
			if (springContext != null) {
				SpringModuleHelper.closeSpringContext(springContext);
			}
		}
	}

	/**
	 * 当前测试方法的事务模式
	 * 
	 * @param testedObject
	 * @param testedMethod
	 * @return
	 */
	public static TransactionMode getTransactionMode() {
		if (TestedContext.currTestedObject() == null || TestedContext.currTestedMethod() == null) {
			return TransactionMode.DISABLED;
		}
		TransactionMode transactionMode = getMethodOrClassLevelAnnotationProperty(Transactional.class, "value",
				DEFAULT, TestedContext.currTestedMethod(), TestedContext.currTestedClazz());
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
		if (TestedContext.currTestedObject() == null || TestedContext.currTestedMethod() == null) {
			return false;
		} else {
			TransactionMode mode = getTransactionMode();
			return mode == COMMIT || mode == ROLLBACK;
		}
	}
}

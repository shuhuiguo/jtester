package org.jtester.database;

import static org.jtester.annotations.Transactional.TransactionMode.COMMIT;
import static org.jtester.annotations.Transactional.TransactionMode.DEFAULT;
import static org.jtester.annotations.Transactional.TransactionMode.ROLLBACK;
import static org.jtester.core.ConfigurationConst.TRANSACTIONAL_MODE_DEFAULT;
import static org.jtester.helper.AnnotationHelper.getMethodOrClassLevelAnnotationProperty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jtester.annotations.Transactional;
import org.jtester.annotations.Transactional.TransactionMode;
import org.jtester.core.TestedContext;
import org.jtester.helper.ConfigurationHelper;

@SuppressWarnings("rawtypes")
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

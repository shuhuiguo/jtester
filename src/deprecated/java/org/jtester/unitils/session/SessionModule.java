package org.jtester.unitils.session;

import java.lang.reflect.Method;
import java.util.Properties;

import org.jtester.module.core.Module;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.unitils.core.TestListener;

public class SessionModule implements Module {

	public void afterInit() {
	}

	public TestListener getTestListener() {
		return new SessionTestListener();
	}

	public void init(Properties configuration) {
	}

	protected class SessionTestListener extends TestListener {
		protected PlatformTransactionManager transactionManager;

		protected TransactionStatus transactionStatus;

//		private boolean isApplicationContextConfiguredForThisTest = false;
//
//		private Session session;

		@Override
		public void beforeTestMethod(Object testObject, Method testMethod) {
			super.beforeTestMethod(testObject, testMethod);

//			session = AnnotationUtils.getMethodOrClassLevelAnnotation(
//					Session.class, testMethod, testObject.getClass());
//			isApplicationContextConfiguredForThisTest = false;
//			if (session != null && session.inSession()) {
//				SpringModule springModule = (SpringModule) Unitils
//						.getInstance().getModulesRepository().getModuleOfType(
//								ModuleClazz.springModule());
//				isApplicationContextConfiguredForThisTest = springModule
//						.isApplicationContextConfiguredFor(testObject);
//				if (isApplicationContextConfiguredForThisTest) {
//					transactionManager = (PlatformTransactionManager) springModule
//							.getApplicationContext(testObject).getBean(
//									session.transactionBeanName());
//					transactionStatus = transactionManager
//							.getTransaction(new DefaultTransactionDefinition());
//				}
//			}
		}

		@Override
		public void afterTestMethod(Object testObject, Method testMethod,
				Throwable testThrowable) {
			super.afterTestMethod(testObject, testMethod, testThrowable);
//			if (!isApplicationContextConfiguredForThisTest) {
//				return;
//			}
//			if (session.commit() == false || transactionStatus.isRollbackOnly()) {
//				transactionManager.rollback(this.transactionStatus);
//			} else {
//				transactionManager.commit(this.transactionStatus);
//			}
		}
	}
}

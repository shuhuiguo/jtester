package org.jtester.unitils.session;

import java.lang.reflect.Method;
import java.util.Properties;

import org.jtester.module.TestListener;
import org.jtester.module.core.Module;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

public class SessionModule implements Module {
	public void init() {
		// TODO Auto-generated method stub
	}

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

		// private boolean isApplicationContextConfiguredForThisTest = false;
		//
		// private Session session;

		@Override
		public void beforeMethodRunning(Object testObject, Method testMethod) throws Exception {
			super.beforeMethodRunning(testObject, testMethod);

			// session = AnnotationUtils.getMethodOrClassLevelAnnotation(
			// Session.class, testMethod, testObject.getClass());
			// isApplicationContextConfiguredForThisTest = false;
			// if (session != null && session.inSession()) {
			// SpringModule springModule = (SpringModule) Unitils
			// .getInstance().getModulesRepository().getModuleOfType(
			// ModuleClazz.springModule());
			// isApplicationContextConfiguredForThisTest = springModule
			// .isApplicationContextConfiguredFor(testObject);
			// if (isApplicationContextConfiguredForThisTest) {
			// transactionManager = (PlatformTransactionManager) springModule
			// .getApplicationContext(testObject).getBean(
			// session.transactionBeanName());
			// transactionStatus = transactionManager
			// .getTransaction(new DefaultTransactionDefinition());
			// }
			// }
		}

		@Override
		public void afterMethodRunned(Object testObject, Method testMethod, Throwable testThrowable) throws Exception {
			super.afterMethodRunned(testObject, testMethod, testThrowable);
			// if (!isApplicationContextConfiguredForThisTest) {
			// return;
			// }
			// if (session.commit() == false ||
			// transactionStatus.isRollbackOnly()) {
			// transactionManager.rollback(this.transactionStatus);
			// } else {
			// transactionManager.commit(this.transactionStatus);
			// }
		}

		@Override
		protected String getName() {
			return "SessionTestListener";
		}
	}
}

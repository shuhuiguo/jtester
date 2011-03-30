package org.jtester.module.database.transaction;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 测试类的事务管理
 * 
 * @author darui.wudr
 * 
 */
public class TestedObjectTransaction {
	private boolean active;

	private TransactionStatus transactionStatus;

	private PlatformTransactionManager transactionManager;

	public TestedObjectTransaction() {
		this.active = false;
		this.transactionManager = null;
		this.transactionStatus = null;
	}

	public void setTransaction(PlatformTransactionManager transactionManager) {
		this.active = true;
		this.transactionManager = transactionManager;
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition(
				TransactionDefinition.PROPAGATION_REQUIRED);
		this.transactionStatus = transactionManager.getTransaction(transactionDefinition);
	}

	public void removeTransaction() {
		this.active = false;
		this.transactionManager = null;
		this.transactionStatus = null;
	}

	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public boolean isActive() {
		return active;
	}

	/**
	 * 事务提交
	 */
	public void commit() {
		this.transactionManager.commit(this.transactionStatus);
	}

	public void rollback() {
		this.transactionManager.rollback(this.transactionStatus);
	}
}

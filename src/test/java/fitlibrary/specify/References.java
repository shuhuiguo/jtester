/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify;

import java.util.ArrayList;
import java.util.List;

import fitlibrary.CalculateFixture;
import fitlibrary.DoFixture;
import fitlibrary.SetUpFixture;
import fitlibrary.ref.EntityReference;
import fitlibrary.traverse.DomainAdapter;

public class References extends DoFixture {
	protected List<Account> accounts = new ArrayList<Account>();
	protected List<AccountRef> accountReferences = new ArrayList<AccountRef>();

	public References() {
		setSystemUnderTest(new Sut());
	}

	public CalculateFixture calculateReference(String entityName) {
		return new CalculateReference(entityName);
	}

	public SetUpFixture setUpAccounts() {
		return new SetUpAccounts();
	}

	public SetUpFixture setUpTransactionsFor(Account account) {
		return new SetUpTransAction(account);
	}

	public boolean addTo(int amount, Account account) {
		account.add(amount);
		return true;
	}

	public List<Account> accounts() {
		return accounts;
	}

	public Account findAccount(int index) {
		if (index >= accounts.size())
			throw new RuntimeException("Unavailable: Account#" + index);
		return accounts.get(index);
	}

	public Account findAccount(String text) {
		if (text.equals("1st"))
			return findAccount(0);
		throw new RuntimeException("Unavailable: Account from '" + text + "'");
	}

	public Account with(Account account) {
		return account;
	}

	public DoFixture withAccount(Account account) {
		return new AccountDoFixture(account);
	}

	public Object showAccount(Account arg) {
		return EntityReference.reference(arg, accounts);
	}

	public Person withPerson(Person person) {
		return person;
	}

	public SetUpAccountRefs getSetUpAccountReferences() {
		return new SetUpAccountRefs();
	}

	public List<AccountRef> checkAccountReferences() {
		return accountReferences;
	}

	public class CalculateReference extends CalculateFixture {
		private EntityReference entityReference;

		public CalculateReference(String entityName) {
			this.entityReference = EntityReference.create(entityName);
		}

		public int indexText(String text) {
			return entityReference.getIndex(text);
		}
	}

	public class SetUpAccounts extends SetUpFixture {
		public void owing(int owing) {
			accounts.add(new Account(owing));
		}
	}

	public static class Account {
		private int owing;
		private List<Transaction> transactions = new ArrayList<Transaction>();

		public Account(int owing) {
			this.owing = owing;
		}

		public void add(int amount) {
			owing += amount;
		}

		public int getOwing() {
			return owing;
		}

		public void add(Transaction details) {
			transactions.add(details);
		}

		public List<Transaction> getTransactions() {
			return transactions;
		}
	}

	public static class Person {
		public String getName() {
			return "ward";
		}
	}

	public static class Sut implements DomainAdapter {
		public Person findPerson(int index) {
			return new Person();
		}

		// @Override
		public Object getSystemUnderTest() {
			return null;
		}
	}

	public static class SetUpTransAction extends SetUpFixture {
		private Account account;

		public SetUpTransAction(Account account) {
			this.account = account;
		}

		public void details(String details) {
			account.add(new Transaction(details));
		}
	}

	public static class Transaction {
		private String details;

		public Transaction(String details) {
			this.details = details;
		}

		public String getDetails() {
			return details;
		}
	}

	public static class AccountDoFixture extends DoFixture {
		private Account account;

		public AccountDoFixture(Account account) {
			super(account);
			this.account = account;
		}

		public Transaction withTransaction(Transaction transaction) {
			return transaction;
		}

		public Transaction findTransaction(int index) {
			List<Transaction> transactions = account.getTransactions();
			if (index >= transactions.size())
				throw new RuntimeException("Unavailable: Transaction#" + index);
			return transactions.get(index);
		}

		public Account this_() {
			return account;
		}
	}

	public class SetUpAccountRefs extends SetUpFixture {
		public void account(Account account) {
			accountReferences.add(new AccountRef(account));
		}
	}

	public static class AccountRef {
		private Account account;

		public AccountRef(Account account) {
			this.account = account;
		}

		public Account getAccount() {
			return account;
		}
	}
}

/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify.suite;

import fitlibrary.DoFixture;
import fitlibrary.suite.SuiteFixture;
import fitlibrary.traverse.DomainAdapter;
import fitlibrary.traverse.workflow.DoTraverse;

public class SimpleSetUp extends SuiteFixture {
	private int count = 0;
	private int suiteSetUpCount, suiteTearDownCount;
	private int setUpCount, tearDownCount;
	protected int totalLocalSetUps = 0, totalLocalTearDowns = 0;

	public void suiteSetUp() {
		suiteSetUpCount++;
	}

	public void suiteTearDown() {
		suiteTearDownCount++;
	}

	public void setUp() {
		setUpCount++;
	}

	public void tearDown() {
		tearDownCount++;
	}

	public int getSuiteSetUpCount() {
		return suiteSetUpCount;
	}

	public int getSuiteTearDownCount() {
		return suiteTearDownCount;
	}

	public int getSetUpCount() {
		return setUpCount;
	}

	public int getTearDownCount() {
		return tearDownCount;
	}

	public int getTotalLocalSetUps() {
		return totalLocalSetUps;
	}

	public int getTotalLocalTearDowns() {
		return totalLocalTearDowns;
	}

	public DoTraverse aFixture() {
		return new DoTraverse(new Sut(count++));
	}

	public MyOtherDoFixture anotherFixture() {
		return new MyOtherDoFixture();
	}

	public void countIs(int newCount) {
		this.count = newCount;
	}

	public class Sut implements DomainAdapter {
		private int count2;
		private int setUps = 0;

		public Sut(int count) {
			this.count2 = count;
		}

		public boolean andSomeImmediateAction() {
			return true;
		}

		public boolean andMore() {
			return true;
		}

		public boolean andMoreBesides() {
			return true;
		}

		public int getCount() {
			return count2;
		}

		public void setUp() {
			setUps++;
			totalLocalSetUps++;
		}

		public void tearDown() {
			totalLocalTearDowns++;
		}

		public int localSetUpCount() {
			return setUps;
		}

		// @Override
		public Object getSystemUnderTest() {
			return null;
		}
	}

	public static class MyOtherDoFixture extends DoFixture {
		public boolean actionOnThat() {
			return true;
		}
	}
}

package fitlibrary.specify.suite;

import fitlibrary.suite.SuiteFixture;
import fitlibrary.traverse.DomainAdapter;
import fitlibrary.traverse.workflow.DoTraverse;

public class SetUpWithDomain extends SuiteFixture {
	protected int totalLocalSetUps = 0, totalLocalTearDowns = 0;

	public int getTotalLocalSetUps() {
		return totalLocalSetUps;
	}

	public int getTotalLocalTearDowns() {
		return totalLocalTearDowns;
	}

	public DoTraverse domain() {
		DoTraverse doTraverse2 = new DoTraverse(new Sut());
		return doTraverse2;
	}

	public class Sut implements DomainAdapter {
		public void setUp() {
			totalLocalSetUps++;
		}

		public void tearDown() {
			totalLocalTearDowns++;
		}

		public void act() {
			//
		}

		// @Override
		public Object getSystemUnderTest() {
			return null;
		}
	}
}

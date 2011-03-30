package fitlibrary.batch.testRun;

import java.util.Set;

import fit.Counts;
import fitlibrary.batch.trinidad.SingleTestResult;
import fitlibrary.batch.trinidad.TestDescriptor;
import fitlibrary.batch.trinidad.TestResult;

public class RetryAwareFitLibraryTestEngine extends FitLibraryTestEngine {
	Set<String> passedList;

	public RetryAwareFitLibraryTestEngine(Set<String> passedList) {
		super();
		this.passedList = passedList;
	}

	@Override
	public TestResult runTest(TestDescriptor test) {
		if (passedList.contains(test.getName())) {
			return new SingleTestResult(new Counts(), test.getName() + " (skipping already passed)", "", 0);
		}

		TestResult result = super.runTest(test);

		if (testPassed(result) && isStandardTest(result)) {
			passedList.add(result.getName());
		}

		return result;
	}

	private boolean testPassed(TestResult result) {
		return result.getCounts().exceptions + result.getCounts().wrong == 0;
	}

	private boolean isStandardTest(TestResult result) {
		return !(result.getName().contains("SuiteSetUp") || result.getName().contains("SuiteTearDown"));
	}
}

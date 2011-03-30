package fitnesse.responders.run.formatters;

import util.TimeMeasurement;
import fitnesse.FitNesseContext;
import fitnesse.responders.run.CompositeExecutionLog;
import fitnesse.responders.run.ResultsListener;
import fitnesse.responders.run.TestSummary;
import fitnesse.responders.run.TestSystem;
import fitnesse.wiki.WikiPage;

public abstract class BaseFormatter implements ResultsListener {

	protected WikiPage page = null;
	protected FitNesseContext context;
	public static final BaseFormatter NULL = new NullFormatter();
	public static int finalErrorCount = 0;
	protected int testCount = 0;
	protected int failCount = 0;

	public abstract void writeHead(String pageType) throws Exception;

	protected BaseFormatter() {
	}

	protected BaseFormatter(FitNesseContext context, final WikiPage page) {
		this.page = page;
		this.context = context;
	}

	protected WikiPage getPage() {
		return page;
	}

	// @Override
	public void errorOccured() {
		try {
			allTestingComplete(new TimeMeasurement().start().stop());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Override
	public void allTestingComplete(TimeMeasurement totalTimeMeasurement) throws Exception {
		finalErrorCount = failCount;
	}

	// @Override
	public void announceNumberTestsToRun(int testsToRun) {
	}

	// @Override
	public void testComplete(WikiPage test, TestSummary summary, TimeMeasurement timeMeasurement) throws Exception {
		testCount++;
		if (summary.wrong > 0) {
			failCount++;
		}
		if (summary.exceptions > 0) {
			failCount++;
		}
	}

	public void addMessageForBlankHtml() throws Exception {
	}

	public int getErrorCount() {
		return 0;
	}

}

class NullFormatter extends BaseFormatter {
	NullFormatter() {
		super(null, null);
	}

	protected WikiPage getPage() {
		return null;
	}

	@Override
	public void announceNumberTestsToRun(int testsToRun) {
	}

	@Override
	public void errorOccured() {
	}

	// @Override
	public void setExecutionLogAndTrackingId(String stopResponderId, CompositeExecutionLog log) throws Exception {
	}

	// @Override
	public void testSystemStarted(TestSystem testSystem, String testSystemName, String testRunner) throws Exception {
	}

	// @Override
	public void newTestStarted(WikiPage test, TimeMeasurement timeMeasurement) throws Exception {
	}

	// @Override
	public void testOutputChunk(String output) throws Exception {
	}

	@Override
	public void testComplete(WikiPage test, TestSummary testSummary, TimeMeasurement timeMeasurement) throws Exception {
	}

	@Override
	public void writeHead(String pageType) throws Exception {
	}
}

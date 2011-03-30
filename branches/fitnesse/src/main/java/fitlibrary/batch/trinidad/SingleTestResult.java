package fitlibrary.batch.trinidad;

import fit.Counts;

public class SingleTestResult implements TestResult {
	private Counts counts;
	private String name;
	private String content;
	private long durationMillis;

	public SingleTestResult(Counts counts, String name, String content, long durationMillis) {
		this.counts = counts;
		this.name = name;
		this.content = content;
		this.durationMillis = durationMillis;
	}

	// @Override
	public Counts getCounts() {
		return this.counts;
	}

	// @Override
	public String getName() {
		return this.name;
	}

	// @Override
	public String getContent() {
		return this.content;
	}

	// @Override
	public long durationMillis() {
		return durationMillis;
	}

	// @Override
	public String toString() {
		return "SingleTestResult['" + name + "', '" + content + "', '" + counts + "', '" + durationMillis + "']";
	}
}
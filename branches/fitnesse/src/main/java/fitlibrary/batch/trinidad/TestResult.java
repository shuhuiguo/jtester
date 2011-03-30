/*
 * Adapted from Gojko Adsic's trinidad to add parallelism and changes to the report
 */
package fitlibrary.batch.trinidad;

import fit.Counts;

public interface TestResult {
	Counts getCounts();

	String getName();

	String getContent();

	long durationMillis();
}
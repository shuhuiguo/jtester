/*
 * Adapted from Gojko Adsic's trinidad to add parallelism and changes to the report
 */
package fitlibrary.batch.trinidad;

import java.io.File;
import java.io.IOException;

public interface TestResultRepository {
	void recordTestResult(TestResult paramTestResult) throws IOException;

	void addFile(File paramFile, String paramString) throws IOException;
}

/*
 * Adapted from Gojko Adsic's trinidad to add parallelism and changes to the report
 */
package fitlibrary.batch.trinidad;

public interface TestEngine {
	TestResult runTest(TestDescriptor paramTestDescriptor);
}
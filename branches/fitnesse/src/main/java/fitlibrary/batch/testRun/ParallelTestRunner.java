/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.batch.testRun;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;

import fit.Counts;
import fitlibrary.batch.fitnesseIn.ParallelFitNesseRepository;
import fitlibrary.batch.fitnesseIn.ParallelTestRepository;
import fitlibrary.batch.resultsOut.FolderTestResultRepository;
import fitlibrary.batch.resultsOut.ParallelFolderTestResultRepository;
import fitlibrary.batch.resultsOut.ParallelTestResultRepository;
import fitlibrary.batch.resultsOut.SuiteResult;
import fitlibrary.batch.trinidad.TestDescriptor;
import fitlibrary.batch.trinidad.TestEngine;
import fitlibrary.batch.trinidad.TestResult;

public class ParallelTestRunner {
	private ParallelTestRepository repository;
	private TestEngine testEngine;
	private ParallelTestResultRepository resultRepository;

	public ParallelTestRunner(ParallelTestRepository repository, TestEngine testEngine, String outputPath,
			boolean showPasses, String suiteName, boolean junitXMLOutput) throws IOException {
		this(repository, testEngine, new ParallelFolderTestResultRepository(new FolderTestResultRepository(outputPath,
				suiteName, System.out, showPasses, junitXMLOutput), Executors.newSingleThreadExecutor()));
	}

	public ParallelTestRunner(ParallelTestRepository repository, TestEngine testRunner,
			ParallelTestResultRepository resultRepository) throws IOException {
		this.repository = repository;
		this.testEngine = testRunner;
		this.resultRepository = resultRepository;
		repository.prepareResultRepository(resultRepository);
	}

	public Counts runTest(String testUrl) throws IOException {
		TestResult testResult = testEngine.runTest(repository.getTest(testUrl));
		resultRepository.recordTestResult(testResult);
		return testResult.getCounts();
	}

	public Counts runSuite(String suite, SuiteResult suiteResult) throws IOException, InterruptedException {
		BlockingQueue<TestDescriptor> queue = repository.getSuite(suite);
		while (true) {
			TestDescriptor test = queue.take();
			if (ParallelFitNesseRepository.isSentinel(test))
				break;
			if (test.getName().equals("Exception"))
				throw new IOException(test.getContent());
			TestResult result = testEngine.runTest(test);
			suiteResult.append(result);
			resultRepository.recordTestResult(result);
		}
		resultRepository.recordTestResult(suiteResult);
		resultRepository.closeAndWaitForCompletion();
		return suiteResult.getCounts();
	}
}

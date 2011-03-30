/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.batch.resultsOut;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

import fitlibrary.batch.trinidad.TestResult;
import fitlibrary.batch.trinidad.TestResultRepository;

public class ParallelFolderTestResultRepository implements ParallelTestResultRepository {
	static final TestResult SENTINEL = new ParallelSuiteResult("FINISH", false);
	final TestResultRepository testResultRepository;
	final BlockingQueue<TestResult> queue = new LinkedBlockingQueue<TestResult>();
	final CountDownLatch endGate = new CountDownLatch(1);

	public ParallelFolderTestResultRepository(final TestResultRepository testResultRepository, Executor executor) {
		this.testResultRepository = testResultRepository;
		executor.execute(new Runnable() {
			// @Override
			public void run() {
				try {
					while (true) {
						TestResult result = queue.take();
						if (result == SENTINEL)
							break;
						testResultRepository.recordTestResult(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					endGate.countDown();
				}
			}
		});
	}

	// @Override
	public void addFile(File f, String relativeFilePath) throws IOException {
		testResultRepository.addFile(f, relativeFilePath);
	}

	// @Override
	public void recordTestResult(TestResult result) throws IOException {
		try {
			queue.put(result);
		} catch (InterruptedException e) {
			throw new IOException(e.getMessage());
		}
	}

	// @Override
	public void closeAndWaitForCompletion() throws InterruptedException {
		queue.add(SENTINEL);
		endGate.await();
	}
}

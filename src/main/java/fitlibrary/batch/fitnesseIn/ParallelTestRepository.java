/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.batch.fitnesseIn;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import fitlibrary.batch.trinidad.TestDescriptor;
import fitlibrary.batch.trinidad.TestResultRepository;

public interface ParallelTestRepository {
	public void setUri(String uri, int port) throws IOException;

	public TestDescriptor getTest(String name) throws IOException;

	public BlockingQueue<TestDescriptor> getSuite(String name) throws IOException;

	public void prepareResultRepository(TestResultRepository resultRepository) throws IOException;
}

/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.polling;

import org.apache.log4j.Logger;

import fitlibrary.log.FitLibraryLogger;

public class Eventually {
	private static Logger logger = FitLibraryLogger.getLogger(Eventually.class);
	protected static final long MIN_SLEEP = 2;
	protected static final long MAX_SLEEP = 10;
	protected static final long DIV_SLEEP = 50;
	private final long timeout;
	private long start = now();
	private final long sleepPeriod;

	public Eventually(long timeout) {
		this.timeout = timeout;
		this.sleepPeriod = Math.min(MAX_SLEEP, Math.max(MIN_SLEEP, timeout / DIV_SLEEP));
	}

	public PassFail poll(PollForPass poll) throws Exception {
		PassFail answer = new PassFail(false, null);
		start = now();
		while (true) {
			answer = poll.result();
			if (answer.hasPassed) {
				if (delay() > 0)
					logger.trace("Passed after " + delay() + " milliseconds");
				return answer;
			}
			if (timedOut()) // Avoid the timeout happening before any call
				break;
			sleep();
		}
		logger.trace("Failed after " + delay() + " milliseconds");
		return answer;
	}

	private boolean timedOut() {
		return delay() > timeout;
	}

	private long delay() {
		return now() - start;
	}

	private void sleep() {
		try {
			Thread.sleep(sleepPeriod);
		} catch (Exception e) {
			//
		}
	}

	private static long now() {
		return System.currentTimeMillis();
	}
}

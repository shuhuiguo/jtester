/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.polling;

public class PassFail {
	public final boolean hasPassed;
	public final Object result;
	private long delay;

	public long getDelay() {
		return delay;
	}

	public PassFail(boolean hasPassed, Object result) {
		this.hasPassed = hasPassed;
		this.result = result;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}
}

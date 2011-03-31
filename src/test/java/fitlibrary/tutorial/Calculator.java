/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.tutorial;

public class Calculator {
	private int total = 0;

	public void givenTheTotalIs(int givenTotal) {
		this.total = givenTotal;
	}

	public void plus(int arg) {
		total += arg;
	}

	public void star(int arg) {
		total *= arg;
	}

	public boolean theTotalNowIs(int arg) {
		return total == arg;
	}

	public int theTotalNow() {
		return total;
	}
}

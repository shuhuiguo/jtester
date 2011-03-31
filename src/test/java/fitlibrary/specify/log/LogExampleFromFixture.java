/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/

package fitlibrary.specify.log;

import fitlibrary.DoFixture;
import fitlibrary.exception.FitLibraryShowException;
import fitlibrary.exception.FitLibraryShowException.Show;

public class LogExampleFromFixture extends DoFixture {
	public boolean actionThatPrints(String s) {
		System.out.println("Output "+s);
		return true;
	}
	public void actionThatShowsAfterTable(String s) {
		showAsAfterTable("My Log", s);
	}
	public void actionThatShows(String s) {
		show(s);
	}
	public void actionThatShowsOnError(String s) {
		throw new FitLibraryShowException(new Show(s));
	}
	public void actionThatLogs(String s) {
		logText(s);
	}
}

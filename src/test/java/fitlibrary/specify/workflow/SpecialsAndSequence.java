/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.specify.workflow;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.exception.FitLibraryShowException;
import fitlibrary.exception.FitLibraryShowException.Show;
import fitlibrary.exception.parse.BadNumberException;

public class SpecialsAndSequence {
	public int plus(int a, int b) {
		return a + b;
	}

	public String and(String a, String b) {
		return a + b;
	}

	public boolean or(boolean a, boolean b) {
		return a || b;
	}

	public boolean runtimeException(String a, String b) {
		throw new RuntimeException();
	}

	public boolean fitLibraryException(String a, String b) {
		throw new FitLibraryException(a);
	}

	public boolean fitLibraryShowException(String a, String b) {
		throw new FitLibraryShowException(new Show(a));
	}

	public boolean badNumberException(int a, String b) {
		throw new BadNumberException();
	}
}

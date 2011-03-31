/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.specify.access;

import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.traverse.RuntimeContextual;

public class CurrentRow implements RuntimeContextual {
	private RuntimeContextInternal runtime;

	// @Override
	public void setRuntimeContext(RuntimeContextInternal runtime) {
		this.runtime = runtime;
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}

	public void actionThatAddsAShowCell() {
		runtime.currentRow().addShow("hello");
	}

	public void actionThatPassesCell(int i) {
		runtime.cellAt(i).pass();
	}

	public void actionThatFailsCell(int i) {
		runtime.cellAt(i).fail("");
	}

	public void actionThatFailsCellWithActual(int i, String actual) {
		runtime.cellAt(i).fail(actual);
	}

	public void actionWithErrorInCellWithMessage(int i, String msg) {
		runtime.cellAt(i).error(msg);
	}
}

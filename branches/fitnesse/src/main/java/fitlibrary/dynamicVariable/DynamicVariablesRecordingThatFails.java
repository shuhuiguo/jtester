/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.dynamicVariable;

import java.io.IOException;

import fitlibrary.exception.FitLibraryException;

public class DynamicVariablesRecordingThatFails implements DynamicVariablesRecording {
	// @Override
	public void record(String key, String value) {
		throw new FitLibraryException("Recording file has not been created");
	}

	// @Override
	public void write() throws IOException {
		throw new FitLibraryException("Recording file has not been created");
	}

	// @Override
	public boolean isRecording() {
		return false;
	}
}

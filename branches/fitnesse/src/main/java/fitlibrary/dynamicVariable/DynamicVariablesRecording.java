/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.dynamicVariable;

import java.io.IOException;

public interface DynamicVariablesRecording {
	void record(String key, String value);

	void write() throws IOException;

	boolean isRecording();
}

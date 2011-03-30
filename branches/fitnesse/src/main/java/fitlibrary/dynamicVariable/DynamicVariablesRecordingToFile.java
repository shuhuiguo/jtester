/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.dynamicVariable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class DynamicVariablesRecordingToFile implements DynamicVariablesRecording {
	private String fileName;
	private Properties props = new Properties();

	public DynamicVariablesRecordingToFile(String fileName) {
		this.fileName = fileName;
	}

	// @Override
	public void record(String key, String value) {
		props.setProperty(key, value);
	}

	// @Override
	public void write() throws IOException {
		if (fileName.equals("pleaseThrowAnExceptionOnThisFile"))
			throw new IOException("Some file exception");
		Properties original = new Properties();
		collectOriginalProperties(original);
		for (Object key : props.keySet())
			original.setProperty(key.toString(), props.getProperty(key.toString()));
		FileOutputStream fileWriter = new FileOutputStream(new File(fileName));
		original.store(fileWriter, "Recorded on " + new Date());
		fileWriter.close();
	}

	private void collectOriginalProperties(Properties original) {
		try {
			FileInputStream fileReader = new FileInputStream(new File(fileName));
			original.load(fileReader);
			fileReader.close();
		} catch (Exception e) {
			//
		}
	}

	// @Override
	public boolean isRecording() {
		return true;
	}
}

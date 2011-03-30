/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.dynamicVariable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import fitlibrary.exception.FitLibraryException;

public class GlobalDynamicVariables extends DynamicVariablesMap {
	public GlobalDynamicVariables() {
		//
	}

	public GlobalDynamicVariables(DynamicVariables dynamicVariables) {
		super(dynamicVariables);
	}

	// @Override
	public boolean addFromPropertiesFile(String fileName) {
		try {
			InputStream in = new FileInputStream(fileName);
			Properties properties = new Properties();
			properties.load(in);
			in.close();
			putAll(properties);
		} catch (FileNotFoundException e) {
			throw new FitLibraryException("File not found");
		} catch (IOException e) {
			throw new FitLibraryException("Problem reading Properties file: " + e);
		}
		return true;
	}

	// @Override
	public void addFromUnicodePropertyFile(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)),
				"UTF8"));
		while (true) {
			String line = reader.readLine();
			if (line == null)
				break;
			int separator = line.indexOf("=");
			if (separator >= 0) {
				String key = line.substring(0, separator).trim();
				String value = line.substring(separator + 1).trim();
				put(key, value);
			}
		}
		reader.close();
	}

	// @Override
	public void putParameter(String key, Object value) {
		throw new RuntimeException("Cannot put a parameter to a GlobalDynamicVariables");
	}

	// @Override
	public DynamicVariables popLocal() {
		throw new RuntimeException("Can't popLocal() when one is not pushed");
	}

	// @Override
	public DynamicVariables top() {
		return this;
	}
}

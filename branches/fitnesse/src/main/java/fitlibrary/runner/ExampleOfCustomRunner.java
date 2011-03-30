/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.runner;

import java.io.IOException;

/**
 * A simple example of using CustomRunner
 */
public class ExampleOfCustomRunner extends CustomRunner {
	public static void main(String args[]) throws IOException {
		new ExampleOfCustomRunner();
	}

	public ExampleOfCustomRunner() throws IOException {
		super("sum");
		makeTables();
		System.out.println(runAndReport());
	}

	private void makeTables() {
		addTable("Sum");
		addRow("a, b, sum()");
		addRow("3, 17, 20");

		addTable("fit.Summary");
	}
}

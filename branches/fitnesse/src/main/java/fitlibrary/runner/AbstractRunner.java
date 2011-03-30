/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.runner;

import java.io.PrintWriter;

import fitlibrary.table.Tables;

public class AbstractRunner {
	protected void outputHtml(PrintWriter output, Tables tables) {
		StringBuilder sb = new StringBuilder();
		tables.toHtml(sb);
		output.print(sb.toString());
	}

	protected void stackTrace(PrintWriter output, Exception e) {
		e.printStackTrace();
		output.print("<pre>\n");
		e.printStackTrace(output);
		output.print("</pre>\n");
	}
}

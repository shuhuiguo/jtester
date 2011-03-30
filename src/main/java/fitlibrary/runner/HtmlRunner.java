/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;

import fit.Counts;
import fit.Parse;
import fit.exception.FitParseException;
import fitlibrary.suite.BatchFitLibrary;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;
import fitlibrary.utility.ParseUtility;

public class HtmlRunner extends AbstractRunner {
	private Report report;

	public HtmlRunner() {
		//
	}

	public HtmlRunner(Report report) {
		this.report = report;
	}

	public Counts runInSuite(File inFile, File theReport, String encoding, Parse setUp, Parse tearDown,
			BatchFitLibrary batchFitLibrary) throws IOException {
		PrintWriter output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(theReport), encoding));
		String fileContents = read(inFile, encoding);
		if (fileContents.indexOf("<i>[Not a TEST]</i>") >= 0) {
			output.print(fileContents);
			output.close();
			return new Counts(0, 0, 0, 0);
		}
		try {
			Parse whole = integrateSetUpAndTearDown(new Parse(fileContents), setUp, tearDown);
			Tables tables = TableFactory.tables(whole);
			Counts counts = batchFitLibrary.doStorytest(tables).getCounts();
			outputHtml(output, tables);
			return counts;
		} catch (FitParseException e) {
			output.print(fileContents);
			return new Counts();
		} catch (Exception e) {
			stackTrace(output, e);
			return new Counts(0, 0, 0, 0);
		} finally {
			output.close();
		}
	}

	public Parse getParse(File inFile, String encoding) throws ParseException, IOException {
		return new Parse(read(inFile, encoding));
	}

	// The Joy of Parse!
	public static Parse integrateSetUpAndTearDown(Parse tables, Parse setUp, Parse tearDown) {
		Parse whole = tables;
		if (setUp != null) {
			whole = setUp;
			ParseUtility.appendToSetUp(whole, tables);
		}
		if (tearDown != null)
			ParseUtility.append(whole, tearDown);
		return whole;
	}

	protected String read(File input, String encoding) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(input), encoding));
		StringBuffer result;
		try {
			result = new StringBuffer();
			while (true) {
				String s = in.readLine();
				if (s == null)
					break;
				result.append(s);
				result.append("\n");
			}
		} finally {
			in.close();
		}
		String string = result.toString();
		if (report == null)
			return string;
		return report.addLinks(string, input);
	}

	public Parse collectTable(File file, String encoding) throws ParseException, IOException {
		return getParse(file, encoding);
	}
}

/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.batch;

import static fitlibrary.batch.FitLibraryRunner.RunParameters.ValidParameters.FIT_NESSE_DIRY;
import static fitlibrary.batch.FitLibraryRunner.RunParameters.ValidParameters.PORT;
import static fitlibrary.batch.FitLibraryRunner.RunParameters.ValidParameters.RESULTS_DIRY;
import static fitlibrary.batch.FitLibraryRunner.RunParameters.ValidParameters.RETRIES;
import static fitlibrary.batch.FitLibraryRunner.RunParameters.ValidParameters.SHOW_PASSES;
import static fitlibrary.batch.FitLibraryRunner.RunParameters.ValidParameters.SUITE_NAME;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TimeZone;

import fit.Counts;
import fitlibrary.batch.FitLibraryRunner.RunParameters.ValidParameters;
import fitlibrary.batch.fitnesseIn.ParallelFitNesseRepository;
import fitlibrary.batch.resultsOut.ParallelSuiteResult;
import fitlibrary.batch.testRun.FitLibraryTestEngine;
import fitlibrary.batch.testRun.ParallelTestRunner;
import fitlibrary.batch.testRun.RetryAwareFitLibraryTestEngine;
import fitlibrary.definedAction.DefineActionsOnPageSlowly;
import fitlibrary.differences.FitLibraryRunnerDifference;
import fitlibrary.exception.FitLibraryException;
import fitlibrary.log.ConfigureLoggingThroughFiles;
import fitlibrary.traverse.Traverse;

public class FitLibraryRunner {
	static int PORT_NO = 8980;

	public static void main(String[] args) throws IOException, InterruptedException {
		if (args.length > 0 && args[0].startsWith("-"))
			runWithNewArguments(args);
		else
			runWithOldArguments(args);
	}

	private static void runWithNewArguments(String[] args) throws IOException, InterruptedException {
		System.out.println("FitLibraryRunner diry is " + new File(".").getAbsolutePath());
		try {
			RunParameters runParameters = RunParameters.getRunParameters(args);
			String fitNesseDirectoryPath = runParameters.get(FIT_NESSE_DIRY);
			ConfigureLoggingThroughFiles.configure(fitNesseDirectoryPath + "/");

			runParallel(runParameters.get(SUITE_NAME), fitNesseDirectoryPath, runParameters.get(RESULTS_DIRY),
					runParameters.getBoolean(SHOW_PASSES), runParameters.getInt(PORT), runParameters.getInt(RETRIES),
					runParameters.getBoolean(ValidParameters.JUNIT_XML_OUTPUT));
		} catch (InvalidParameterException e) {
			error();
		} catch (NumberFormatException e) {
			error();
		}
	}

	private static void error() {
		System.err
				.println("Usage: fitlibrary.batch.FitLibraryRunner "
						+ ""
						+ "-suiteName suiteName [-fitNesseDiry fitNesseDiry] [-resultsDiry resultsDiry] [-showPasses true] [-port port]");
		System.exit(-1);
	}

	private static void runWithOldArguments(String[] args) throws IOException, InterruptedException {
		if (args.length != 3 && args.length != 4)
			error();
		String suiteName = args[0];
		String fitNesseDirectoryPath = args[1];
		String resultsDirectoryPath = args[2];
		boolean showPasses = args.length == 4;
		runParallel(suiteName, fitNesseDirectoryPath, resultsDirectoryPath, showPasses, PORT_NO, 0, false);
	}

	private static void runParallel(String suiteName, String fitNesseDirectoryPath, String resultsDirectoryPath,
			boolean showPasses, int port, int maxRetries, boolean junitXmlOutput) throws IOException,
			InterruptedException {
		verifyFitNesseDirectory(fitNesseDirectoryPath);
		DefineActionsOnPageSlowly.setFitNesseDiry(fitNesseDirectoryPath); // Hack
																			// this
																			// in
																			// for
																			// now.
		Traverse.setDifferenceStrategy(new FitLibraryRunnerDifference(fitNesseDirectoryPath));
		long start = System.currentTimeMillis();
		HashSet<String> passedTestList = new HashSet<String>();
		FitLibraryTestEngine testEngine = new RetryAwareFitLibraryTestEngine(passedTestList);
		int numLoops = 1;
		Counts counts;
		while (true) {
			ParallelTestRunner runner = new ParallelTestRunner(new ParallelFitNesseRepository(fitNesseDirectoryPath,
					port), testEngine, resultsDirectoryPath, showPasses, suiteName, junitXmlOutput);
			counts = runner.runSuite(suiteName, new ParallelSuiteResult(suiteName, showPasses));
			report(start, counts);
			if ((counts.exceptions + counts.wrong) == 0 || numLoops++ > maxRetries)
				break;

			System.out.println(" ");
			System.out.println("Some tests failed... Retry #" + (numLoops - 1));
		}

		System.exit(counts.wrong + counts.exceptions);
	}

	private static void verifyFitNesseDirectory(String fitNesseDirectoryPath) {
		File fitNesseDiry = new File(fitNesseDirectoryPath);
		if (!fitNesseDiry.isDirectory())
			throw new FitLibraryException("Not a directory: " + fitNesseDirectoryPath);
		if (!Arrays.asList(fitNesseDiry.list()).contains("FitNesseRoot"))
			throw new FitLibraryException("Does not contain FitNesseRoot: " + fitNesseDirectoryPath);
	}

	static String formatTime(long elapsedTimeMillis) {
		if (elapsedTimeMillis < 1000)
			return elapsedTimeMillis + " milliseconds.";

		String format = "s' seconds'";

		if (elapsedTimeMillis >= 60 * 1000)
			format = "m 'minutes' " + format;

		if (elapsedTimeMillis >= 60 * 60 * 1000)
			format = "h 'hours' " + format;

		DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		return df.format(new Date(elapsedTimeMillis)) + " (" + elapsedTimeMillis + " milliseconds).";
	}

	private static void report(long start, Counts counts) {
		System.out.println("Total right=" + counts.right + ", wrong=" + counts.wrong + ", ignores=" + counts.ignores
				+ ", exceptions=" + counts.exceptions);
		System.out.println("Time since start = " + formatTime(System.currentTimeMillis() - start));
	}

	public static RunParameters getRunParameters(String[] args) {
		return RunParameters.getRunParameters(args);
	}

	public static class RunParameters {
		public enum ValidParameters {
			SUITE_NAME {
				@Override
				public String parameterName() {
					return "suiteName";
				}

				@Override
				public String defaultValue() {
					return null;
				}
			},
			FIT_NESSE_DIRY {
				@Override
				public String parameterName() {
					return "fitNesseDiry";
				}

				@Override
				public String defaultValue() {
					return ".";
				}
			},
			RESULTS_DIRY {
				@Override
				public String parameterName() {
					return "resultsDiry";
				}

				@Override
				public String defaultValue() {
					return "runnerResults";
				}
			},
			SHOW_PASSES {
				@Override
				public String parameterName() {
					return "showPasses";
				}

				@Override
				public String defaultValue() {
					return "false";
				}
			},
			PORT {
				@Override
				public String parameterName() {
					return "port";
				}

				@Override
				public String defaultValue() {
					return "80";
				}
			},
			RETRIES {
				@Override
				public String parameterName() {
					return "retries";
				}

				@Override
				public String defaultValue() {
					return "0";
				}
			},
			JUNIT_XML_OUTPUT {
				@Override
				public String parameterName() {
					return "junitXmlOutput";
				}

				@Override
				public String defaultValue() {
					return "false";
				}
			};

			abstract String parameterName();

			abstract String defaultValue();

			public static ValidParameters parseFromParameterName(String parameterName) {
				for (ValidParameters param : ValidParameters.values()) {
					if (parameterName.equals(param.parameterName()))
						return param;
				}
				throw new InvalidParameterException(parameterName);
			}
		}

		private Map<ValidParameters, String> parameterMap = new HashMap<ValidParameters, String>();

		public RunParameters() {
			for (ValidParameters param : ValidParameters.values()) {
				parameterMap.put(param, param.defaultValue());
			}
		}

		public static RunParameters getRunParameters(String[] args) {
			RunParameters runParameters = new RunParameters();
			for (int i = 0; i < args.length; i++) {
				String tag = args[i];
				if (tag.startsWith("-")) {
					tag = tag.substring(1);
					i++;
					if (i < args.length)
						runParameters.put(ValidParameters.parseFromParameterName(tag), args[i]);
					else
						throw new InvalidParameterException(tag);
				}
			}
			if (runParameters.get(SUITE_NAME) == null)
				throw new InvalidParameterException("suiteName");
			return runParameters;
		}

		public int getInt(ValidParameters key) {
			return Integer.parseInt(get(key));
		}

		public boolean getBoolean(ValidParameters key) {
			return Boolean.parseBoolean(get(key));
		}

		public String get(ValidParameters key) {
			return parameterMap.get(key);
		}

		public void put(ValidParameters key, String value) {
			parameterMap.put(key, value);
		}
	}
}

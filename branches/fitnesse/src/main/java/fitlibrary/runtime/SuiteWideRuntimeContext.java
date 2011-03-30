package fitlibrary.runtime;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fitlibrary.config.Configuration;
import fitlibrary.flow.GlobalActionScope;
import fitlibrary.flow.IScope;
import fitlibrary.log.FileLogger;
import fitlibrary.runResults.TestResults;
import fitlibrary.traverse.TableEvaluator;
import fitlibrary.typed.TypedObject;

public class SuiteWideRuntimeContext implements Configuration {
	private Map<String, Integer> timeouts = new HashMap<String, Integer>();
	private FileLogger fileLogger = new FileLogger();
	private IScope scope;
	private TableEvaluator tableEvaluator;
	private GlobalActionScope global;
	private boolean keepUniCode = false;
	private boolean addTimings = false;
	private int maxErrorBeforeStopping = Integer.MAX_VALUE;
	private int maxFailsBeforeStopping = Integer.MAX_VALUE;

	public SuiteWideRuntimeContext(IScope scope, GlobalActionScope global) {
		this.scope = scope;
		this.global = global;
	}

	public void putTimeout(String name, int timeout) {
		timeouts.put(name, timeout);
	}

	public int getTimeout(String name, int defaultTimeout) {
		Integer timeout = timeouts.get(name);
		if (timeout == null)
			return defaultTimeout;
		return timeout;
	}

	public void reset() {
		timeouts = new HashMap<String, Integer>();
	}

	public void startLogging(String fileName) {
		fileLogger.start(fileName);
	}

	public void printToLog(String s) throws IOException {
		fileLogger.println(s);
	}

	public IScope getScope() {
		if (scope == null)
			throw new RuntimeException("No scope in runtime");
		return scope;
	}

	public void addNamedObject(String name, TypedObject typedObject) {
		scope.addNamedObject(name, typedObject);
	}

	public void setAbandon(boolean abandon) {
		scope.setAbandon(abandon);
	}

	public boolean isAbandoned(TestResults testResults) {
		return scope.isAbandon()
				|| (scope.isStopOnError() && testResults.problems()
						|| (testResults.getCounts().exceptions >= maxErrorBeforeStopping) || (testResults.getCounts().wrong >= maxFailsBeforeStopping));
	}

	public void setStopOnError(boolean stop) {
		scope.setStopOnError(stop);
	}

	public void stopAfterErrorsOrFails(int maxErrors, int maxFails) {
		this.maxErrorBeforeStopping = maxErrors;
		this.maxFailsBeforeStopping = maxFails;
	}

	public void SetTableEvaluator(TableEvaluator evaluator) {
		this.tableEvaluator = evaluator;
	}

	public TableEvaluator getTableEvaluator() {
		return tableEvaluator;
	}

	public GlobalActionScope getGlobal() {
		return global;
	}

	public boolean keepingUniCode() {
		return keepUniCode;
	}

	public void keepUnicode(boolean keep) {
		keepUniCode = keep;
	}

	public boolean isAddTimings() {
		return addTimings;
	}

	public void addTimings(boolean add) {
		this.addTimings = add;
	}
}

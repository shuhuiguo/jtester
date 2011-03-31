package fitlibrary.specify.global;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.listener.OnError;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.traverse.RuntimeContextual;

public class OnErrorHandler implements RuntimeContextual, OnError {
	private RuntimeContextInternal runtime;

	public boolean stopOnError(int fails, int errors) {
		runtime.currentRow().addShow("stopOnError with "+fails+" fails and "+errors+" errors");
		if (errors >= 2)
			runtime.currentRow().addShow("Stopping");
		return errors >= 2;
	}
	
	public Object listener() {
		return this;
	}
	
	public boolean fails() {
		return false;
	}

	public boolean exceptions() {
		throw new FitLibraryException("error");
	}

	public Object getSystemUnderTest() {
		return null;
	}

	public void setRuntimeContext(RuntimeContextInternal runtime) {
		this.runtime = runtime;
	}
}

package fitlibrary.listener;

public interface OnError {
	// The following method is called whenever there is an increase in either the
	// number of fails (wrongs) or the number of error (exceptions).
	// If it returns true, execution of the current storytest stops.
	boolean stopOnError(int fails, int errors);
}

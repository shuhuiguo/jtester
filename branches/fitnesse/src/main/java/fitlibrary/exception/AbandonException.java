package fitlibrary.exception;

public class AbandonException extends RuntimeException { // FitLibraryException
															// {
	private static final long serialVersionUID = 1L;

	public AbandonException() {
		super("abandon");
	}
}

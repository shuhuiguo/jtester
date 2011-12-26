package org.jtester.reflector.model;

public class YourTestedException extends Exception {
	private static final long serialVersionUID = -3003266231421877135L;

	public YourTestedException() {
	}

	public YourTestedException(String message) {
		super(message);
	}

	public YourTestedException(Throwable cause) {
		super(cause);
	}

	public YourTestedException(String message, Throwable cause) {
		super(message, cause);
	}
}

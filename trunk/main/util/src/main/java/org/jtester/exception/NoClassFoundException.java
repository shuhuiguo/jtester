package org.jtester.exception;

public class NoClassFoundException extends JTesterException {
	private static final long serialVersionUID = 1155479426741130255L;

	public NoClassFoundException(String error, Throwable throwable) {
		super(error, throwable);
	}

	public NoClassFoundException(String message) {
		super(message);
	}

	public NoClassFoundException(Throwable throwable) {
		super(throwable);
	}
}

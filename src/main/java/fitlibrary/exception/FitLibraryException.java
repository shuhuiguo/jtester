/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.exception;

public class FitLibraryException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FitLibraryException(String s) {
		super(s);
	}

	public FitLibraryException(Exception e) {
		super(e);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		FitLibraryException other = (FitLibraryException) obj;
		return messagesEqual(other) && causesEqual(other);
	}

	protected boolean messagesEqual(FitLibraryException other) {
		if (getMessage() == null)
			return other.getMessage() == null;
		if (other.getMessage() == null)
			return false;
		return getMessage().equals(other.getMessage());
	}

	protected boolean causesEqual(FitLibraryException other) {
		if (getCause() == null)
			return other.getCause() == null;
		if (other.getCause() == null)
			return false;
		return getCause().equals(other.getCause());
	}

	@Override
	public int hashCode() {
		return (getMessage() == null ? 0 : getMessage().hashCode()) + (getCause() == null ? 0 : getCause().hashCode());
	}
}

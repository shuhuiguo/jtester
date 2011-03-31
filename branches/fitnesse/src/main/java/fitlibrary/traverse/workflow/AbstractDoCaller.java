/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.traverse.workflow;

public abstract class AbstractDoCaller implements DoCaller {
	private Exception problem = null;

	// @Override
	public Exception problem() {
		return problem;
	}

	// @Override
	public boolean isProblem() {
		return problem != null;
	}

	protected void setProblem(Exception exception) {
		problem = exception;
	}

	// @Override
	public boolean partiallyValid() {
		return false;
	}

	// @Override
	public boolean isAmbiguous() {
		return false;
	}

	// @Override
	public String getPartialErrorMessage() {
		return "NOT AN ERROR";
	}

}

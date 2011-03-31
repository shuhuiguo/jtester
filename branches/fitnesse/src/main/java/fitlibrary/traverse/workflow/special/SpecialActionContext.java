/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.traverse.workflow.special;

import fitlibrary.closure.ICalledMethodTarget;
import fitlibrary.table.Row;
import fitlibrary.traverse.Evaluator;

public interface SpecialActionContext extends Evaluator {
	ICalledMethodTarget findMethodFromRow(Row row, int start, int extrasCellsOnEnd) throws Exception;

	void showAfterTable(String s);

	void setFitVariable(String variableName, Object result);

	void show(Row row, String text);

	void logMessage(String text);

	void showAsAfterTable(String title, String s);
}

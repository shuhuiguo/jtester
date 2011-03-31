/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.traverse.workflow.caller;

import java.util.List;

import org.apache.log4j.Logger;

import fitlibrary.closure.LookupMethodTarget;
import fitlibrary.log.FitLibraryLogger;
import fitlibrary.runResults.TestResults;
import fitlibrary.special.PositionedTarget;
import fitlibrary.table.Row;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.workflow.AbstractDoCaller;
import fitlibrary.typed.TypedObject;

public class ActionSpecial extends AbstractDoCaller {
	@SuppressWarnings("unused")
	private static Logger logger = FitLibraryLogger.getLogger(ActionSpecial.class);
	private List<PositionedTarget> positionedTargets;
	private Evaluator evaluator;

	public ActionSpecial(Row row, Evaluator evaluator, boolean sequencing, LookupMethodTarget lookupTarget) {
		this.evaluator = evaluator;
		String[] cells = new String[row.size()];
		for (int i = 0; i < row.size(); i++)
			cells[i] = row.text(i, evaluator);
		positionedTargets = lookupTarget.findActionSpecialMethod(evaluator, cells, sequencing);
	}

	@Override
	public boolean isAmbiguous() {
		int count = 0;
		for (PositionedTarget target : positionedTargets)
			if (target.isFound())
				count++;
		return count > 1;
	}

	// @Override
	public String ambiguityErrorMessage() {
		final String AND = " AND ";
		String message = "";
		for (PositionedTarget target : positionedTargets)
			if (target.isFound())
				message += AND + target.ambiguityErrorMessage();
		return message.substring(AND.length());
	}

	// @Override
	public boolean isValid() {
		return positionedTargets.size() == 1 && positionedTargets.get(0).isFound();
	}

	@Override
	public boolean partiallyValid() {
		return positionedTargets.size() == 1 && positionedTargets.get(0).partiallyValid();
	}

	@Override
	public String getPartialErrorMessage() {
		return positionedTargets.get(0).getPartialErrorMessage();
	}

	// @Override
	public TypedObject run(Row row, TestResults testResults) throws Exception {
		return positionedTargets.get(0).run(row, testResults, evaluator.getRuntimeContext());
	}
}

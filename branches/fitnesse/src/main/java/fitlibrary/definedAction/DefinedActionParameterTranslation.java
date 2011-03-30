/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.definedAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.table.Tables;

public class DefinedActionParameterTranslation {
	public static List<String> translateParameters(List<String> parameters, Tables body) {
		List<String> sorted = new ArrayList<String>(parameters);
		Collections.sort(sorted, new Comparator<String>() {
			// @Override
			public int compare(String arg0, String arg1) {
				if (arg0.length() < arg1.length())
					return 1;
				return 0;
			}
		});
		List<String> newNames = new ArrayList<String>(parameters);
		for (Table table : body)
			for (Row row : table)
				for (Cell cell : row)
					for (int i = 0; i < sorted.size(); i++) {
						String parameter = sorted.get(i);
						String pattern = Pattern.quote(parameter);
						String fullText = cell.fullText();
						String newParameter = "paRameRer__" + i;
						String atParameter = "@{" + newParameter + "}";
						if (!fullText.contains(atParameter)) {
							String replaced = fullText.replaceAll(pattern, atParameter);
							if (!replaced.equals(fullText)) {
								cell.setText(replaced);
								newNames.set(parameters.indexOf(parameter), newParameter);
							}
						}
					}
		return newNames;
	}

	public static boolean needToTranslateParameters(List<String> parameters, Tables body) {
		if (parameters.isEmpty())
			return false;
		for (Table table : body)
			for (Row row : table)
				for (Cell cell : row)
					for (String parameter : parameters)
						if (cell.fullText().contains("@{" + parameter + "}"))
							return false;
		return true;
	}
}

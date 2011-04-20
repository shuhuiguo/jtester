package org.jtester.hamcrest.matcher.json;

import static org.jtester.hamcrest.reflection.ReflectionComparatorFactory.createRefectionComparator;

import java.util.ArrayList;
import java.util.Collection;

import org.hamcrest.Description;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jtester.hamcrest.reflection.ReflectionComparator;
import org.jtester.hamcrest.reflection.ReflectionComparatorMode;
import org.jtester.hamcrest.reflection.difference.Difference;
import org.jtester.hamcrest.reflection.report.DefaultDifferenceReport;
import org.jtester.hamcrest.reflection.report.DifferenceReport;

public class JsonArrayValueEqMatcher extends JsonArrayMatcher {
	private final String key;
	private final Collection<String> expected;
	private ReflectionComparatorMode[] modes;

	public JsonArrayValueEqMatcher(String key, Collection<String> expected, ReflectionComparatorMode[] modes) {
		this.key = key;
		this.expected = expected;
		this.modes = modes == null ? null : modes.clone();
	}

	private Difference difference;

	@Override
	protected boolean matchJson(JSONArray jsonArray) {
		Collection<String> actuals = new ArrayList<String>();

		Object[] arr = jsonArray.toArray();
		for (Object o : arr) {
			if (!(o instanceof JSONObject)) {
				this.message = "the item of json array must be json object";
				return false;
			}
			Object value = ((JSONObject) o).get(key);
			actuals.add(value == null ? null : value.toString());
		}

		ReflectionComparator reflectionComparator = createRefectionComparator(modes);
		this.difference = reflectionComparator.getDifference(expected, actuals);

		return difference == null;
	}

	private String message = null;

	public void describeTo(Description description) {
		if (message != null) {
			description.appendText(message);
		}
		if (difference != null) {
			String message = "Incorrect value for key: " + this.key;
			description.appendText(message);
			DifferenceReport differenceReport = new DefaultDifferenceReport();
			description.appendText(differenceReport.createReport(difference));
		}
	}
}

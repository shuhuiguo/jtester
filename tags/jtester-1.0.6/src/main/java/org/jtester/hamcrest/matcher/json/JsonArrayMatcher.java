package org.jtester.hamcrest.matcher.json;

import org.hamcrest.BaseMatcher;
import org.json.simple.JSONArray;

public abstract class JsonArrayMatcher extends BaseMatcher<JSONArray> {

	public boolean matches(Object item) {
		JSONArray jsonArray = (JSONArray) item;
		return this.matchJson(jsonArray);
	}

	protected abstract boolean matchJson(JSONArray jsonArray);
}

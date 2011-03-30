package org.jtester.hamcrest.matcher.json;

import org.hamcrest.BaseMatcher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public abstract class JsonObjectMatcher extends BaseMatcher<JSONArray> {

	public boolean matches(Object item) {
		return this.matcheJsonObject((JSONObject) item);
	}

	protected abstract boolean matcheJsonObject(JSONObject json);
}

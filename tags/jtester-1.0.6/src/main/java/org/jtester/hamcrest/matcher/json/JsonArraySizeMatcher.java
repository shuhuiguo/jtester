package org.jtester.hamcrest.matcher.json;

import org.hamcrest.Description;
import org.json.simple.JSONArray;

public class JsonArraySizeMatcher extends JsonArrayMatcher {
	private final int size;
	private int jsonSize;

	public JsonArraySizeMatcher(int size) {
		this.size = size;
	}

	@Override
	protected boolean matchJson(JSONArray jsonArray) {
		this.jsonSize = jsonArray.size();
		return size == jsonSize;
	}

	public void describeTo(Description description) {
		description.appendText(String.format("expected size is %d,but actual size is %d", this.size, this.jsonSize));
	}

}

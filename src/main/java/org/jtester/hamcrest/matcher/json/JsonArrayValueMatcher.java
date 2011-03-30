package org.jtester.hamcrest.matcher.json;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonArrayValueMatcher extends JsonArrayMatcher {
	private final String key;
	private final Matcher<?> matcher;

	private Object actualValue;

	public JsonArrayValueMatcher(String key, Matcher<?> matcher) {
		this.key = key;
		this.matcher = matcher;
	}

	private int index = 0;

	@Override
	protected boolean matchJson(JSONArray jsonArray) {
		Object[] arr = jsonArray.toArray();
		for (Object o : arr) {
			JSONObject json = (JSONObject) o;
			Object value = json.get(this.key);

			index++;
			if (matcher.matches(value) == false) {
				this.actualValue = value;
				return false;
			}
		}
		return true;
	}

	public void describeTo(Description description) {
		description.appendText("the key[" + this.key + "] of json array must match ");
		matcher.describeTo(description);
		description.appendText(String.format(", actual not matched value[%s] in index[%d]", this.actualValue,
				this.index));
	}

}

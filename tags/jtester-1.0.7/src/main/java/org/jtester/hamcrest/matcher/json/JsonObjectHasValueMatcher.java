package org.jtester.hamcrest.matcher.json;

import org.hamcrest.Description;
import org.json.simple.JSONObject;

public class JsonObjectHasValueMatcher extends JsonObjectMatcher {
	private String key;
	private String value;
	private final String[] strings;

	public JsonObjectHasValueMatcher(String key, String value, String... strings) {
		this.key = key;
		this.value = value;
		this.strings = strings;
	}

	@Override
	protected boolean matcheJsonObject(JSONObject json) {
		boolean ret = this.jsonKeyValueEq(json, key, value);
		if (ret == false || strings == null || strings.length < 2) {
			return ret;
		}
		for (int loop = 0; loop < strings.length - 1; loop += 2) {
			key = strings[loop];
			value = strings[loop + 1];
			ret = this.jsonKeyValueEq(json, key, value);
			if (ret == false) {
				return ret;
			}
		}
		return true;
	}

	private Object actual;

	private boolean jsonKeyValueEq(JSONObject json, String key, String value) {
		this.actual = json.get(key);
		if (this.actual == null) {
			return value == null;
		} else {
			String jsonValue = this.actual.toString();
			return jsonValue.equals(value);
		}
	}

	public void describeTo(Description description) {
		description.appendText(String.format("json object expected key[%s] value is [%s], actual is [%s]", key, value,
				actual));
	}

}

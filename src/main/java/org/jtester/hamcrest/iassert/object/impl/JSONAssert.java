package org.jtester.hamcrest.iassert.object.impl;

import java.util.Collection;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jtester.hamcrest.iassert.common.impl.AllAssert;
import org.jtester.hamcrest.iassert.common.intf.IAssert;
import org.jtester.hamcrest.iassert.object.intf.IJSONArrayAssert;
import org.jtester.hamcrest.iassert.object.intf.IJSONAssert;
import org.jtester.hamcrest.iassert.object.intf.IJSONObjectAssert;
import org.jtester.hamcrest.matcher.json.JsonArraySizeMatcher;
import org.jtester.hamcrest.matcher.json.JsonArrayValueEqMatcher;
import org.jtester.hamcrest.matcher.json.JsonArrayValueMatcher;
import org.jtester.hamcrest.matcher.json.JsonObjectHasValueMatcher;
import org.jtester.hamcrest.reflection.ReflectionComparatorMode;
import org.jtester.utility.ArrayHelper;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class JSONAssert extends AllAssert<JSONValue, JSONAssert> implements IJSONAssert, IJSONArrayAssert,
		IJSONObjectAssert {

	public JSONAssert(String value, Class<? extends IAssert<?, ?>> clazE) {
		super(clazE);
		this.value = JSONValue.parse(value);
		this.type = AssertType.AssertThat;
	}

	public IJSONArrayAssert isArray() {
		Matcher<?> matcher = Is.is(JSONArray.class);
		return (IJSONArrayAssert) this.assertThat(matcher);
	}

	public IJSONObjectAssert isSimple() {
		Matcher<?> matcher = Is.is(JSONObject.class);
		return (IJSONObjectAssert) this.assertThat(matcher);
	}

	public IJSONArrayAssert keyValueEq(String key, Collection<String> expected, ReflectionComparatorMode... modes) {
		JsonArrayValueEqMatcher matcher = new JsonArrayValueEqMatcher(key, expected, modes);
		return this.assertThat(matcher);
	}

	public IJSONArrayAssert keyValueEq(String key, String[] expected, ReflectionComparatorMode... modes) {
		Collection<String> _expected = (List<String>) ArrayHelper.convert(expected);
		JsonArrayValueEqMatcher matcher = new JsonArrayValueEqMatcher(key, _expected, modes);
		return this.assertThat(matcher);
	}

	public IJSONArrayAssert keyValueMatch(String key, Matcher matcher) {
		JsonArrayValueMatcher _matcher = new JsonArrayValueMatcher(key, matcher);
		return this.assertThat(_matcher);
	}

	public IJSONObjectAssert hasKeyValues(String key, String value, String... strings) {
		JsonObjectHasValueMatcher matcher = new JsonObjectHasValueMatcher(key, value, strings);
		return this.assertThat(matcher);
	}

	public IJSONArrayAssert jsonSizeIs(int size) {
		JsonArraySizeMatcher matcher = new JsonArraySizeMatcher(size);
		return this.assertThat(matcher);
	}
}

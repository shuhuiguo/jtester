package org.jtester.hamcrest.matcher.property;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jtester.reflector.PropertiesAccessor;
import org.jtester.utility.ArrayHelper;

/**
 * 属性值集合或属性值作为一个对象，要满足指定的断言
 * 
 * @author darui.wudr
 * 
 */
public class PropertyMatcher extends BaseMatcher<Object> {
	private String property;
	private Matcher<?> matcher;

	public PropertyMatcher(String property, Matcher<?> matcher) {
		this.property = property;
		this.matcher = matcher;
	}

	private Object propertyValue = null;

	public boolean matches(Object actual) {
		if (actual == null) {
			return false;
		}
		if (ArrayHelper.isCollOrArray(actual)) {
			this.propertyValue = PropertiesAccessor.getArrayItemProperty(actual, property);
		} else {
			this.propertyValue = PropertiesAccessor.getProperty(actual, property);
		}

		return matcher.matches(propertyValue);
	}

	public void describeTo(Description description) {
		description.appendText("the propery[" + this.property + "] of object must match");
		matcher.describeTo(description);
		description.appendText(String.format(",but actual value is %s .", this.propertyValue));
	}
}

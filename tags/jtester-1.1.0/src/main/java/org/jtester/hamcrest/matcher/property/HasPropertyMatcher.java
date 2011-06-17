package org.jtester.hamcrest.matcher.property;

import java.util.Collection;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jtester.bytecode.reflector.impl.PropertiesAccessorImpl;

/**
 * 集合对象的指定属性集中“存在有”属性值符合指定的断言
 * 
 * @author darui.wudr
 * 
 */
public class HasPropertyMatcher extends BaseMatcher<Object> {
	private String property;
	private Matcher<?> matcher;

	public HasPropertyMatcher(String property, Matcher<?> matcher) {
		this.property = property;
		this.matcher = matcher;
	}

	public boolean matches(final Object actual) {
		if (actual == null) {
			return false;
		}
		this.propertyValues = PropertiesAccessorImpl.getArrayOrItemProperty(actual, property);

		for (Object o : this.propertyValues) {
			boolean isMatch = matcher.matches(o);
			if (isMatch) {
				return true;
			}
		}
		return false;
	}

	private Collection<?> propertyValues = null;

	public void describeTo(Description description) {
		description.appendText("the propery[" + this.property + "] of object must at least one match");
		matcher.describeTo(description);
		description.appendText(String.format(",but actual value is:%s, no matched value", this.propertyValues));
	}
}

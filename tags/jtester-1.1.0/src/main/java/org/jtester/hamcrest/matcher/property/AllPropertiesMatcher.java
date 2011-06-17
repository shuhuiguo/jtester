package org.jtester.hamcrest.matcher.property;

import java.util.Collection;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jtester.bytecode.reflector.impl.PropertiesAccessorImpl;

/**
 * 集合对象的指定属性集中“所有的”属性值符合指定的断言
 * 
 * @author darui.wudr
 * 
 */
public class AllPropertiesMatcher extends BaseMatcher<Object> {
	private String property;
	private Matcher<?> matcher;

	public AllPropertiesMatcher(String property, Matcher<?> matcher) {
		this.property = property;
		this.matcher = matcher;
	}

	public boolean matches(Object actual) {
		if (actual == null) {
			return false;
		}
		this.propertyValues = PropertiesAccessorImpl.getArrayOrItemProperty(actual, property);
		
		this.index = 0;
		for (Object o : this.propertyValues) {
			index++;
			if (matcher.matches(o) == false) {
				return false;
			}
		}
		return true;
	}

	private Collection<?> propertyValues = null;
	private int index = 0;

	public void describeTo(Description description) {
		description.appendText("the propery[" + this.property + "] of object must match");
		matcher.describeTo(description);
		description.appendText(String.format(",actual value is:%s in index[%d]", this.propertyValues, this.index));
	}
}

package org.jtester.hamcrest.matcher.property;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jtester.hamcrest.reflection.ReflectionComparatorMode;
import org.jtester.reflector.ReflectUtil;
import org.jtester.utility.StringHelper;

/**
 * 集合（数组）中对象的多个属性值集合(二维数组)，反射值与期望值相等
 * 
 * @author darui.wudr
 * 
 */
public class PropertiesArrayRefEqMatcher extends BaseMatcher<Object> {
	private String[] properties;
	private Object[][] expected;

	UnitilsReflectionMatcher matcher;

	public PropertiesArrayRefEqMatcher(String[] properties, Object[][] expected, ReflectionComparatorMode... modes) {
		if (properties == null || properties.length == 0) {
			throw new RuntimeException("properties list can't be null!");
		}
		this.properties = properties;
		this.expected = expected;
		this.matcher = new UnitilsReflectionMatcher(expected, modes);
	}

	public boolean matches(Object actual) {
		this.propertyValues = ReflectUtil.getArrayItemProperties(actual, this.properties);

		return matcher.matches(propertyValues);
	}

	private Object[][] propertyValues = null;

	public void describeTo(Description description) {
		description.appendText("the propery[" + StringHelper.toString(this.properties) + "] of object must match");

		description.appendText(String.format(",but actual value is:%s, not matched value[%s]", StringHelper
				.toString(this.propertyValues), StringHelper.toString(this.expected)));
	}
}

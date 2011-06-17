package org.jtester.hamcrest.matcher.property;

import java.util.Collection;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jtester.bytecode.reflector.impl.PropertiesAccessorImpl;
import org.jtester.utility.ArrayHelper;
import org.jtester.utility.StringHelper;

/**
 * 对象中的多个属性值(数组)，“依次”符合期望
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings("rawtypes")
public class PropertiesArrayMatcher extends BaseMatcher<Object> {
	private String[] properties;
	private Object[] expecteds;

	public PropertiesArrayMatcher(String[] properties, Object[] expecteds) {
		if (properties == null || expecteds == null) {
			throw new RuntimeException("method[propertyEq]: must specify property value or expected value");
		}
		if (properties.length != expecteds.length) {
			throw new RuntimeException(
					"method[propertyEq]: the size of property value and expected value should be equal.");
		}
		this.properties = properties;
		this.expecteds = expecteds;
	}

	Matcher currentMatcher;
	String currProperty;
	Object currPropValue;
	Object currObj;

	public boolean matches(Object actual) {
		Collection coll = ArrayHelper.convertToCollectionWithoutMap(actual);
		for (Object item : coll) {
			boolean match = matchEach(item);
			if (match == false) {
				currObj = item;
				return false;
			}
		}
		return true;
	}

	int index = 0;

	private boolean matchEach(Object actual) {
		index = 0;
		for (Object expected : expecteds) {
			currProperty = properties[index];
			index++;
			currPropValue = PropertiesAccessorImpl.getProperty(actual, currProperty);

			if (expected instanceof Matcher) {
				currentMatcher = (Matcher) expected;
			} else {
				currentMatcher = new UnitilsReflectionMatcher(expected, null);
			}
			boolean match = this.currentMatcher.matches(currPropValue);
			if (match == false) {
				return false;
			}
		}
		return true;
	}

	public void describeTo(Description description) {
		StringBuilder buff = new StringBuilder();
		buff.append(String.format("the propery[%s] of object[%s] must match", StringHelper.toString(this.properties),
				String.valueOf(currObj)));
		description.appendText(buff.toString());

		description.appendText(String.format(",but actual values[%d] is:%s, can't match: ", index, currPropValue));
		this.currentMatcher.describeTo(description);
	}
}

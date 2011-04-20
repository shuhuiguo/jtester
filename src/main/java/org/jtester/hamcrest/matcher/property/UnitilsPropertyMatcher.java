package org.jtester.hamcrest.matcher.property;

//import static junit.framework.Assert.assertNotNull;
import static org.jtester.hamcrest.reflection.ReflectionComparatorFactory.createRefectionComparator;

import java.util.Collection;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jtester.hamcrest.reflection.ReflectionComparator;
import org.jtester.hamcrest.reflection.ReflectionComparatorMode;
import org.jtester.hamcrest.reflection.difference.Difference;
import org.jtester.hamcrest.reflection.report.DefaultDifferenceReport;
import org.jtester.hamcrest.reflection.report.DifferenceReport;
import org.jtester.reflector.PropertiesAccessor;
import org.jtester.utility.ArrayHelper;

/**
 * 用反射的方式验证对象的属性值是否为期望值
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings("rawtypes")
public class UnitilsPropertyMatcher extends BaseMatcher<Object> {
	private Object expected;

	private String property;

	private ReflectionComparatorMode[] modes;

	public UnitilsPropertyMatcher(String property, Object expected, ReflectionComparatorMode[] modes) {
		this.property = property;
		this.expected = expected;
		this.modes = modes == null ? null : modes.clone();
	}

	public UnitilsPropertyMatcher(String property, Collection<?> expected, ReflectionComparatorMode[] modes) {
		this.property = property;
		this.expected = expected;
		this.modes = modes == null ? null : modes.clone();
	}

	public <T extends Object> UnitilsPropertyMatcher(String property, T[] expected, ReflectionComparatorMode[] modes) {
		this.property = property;
		this.expected = expected == null ? null : expected.clone();
		this.modes = modes == null ? null : modes.clone();
	}

	private Difference difference;

	public boolean matches(Object actual) {
		if (actual == null) {
			throw new RuntimeException("actual object can't be null!");
		}
		Collection _actualProps = PropertiesAccessor.getArrayItemProperty(actual, this.property);
		Collection _expectedProps = ArrayHelper.convertToCollectionWithoutMap(expected);

		ReflectionComparator reflectionComparator = createRefectionComparator(modes);
		this.difference = reflectionComparator.getDifference(_expectedProps, _actualProps);

		return difference == null;
	}

	public void describeTo(Description description) {
		if (difference != null) {
			String message = "Incorrect value for property: " + this.property;
			description.appendText(message);
			DifferenceReport differenceReport = new DefaultDifferenceReport();
			description.appendText(differenceReport.createReport(difference));
		}
	}
}

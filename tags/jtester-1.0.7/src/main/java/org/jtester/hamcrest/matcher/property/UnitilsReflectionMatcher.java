package org.jtester.hamcrest.matcher.property;

import static org.jtester.hamcrest.reflection.ReflectionComparatorFactory.createRefectionComparator;

import java.util.Collection;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jtester.hamcrest.reflection.ReflectionComparator;
import org.jtester.hamcrest.reflection.ReflectionComparatorMode;
import org.jtester.hamcrest.reflection.difference.Difference;
import org.jtester.hamcrest.reflection.report.DefaultDifferenceReport;
import org.jtester.hamcrest.reflection.report.DifferenceReport;

/**
 * 以反射的方式验证2个对象是否相等
 * 
 * @author darui.wudr
 * 
 */
public class UnitilsReflectionMatcher extends BaseMatcher<Object> {
	private Object expected;

	private ReflectionComparatorMode[] modes;

	public UnitilsReflectionMatcher(Object expected, ReflectionComparatorMode[] modes) {
		this.expected = expected;
		this.modes = modes == null ? null : modes.clone();
	}

	public UnitilsReflectionMatcher(Collection<?> expected, ReflectionComparatorMode[] modes) {
		this.expected = expected;
		this.modes = modes == null ? null : modes.clone();
	}

	public <T extends Object> UnitilsReflectionMatcher(T[] expected, ReflectionComparatorMode[] modes) {
		this.expected = expected == null ? null : expected.clone();
		this.modes = modes == null ? null : modes.clone();
	}

	public UnitilsReflectionMatcher(List<?> expected, ReflectionComparatorMode[] modes) {
		this.expected = expected;
		this.modes = modes == null ? null : modes.clone();
	}

	private Difference difference;

	public boolean matches(Object actual) {
		ReflectionComparator reflectionComparator = createRefectionComparator(modes);
		this.difference = reflectionComparator.getDifference(expected, actual);
		return difference == null;
	}

	public void describeTo(Description description) {
		if (difference != null) {
			DifferenceReport differenceReport = new DefaultDifferenceReport();
			description.appendText(differenceReport.createReport(difference));
		}
	}
}

package org.jtester.hamcrest.matcher.property.comparator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jtester.hamcrest.matcher.property.difference.Difference;
import org.jtester.hamcrest.matcher.property.reflection.ReflectionComparator;
import org.jtester.tools.commons.DateHelper;

public class EqStringComparator implements Comparator {

	public boolean canCompare(Object left, Object right) {
		return left instanceof String || left == null;
	}

	public Difference compare(Object left, Object right, boolean onlyFirstDifference,
			ReflectionComparator reflectionComparator) {
		// check if the same instance is referenced
		if (left == right) {
			return null;
		}
		// check if the left value is null
		if (left == null) {
			return new Difference("Left value null", left, right);
		}
		// check if the right value is null
		if (right == null) {
			return new Difference("Right value null", left, right);
		}

		if (right instanceof Date) {
			SimpleDateFormat df = DateHelper.getDateFormat((String) left);
			right = df.format((Date) right);
		} else {
			right = String.valueOf(right);
		}
		if (left.equals(right)) {
			return null;
		} else {
			return new Difference("Different object values", left, right);
		}
	}
}

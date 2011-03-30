package org.jtester.module.jmock;

import org.hamcrest.Matcher;
import org.jmock.syntax.MethodClause;
import org.jmock.syntax.ReceiverClause;

@Deprecated
public interface ICallMethod {
	<T> T oneOf(T mockObject);

	<T> T one(T mockObject);

	<T> T allowing(T mockObject);

	<T> T ignoring(T mockObject);

	MethodClause ignoring(Matcher<?> mockObjectMatcher);

	<T> T never(T mockObject);

	ReceiverClause atLeast(int count);

	ReceiverClause between(int minCount, int maxCount);

	ReceiverClause atMost(int count);

	MethodClause allowing(Matcher<?> mockObjectMatcher);

	ReceiverClause exactly(int count);
}

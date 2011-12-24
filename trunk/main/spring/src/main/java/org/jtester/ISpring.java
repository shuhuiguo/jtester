package org.jtester;

import org.jtester.spring.TestedSpringContext;

public interface ISpring extends IAssertion {
	TestedSpringContext spring = new TestedSpringContext();
}

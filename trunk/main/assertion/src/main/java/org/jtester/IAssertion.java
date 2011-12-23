package org.jtester;

import org.jtester.assertion.TheStyleAssertion;
import org.jtester.assertion.WantStyleAssertion;

public interface IAssertion {
	final WantStyleAssertion want = new WantStyleAssertion();

	final TheStyleAssertion the = new TheStyleAssertion();
}

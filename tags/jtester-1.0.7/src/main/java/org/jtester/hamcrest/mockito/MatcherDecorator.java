/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.jtester.hamcrest.mockito;

import java.io.Serializable;

import org.hamcrest.Matcher;

@SuppressWarnings({ "rawtypes" })
public interface MatcherDecorator extends Serializable {
	Matcher getActualMatcher();
}

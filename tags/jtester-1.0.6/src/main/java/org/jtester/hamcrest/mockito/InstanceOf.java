/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.jtester.hamcrest.mockito;

import java.io.Serializable;

import org.hamcrest.Description;

public class InstanceOf extends ArgumentMatcher<Object> implements Serializable {

    private static final long serialVersionUID = 517358915876138366L;
    private final Class<?> clazz;

    public InstanceOf(Class<?> clazz) {
        this.clazz = clazz;
    }

    public boolean matches(Object actual) {
        return (actual != null) && clazz.isAssignableFrom(actual.getClass());
    }

    public void describeTo(Description description) {
        description.appendText("isA(" + clazz.getName() + ")");
    }
}
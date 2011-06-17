/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.jtester.hamcrest.mockito;

import java.io.Serializable;

import org.hamcrest.SelfDescribing;

public interface ContainsExtraTypeInformation extends Serializable {
    SelfDescribing withExtraTypeInfo();

    boolean typeMatches(Object object);
}
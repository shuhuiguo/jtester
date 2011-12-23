package org.jtester.assertion.object.impl;

import org.jtester.assertion.common.impl.ReflectionAssert;
import org.jtester.assertion.object.intf.IObjectAssert;

public class ObjectAssert extends ReflectionAssert<Object, IObjectAssert> implements IObjectAssert {
	public ObjectAssert() {
		super(IObjectAssert.class);
		this.valueClaz = Object.class;
	}

	public ObjectAssert(Object bean) {
		super(bean, IObjectAssert.class);
		this.valueClaz = Object.class;
	}
}

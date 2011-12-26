package org.jtester.assertion.object.impl;

import org.jtester.assertion.object.intf.IFloatAssert;

public class FloatAssert extends NumberAssert<Float, IFloatAssert> implements IFloatAssert {
	public FloatAssert() {
		super(IFloatAssert.class);
		this.valueClaz = Float.class;
	}

	public FloatAssert(Float f) {
		super(f, IFloatAssert.class);
		this.valueClaz = Float.class;
	}
}

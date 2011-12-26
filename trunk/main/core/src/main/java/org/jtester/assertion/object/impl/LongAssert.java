package org.jtester.assertion.object.impl;

import org.jtester.assertion.object.intf.ILongAssert;

public class LongAssert extends NumberAssert<Long, ILongAssert> implements ILongAssert {
	public LongAssert() {
		super(ILongAssert.class);
		this.valueClaz = Long.class;
	}

	public LongAssert(Long l) {
		super(l, ILongAssert.class);
		this.valueClaz = Long.class;
	}
}

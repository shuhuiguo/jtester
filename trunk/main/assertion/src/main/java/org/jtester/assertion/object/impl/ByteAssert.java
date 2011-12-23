package org.jtester.assertion.object.impl;

import org.jtester.assertion.common.impl.AllAssert;
import org.jtester.assertion.object.intf.IByteAssert;

public class ByteAssert extends AllAssert<Byte, IByteAssert> implements IByteAssert {

	public ByteAssert(Byte value) {
		super(value, IByteAssert.class);
		this.valueClaz = Byte.class;
	}

	public ByteAssert() {
		super(IByteAssert.class);
		this.valueClaz = Byte.class;
	}
}

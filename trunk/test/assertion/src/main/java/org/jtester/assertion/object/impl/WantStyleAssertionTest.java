package org.jtester.assertion.object.impl;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.jtester.assertion.object.intf.IArrayAssert;
import org.jtester.assertion.object.intf.IBooleanAssert;
import org.jtester.assertion.object.intf.IByteAssert;
import org.jtester.assertion.object.intf.ICharacterAssert;
import org.jtester.assertion.object.intf.IDoubleAssert;
import org.jtester.assertion.object.intf.IFileAssert;
import org.jtester.assertion.object.intf.IFloatAssert;
import org.jtester.assertion.object.intf.IIntegerAssert;
import org.jtester.assertion.object.intf.ILongAssert;
import org.jtester.assertion.object.intf.IShortAssert;
import org.jtester.assertion.object.intf.IStringAssert;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "assertion" })
public class WantStyleAssertionTest extends JTester {

	public void wantAssert() {
		want.object(want.string(new String())).clazIs(IStringAssert.class);
		want.object(want.bool(true)).clazIs(IBooleanAssert.class);
		want.object(want.bool(Boolean.TRUE)).clazIs(IBooleanAssert.class);
		// // number
		want.object(want.number(Short.valueOf("1"))).clazIs(IShortAssert.class);
		want.object(want.number(1)).clazIs(IIntegerAssert.class);
		want.object(want.number(1L)).clazIs(ILongAssert.class);
		want.object(want.number(1f)).clazIs(IFloatAssert.class);
		want.object(want.number(1d)).clazIs(IDoubleAssert.class);
		want.object(want.character('c')).clazIs(ICharacterAssert.class);
		want.object(want.bite(Byte.MAX_VALUE)).clazIs(IByteAssert.class);

		want.object(want.array(new boolean[] {})).clazIs(IArrayAssert.class);
		want.object(want.file(new File(""))).clazIs(IFileAssert.class);
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void wantAssert_Failure() {
		want.fail("error message");
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void wantAssert_Failure2() {
		want.bool(true).isEqualTo(false);
	}

	public void wantNumber_BigDecimal() {
		want.number(new BigDecimal("100.256")).isEqualTo(new BigDecimal("100.256"));
	}

	public void wantNumber_BigInteger() {
		want.number(new BigInteger("10111111111111")).isEqualTo(new BigInteger("10111111111111"));
	}

	public void wantNumber_Byte() {
		want.number(new Byte("127")).isEqualTo(new Byte("127"));
	}
}

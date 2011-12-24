package org.jtester.assertion.object.impl;

import org.jtester.assertion.object.intf.IArrayAssert;
import org.jtester.assertion.object.intf.IBooleanAssert;
import org.jtester.assertion.object.intf.IByteAssert;
import org.jtester.assertion.object.intf.ICharacterAssert;
import org.jtester.assertion.object.intf.ICollectionAssert;
import org.jtester.assertion.object.intf.IDateAssert;
import org.jtester.assertion.object.intf.IDoubleAssert;
import org.jtester.assertion.object.intf.IFileAssert;
import org.jtester.assertion.object.intf.IFloatAssert;
import org.jtester.assertion.object.intf.IIntegerAssert;
import org.jtester.assertion.object.intf.ILongAssert;
import org.jtester.assertion.object.intf.IMapAssert;
import org.jtester.assertion.object.intf.INumberAssert;
import org.jtester.assertion.object.intf.IObjectAssert;
import org.jtester.assertion.object.intf.IShortAssert;
import org.jtester.assertion.object.intf.IStringAssert;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "assertion" })
public class TheStyleAssertionTest extends JTester {

	public void theAssert() {
		want.object(the.string()).clazIs(IStringAssert.class);
		want.object(the.bool()).clazIs(IBooleanAssert.class);
		want.object(the.number()).clazIs(INumberAssert.class);
		want.object(the.integer()).clazIs(IIntegerAssert.class);
		want.object(the.longnum()).clazIs(ILongAssert.class);
		want.object(the.doublenum()).clazIs(IDoubleAssert.class);
		want.object(the.floatnum()).clazIs(IFloatAssert.class);
		want.object(the.shortnum()).clazIs(IShortAssert.class);
		want.object(the.character()).clazIs(ICharacterAssert.class);
		want.object(the.bite()).clazIs(IByteAssert.class);
		want.object(the.array()).clazIs(IArrayAssert.class);
		want.object(the.map()).clazIs(IMapAssert.class);
		want.object(the.collection()).clazIs(ICollectionAssert.class);
		want.object(the.object()).clazIs(IObjectAssert.class);
		want.object(the.file()).clazIs(IFileAssert.class);
		want.object(the.calendar()).clazIs(IDateAssert.class);
		want.object(the.date()).clazIs(IDateAssert.class);
	}

	@Test
	public void testString() {
		want.object(new Integer(1)).eqToString("1");
	}
}

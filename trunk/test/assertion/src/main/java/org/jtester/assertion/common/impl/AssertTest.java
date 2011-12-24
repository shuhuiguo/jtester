package org.jtester.assertion.common.impl;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.jtester.IAssertion;
import org.jtester.assertion.common.intf.IAssert;
import org.jtester.junit.DataFrom;
import org.junit.Test;

@SuppressWarnings({ "rawtypes" })
public class AssertTest implements IAssertion {
	@Test
	@DataFrom("assertClass")
	public void wanted(IAssert<?, ?> as, Class claz) {
		want.object(as).propertyEq("valueClaz", claz);
	}

	public static Object[][] assertClass() {
		return new Object[][] { { the.bool(), Boolean.class }, /** <br> */
		{ the.array(), Object[].class }, /** <br> */
		{ the.bite(), Byte.class }, /** <br> */
		{ the.calendar(), Calendar.class }, /** <br> */
		{ the.character(), Character.class }, /** <br> */
		{ the.collection(), Collection.class }, /** <br> */
		{ the.date(), Date.class }, /** <br> */
		{ the.doublenum(), Double.class }, /** <br> */
		{ the.file(), File.class }, /** <br> */
		{ the.floatnum(), Float.class }, /** <br> */
		{ the.integer(), Integer.class }, /** <br> */
		{ the.longnum(), Long.class }, /** <br> */
		{ the.map(), Map.class }, /** <br> */
		{ the.object(), Object.class }, /** <br> */
		{ the.shortnum(), Short.class }, /** <br> */
		{ the.string(), String.class } };
	}

	@Test
	public void wantedMap() {
		want.object(the.map()).propertyEq("valueClaz", Map.class);
	}
}

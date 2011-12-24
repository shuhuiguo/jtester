package org.jtester.assertion.common.impl;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.jtester.IAssertion;
import org.jtester.assertion.common.intf.IAssert;
import org.jtester.beans.DataIterator;
import org.jtester.junit.DataFrom;
import org.jtester.junit.JTesterRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings({ "rawtypes" })
@RunWith(JTesterRunner.class)
public class AssertTest implements IAssertion {
	@Test
	@DataFrom("assertClass")
	public void wanted(IAssert<?, ?> as, Class claz) {
		want.object(as).propertyEq("valueClaz", claz);
	}

	public static Iterator assertClass() {
		return new DataIterator() {
			{
				data(the.bool(), Boolean.class);
				data(the.array(), Object[].class);
				data(the.bite(), Byte.class);
				data(the.calendar(), Calendar.class);
				data(the.character(), Character.class);
				data(the.collection(), Collection.class);
				data(the.date(), Date.class);
				data(the.doublenum(), Double.class);
				data(the.file(), File.class);
				data(the.floatnum(), Float.class);
				data(the.integer(), Integer.class);
				data(the.longnum(), Long.class);
				data(the.map(), Map.class);
				data(the.object(), Object.class);
				data(the.shortnum(), Short.class);
				data(the.string(), String.class);
			}
		};
	}

	@Test
	public void wantedMap() {
		want.object(the.map()).propertyEq("valueClaz", Map.class);
	}
}

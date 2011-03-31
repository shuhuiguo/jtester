/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.parser;

import java.text.SimpleDateFormat;
import java.util.Date;

import fitlibrary.DoFixture;

public class ObjectDelegateForDate extends DoFixture {
	private static SimpleDateFormat DATE_FORMAT = 
		   new SimpleDateFormat("yyyy/MM/dd HH:mm");

	public ObjectDelegateForDate() {
		registerParseDelegate(Date.class,DATE_FORMAT);
	}
	@SuppressWarnings("deprecation")
	public Date getDate() {
	    return new Date(2004-1900,2,3);
	}
}

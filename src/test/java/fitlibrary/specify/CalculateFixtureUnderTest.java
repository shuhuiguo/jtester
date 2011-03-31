/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;
import java.util.Calendar;

import fitlibrary.DoFixture;
import fitlibrary.parser.tree.ListTree;

public class CalculateFixtureUnderTest extends DoFixture {
	private int count = 1;
	
	public int plusAB(int a, int b) {
		return a + b;
	}
	public int sum(int a, int b) {
		return a + b;
	}
	public int minusAB(int a, int b) {
		return a - b;
	}
	public int plusA(int a) {
		return a;
	}
	public String getCamelFieldName(String name) {
		return name;
	}
	public String plusName(String name) {
		return name+"+";
	}
	public String exceptionMethod() {
		throw new RuntimeException();
	}
	public void voidMethod() {
		//
	}
	public int increment() {
		return count++;
	}
	public Calendar useCalendar(Calendar calendar) {
		return calendar;
	}
	public ListTree plus12(ListTree t1, ListTree t2) {
		return new ListTree("", new ListTree[]{ t1, t2 });
	}
}

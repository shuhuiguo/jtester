/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fit.specify;
import fit.ColumnFixture;
import java.util.Calendar;

public class ColumnFixtureUnderTest extends ColumnFixture { //COPY:ALL
	private int count = 1;
	public int a, b; //COPY:ALL
	public String camelFieldName;
	public Calendar calendar;
	
	public int plus() { //COPY:ALL
		return a + b; //COPY:ALL
	} //COPY:ALL
	public int minus() {
		return a - b;
	}
	public String getCamelFieldName() {
		return camelFieldName;
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
	public Calendar useCalendar() {
		return calendar;
	}
} //COPY:ALL

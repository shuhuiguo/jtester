package org.jtester.core.junit;

import java.util.Iterator;

import org.jtester.core.IJTester.DataIterator;

public class DataCase {
	public static Iterator<Object[]> dataWithParameter() {
		return new DataIterator() {
			{
				data("darui.wu");
				data("jobs.he");
			}
		};
	}
}

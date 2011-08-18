package org.jtester.core.testng;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class DataProviderIteratorTest extends JTester {

	@Test
	public void testCheckDataLength() {
		try {
			new DataProviderIterator() {
				{
					put(1);
					put(1, 2);
				}
			};
			want.fail();
		} catch (RuntimeException e) {
			String message = e.getMessage();
			want.string(message).containsInOrder("DataProvider error", "length is 1", "(data index 2)", "length is 2");
		}
	}

	public void testPutEmpty() {
		try {
			new DataProviderIterator() {
				{
					put();
					put(1, 2);
				}
			};
			want.fail();
		} catch (RuntimeException e) {
			String message = e.getMessage();
			want.string(message).contains("provider data(index 1) error");
		}
	}
}

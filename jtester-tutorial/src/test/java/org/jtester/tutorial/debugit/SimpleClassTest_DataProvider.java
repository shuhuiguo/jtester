package org.jtester.tutorial.debugit;

import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SimpleClassTest_DataProvider {

	@Test(dataProvider = "isAge18Data")
	public void testIsAge18(Integer actualAge, boolean expectedResult) {
		boolean actualResult = SimpleClass.isAge18(actualAge);
		Assert.assertTrue(actualResult == expectedResult);
	}

	@DataProvider
	public Object[][] isAge18Data() {
		return new Object[][] { { 17, false },// <br>
				{ 18, true },// <br>
				{ 19, false },// <br>
				{ null, false } // <br>
		};
	}
}

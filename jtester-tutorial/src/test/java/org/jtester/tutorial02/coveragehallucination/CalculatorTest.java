package org.jtester.tutorial02.coveragehallucination;

import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class CalculatorTest extends JTester {
	/**
	 * 测试覆盖率100%,但高测试覆盖率和高质量的软件能够被等同起来么？
	 */
	public void testDivide() {
		double result = Calculator.divide(2, 1);
		want.number(result).isEqualTo(2d);
	}

	@Test(dataProvider = "divideData")
	public void testDivide2(Integer op1, Integer op2) {
		Calculator.divide(op1, op2);
	}

	@DataProvider
	public Object[][] divideData() {
		return new Object[][] { { null, 1 }, { 1, null }, { 1, 0 } };
	}

}

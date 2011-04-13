package org.jtester.tutorial02.coveragehallucination;

public class Calculator {
	public static double divide(Integer operand1, Integer operand2) {
		return operand1 / operand2;
	}

//	public static double divide(Integer operand1, Integer operand2) {
//		if (operand2 == null || operand2 == 0) {
//			throw new RuntimeException("除数不能为零！");
//		}
//		return (operand1 == null ? 0 : operand1) / operand2;
//	}
}

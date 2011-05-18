package org.jtester.tutorial.debugit;

public class SimpleClass {
	private static final Integer AGE_18 = new Integer(18);

	public static boolean isAge18(Integer age) {
		return (AGE_18 == age);
	}

	public static void main(String[] args) {
		boolean isAge18 = SimpleClass.isAge18(19);
		System.out.println(isAge18);
	}
}

package org.jtester.tutorial.debugit;

public class SimpleClass {
	private static final Integer AGE_18 = new Integer(18);

	public static boolean isAge18(Integer age) {
		if (age == null) {
			throw new RuntimeException("年龄不能为空.");
		} else if (age < 1) {
			throw new RuntimeException("年龄不能小于一岁.");
		} else if (age > 200) {
			throw new RuntimeException("年龄不能大于人类正常可以活的时间");
		} else {
			return (AGE_18 == age);
		}
	}
}

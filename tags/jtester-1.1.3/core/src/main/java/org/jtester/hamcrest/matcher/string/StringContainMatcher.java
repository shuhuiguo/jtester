package org.jtester.hamcrest.matcher.string;

/**
 * 经过模式处理后，判断一个字符串是否包含另外一个字符串
 * 
 * @author darui.wudr
 * 
 */
public class StringContainMatcher extends StringMatcher {

	public StringContainMatcher(String expectedSubString) {
		super(expectedSubString);
	}

	@Override
	protected boolean match(String expected, String actual) {
		if (expected == null) {
			return false;
		} else {
			return actual.contains(expected);
		}
	}

	@Override
	protected String relationship() {
		if (modes == null || modes.length == 0) {
			return "containing";
		} else {
			return "containing by mode" + StringMode.toString(modes);
		}
	}

	public static StringContainMatcher contains(String sub) {
		StringContainMatcher matcher = new StringContainMatcher(sub);
		return matcher;
	}
}

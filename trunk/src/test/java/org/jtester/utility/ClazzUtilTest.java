package org.jtester.utility;

import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = { "JTester" })
public class ClazzUtilTest extends JTester {

	@Test(dataProvider = "provideClazzName")
	public void getPackFromClassName(String clazz, String pack) {
		want.string(ClazzUtil.getPackFromClassName(clazz)).isEqualTo(pack);
	}

	@DataProvider
	public Object[][] provideClazzName() {
		return new String[][] { { "", "" }, { "EefErr", "" },
				{ "org.jtester.utility.ClazzUtilTest", "org.jtester.utility" } };
	}

	@Test(dataProvider = "testCamel_Data")
	public void testCamel(String name, String camel) {
		String value = ClazzUtil.camel(name);
		want.string(value).isEqualTo(camel);
	}

	@DataProvider
	public Object[][] testCamel_Data() {
		return new String[][] { { "is a word", "isAWord" },// <br>
				{ "get an Word", "getAnWord" },// <br>
				{ "get\t an wWOd", "getAnWWOd" },// <br>
				{ "", "" },// <br>
				{ "Get an word", "GetAnWord" } // <br>
		};
	}

	@Test
	public void testCamel() {
		String value = ClazzUtil.camel("is", "an", "word");
		want.string(value).isEqualTo("isAnWord");
	}
}

package org.jtester.reflector.helper;

import org.jtester.reflector.helper.ClazzHelper;
import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = { "JTester" })
public class ClazzHelperTest extends JTester {

	@Test(dataProvider = "provideClazzName")
	public void getPackFromClassName(String clazz, String pack) {
		want.string(ClazzHelper.getPackFromClassName(clazz)).isEqualTo(pack);
	}

	@DataProvider
	public Object[][] provideClazzName() {
		return new String[][] { { "", "" }, { "EefErr", "" },
				{ "org.jtester.utility.ClazzUtilTest", "org.jtester.utility" } };
	}

	@Test(dataProvider = "dataProvider_testGetPathFromPath")
	public void testGetPathFromPath(String clazName, String path) {
		String _path = ClazzHelper.getPathFromPath(clazName);
		want.string(_path).isEqualTo(path);
	}

	@DataProvider
	public Object[][] dataProvider_testGetPathFromPath() {
		return new Object[][] { { "a.b.c.ImplClazz", "a/b/c" }, // <br>
				{ "ImplClazz", "" }, /** <br> **/
				{ ".ImplClazz", "" }
		/** <br> **/
		};
	}
}

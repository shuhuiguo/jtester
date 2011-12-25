package org.jtester.json.helper;

import org.jtester.IAssertion;
import org.jtester.beans.User;
import org.jtester.junit.DataFrom;
import org.junit.Test;

public class ClazzMapTest implements IAssertion {

	@Test
	@DataFrom("type_data")
	public void testGetClazzName(Object obj, String expected) {
		String clazzname = ClazzMap.getClazzName(obj);
		want.string(clazzname).start(expected);
	}

	public static Object[][] type_data() {
		return new Object[][] { { Integer.valueOf(1), "Integer" }, // <br>
				{ new int[1], "int[]@" }, // <br>
				{ new Integer[0], "Integer[]@" }, // <br>
				{ new String(), "string" }, // <br>
				{ new String[0], "string[]@" }, // <br>
				{ new User(), "org.jtester.beans.User@" }, // <br>
				{ new User[0], "[Lorg.jtester.beans.User;@" } // <br>
		};
	}

	@Test
	public void testGetClazzName_Primitive() {
		String clazzname = ClazzMap.getClazzName(1);
		want.string(clazzname).isEqualTo("Integer");
	}
}

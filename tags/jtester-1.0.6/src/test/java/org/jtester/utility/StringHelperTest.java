package org.jtester.utility;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

public class StringHelperTest extends JTester {

	@Test
	public void testJoin_Null() {
		String str = StringHelper.join(",", null);
		want.string(str).isEqualTo("");
	}

	@Test
	public void testJoin_Empty() {
		String str = StringHelper.join(",", new String[] {});
		want.string(str).isEqualTo("");
	}

	@Test
	public void testJoin_OneItem() {
		String str = StringHelper.join(",", new String[] { "one" });
		want.string(str).isEqualTo("one");
	}

	@Test
	public void testJoin_MultiItem() {
		String str = StringHelper.join(",", new String[] { "one", null });
		want.string(str).isEqualTo("one,null");
	}

}

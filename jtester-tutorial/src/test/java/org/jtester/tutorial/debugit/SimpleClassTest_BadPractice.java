package org.jtester.tutorial.debugit;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class SimpleClassTest_BadPractice {
	@Test
	public void testIsAge18() {
		assertFalse(SimpleClass.isAge18(1));
		assertFalse(SimpleClass.isAge18(17));
		assertTrue(SimpleClass.isAge18(18));
		assertFalse(SimpleClass.isAge18(19));
		assertFalse(SimpleClass.isAge18(200));

		// assertFalse(SimpleClass.isAge18(-1));
		// assertFalse(SimpleClass.isAge18(0));
		// assertFalse(SimpleClass.isAge18(null));
	}
}

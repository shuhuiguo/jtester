package org.jtester.spec;

import java.util.Set;

import org.jtester.hamcrest.matcher.property.reflection.EqMode;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

public class JSpecTest extends JTester {

	@Test
	public void testFindScenarioRunIndex() {
		System.setProperty(JSpec.SCENARIO_RUN_INDEX, "1,2");
		Set<Integer> indexs = JSpec.findScenarioRunIndex();
		want.list(indexs).reflectionEq(new Integer[] { 1, 2 }, EqMode.IGNORE_ORDER);
	}
}

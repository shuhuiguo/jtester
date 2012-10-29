package org.jtester.spec;

import org.jtester.hamcrest.matcher.string.StringMode;
import org.jtester.spec.scenario.JSpecScenario;
import org.testng.annotations.Test;

public class TestJSpec extends JSpec {

	@Test(dataProvider = "story")
	@Override
	public void runStory(JSpecScenario scenario) throws Throwable {
		try {
			this.run(scenario);
			want.fail();
		} catch (Exception e) {
			String error = e.getMessage();
			want.string(error).contains("parse scenario error.", StringMode.SameAsSpace);
		}
	}
}

package org.jtester.spec;

import org.jtester.spec.annotations.StoryFile;
import org.jtester.spec.annotations.StorySource;
import org.jtester.spec.annotations.StoryType;
import org.jtester.spec.annotations.When;
import org.jtester.spec.scenario.JSpecScenario;
import org.testng.annotations.Test;

@StoryFile(type = StoryType.TXT, source = StorySource.ClassPath)
public class SkipScenarioDemo extends JSpec {
	int skip = 0;

	@Test(dataProvider = "story", groups = "jspec")
	public void runStory(JSpecScenario scenario) throws Exception {
		try {
			this.run(scenario);
		} catch (Throwable e) {
			skip++;
		}
//		this.run(scenario);
	}

	@When
	public void emptyMethod() throws Exception {
		want.number(skip).isEqualTo(1);
	}
}
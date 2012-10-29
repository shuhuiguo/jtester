package org.jtester.spec;

import org.jtester.spec.annotations.Named;
import org.jtester.spec.annotations.StoryFile;
import org.jtester.spec.annotations.StorySource;
import org.jtester.spec.annotations.StoryType;
import org.jtester.spec.annotations.Then;
import org.jtester.spec.scenario.JSpecScenario;
import org.testng.annotations.Test;

@StoryFile(value = "SpecialCharacterXMLSpecDemo.xml", type = StoryType.XML, source = StorySource.ClassPath)
public class SpecialCharacterXMLSpecDemo extends JSpec {
	@Then
	public void checkString(final @Named("字符串") String input// <br>
	) throws Exception {
		want.string(input).notNull();
	}

	@Test(dataProvider = "story", groups = "jspec")
	@Override
	public void runStory(JSpecScenario scenario) throws Throwable {
		this.run(scenario);
	}
}

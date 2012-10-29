package org.jtester.spec;

import org.jtester.spec.annotations.Given;
import org.jtester.spec.annotations.Named;
import org.jtester.spec.annotations.StoryFile;
import org.jtester.spec.annotations.StorySource;
import org.jtester.spec.annotations.StoryType;
import org.jtester.spec.annotations.Then;
import org.jtester.spec.scenario.JSpecScenario;
import org.testng.annotations.Test;

@StoryFile(type = StoryType.TXT, source = StorySource.ClassPath)
public class SkipStepDemo extends JSpec {
	@Test(dataProvider = "story", groups = "jspec")
	public void runStory(JSpecScenario scenario) throws Throwable {
		this.run(scenario);
	}

	StringBuilder buff = null;

	@Given
	public void inputInitString(final @Named("初始值") String init// <br>
	) throws Exception {
		this.buff = new StringBuilder(init);
	}

	@Given
	public void appendThisString(final @Named("参数") String append// <br>
	) throws Exception {
		this.buff.append(",").append(append);
	}

	@Then
	public void checkFinalText(final @Named("合并字符串") String finalString// <br>
	) throws Exception {
		want.string(this.buff.toString()).isEqualTo(finalString);
	}
}
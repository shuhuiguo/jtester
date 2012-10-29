package org.jtester.spec;

import java.util.Map;

import org.jtester.spec.annotations.Given;
import org.jtester.spec.annotations.Named;
import org.jtester.spec.annotations.StoryFile;
import org.jtester.spec.annotations.StorySource;
import org.jtester.spec.annotations.StoryType;
import org.jtester.spec.annotations.Then;
import org.jtester.spec.scenario.JSpecScenario;
import org.testng.annotations.Test;

@StoryFile(value = "MapConvertDemo.xml", type = StoryType.XML, source = StorySource.ClassPath)
public class MapConvertXMLDemo extends JSpec {
	private Map<String, String> map;

	@Given
	public void giveAMapPara(final @Named("map") Map<String, String> map// <br>
	) throws Exception {
		this.map = map;
	}

	@Then
	public void checkMap(final @Named("map") DataMap map// <br>
	) throws Exception {
		want.map(this.map).propertyEqMap(map);
	}

	@Test(dataProvider = "story")
	@Override
	public void runStory(JSpecScenario scenario) throws Throwable {
		this.run(scenario);
	}
}
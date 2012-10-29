package org.jtester.spec;

import org.jtester.spec.annotations.Given;
import org.jtester.spec.annotations.Named;
import org.jtester.spec.annotations.StoryFile;
import org.jtester.spec.annotations.StoryType;
import org.jtester.spec.annotations.Then;
import org.jtester.spec.scenario.JSpecScenario;
import org.testng.annotations.Test;

@StoryFile(type = StoryType.XML, value = "TemplateMethodXMLSpec.xml")
public class TemplateMethodXMLSpec extends JSpec {

	@Test(groups = "jspec", dataProvider = "story")
	public void runStory(JSpecScenario scenario) throws Throwable {
		this.run(scenario);
	}

	private Integer[] intArray;

	private DataMap map;

	@Given
	public void initCondiction(final @Named("列表") Integer[] intArray,// <br>
			final @Named("Map对象") DataMap map// <br>
	) throws Exception {
		this.intArray = intArray;
		this.map = map;
	}

	@Then
	public void checkCondiction(final @Named("列表") Integer[] intArray, // <br>
			final @Named("Map对象") DataMap map// <br>
	) throws Exception {
		want.array(this.intArray).reflectionEq(intArray);
		want.map(this.map).reflectionEqMap(map);
	}
}

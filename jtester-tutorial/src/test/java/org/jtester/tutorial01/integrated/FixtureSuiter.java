package org.jtester.tutorial01.integrated;

import org.jtester.fit.FitRunner;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test
public class FixtureSuiter extends JTester {

	public void createChannelTask() {
		FitRunner.runFit(EsbChannelTaskManagerFixture.class, "createChannelTask.wiki");
	}

	public void testCustomizedTypeFixture() {
		FitRunner.runFit(CustomizeTypeFixture.class, "CustomizeTypeFixture.wiki");
	}

	public void singleValue_Integer() {
		FitRunner.runFit(CustomizeTypeFixture.class, "singleValue_Integer.wiki");
	}

	public void singleValue_Complex() {
		FitRunner.runFit(CustomizeTypeFixture.class, "singleValue_complex.wiki");
	}

	public void springUsage() {
		FitRunner.runFit(SpringUsageFixture.class, "springUsage.wiki");
	}
}

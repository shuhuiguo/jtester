package org.jtester.spec;

import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.io.CasePreservingResolver;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jtester.core.IJTester;

public class JBehaveDemo extends JUnitStory {
	@Override
	public Configuration configuration() {
		Configuration conf = super.configuration();
		conf.useStoryPathResolver(new CasePreservingResolver());
		conf.useStoryLoader(new LoadFromClasspath(this.getClass()));
		conf.useStoryReporterBuilder(new StoryReporterBuilder().withDefaultFormats().withFormats(Format.CONSOLE,
				Format.HTML));
		return conf;
	}

	// Here we specify the steps classes
	@Override
	public List<CandidateSteps> candidateSteps() {
		// varargs, can have more that one steps classes
		return new InstanceStepsFactory(configuration(), new JBehaveStep()).createCandidateSteps();
	}

	public static class JBehaveStep implements IJTester {

		private String greeting = "";

		private String guest = "";

		private String housemaster = "";

		private String date = "";

		@Given("$housemaster $greeting $guest on $date")
		public void giveGreeting(@Named("guest") String guest, @Named("greeting") String greeting,
				@Named("housemaster") String housemaster, @Named("date") String date) {
			this.greeting = greeting;
			this.guest = guest;
			this.housemaster = housemaster;
			this.date = date;
		}

		String wholeword = "";

		@When("do greeting")
		public void doGreeting() {
			this.wholeword = String.format("%s %s %s on %s.", this.housemaster, this.greeting, this.guest, this.date);
		}

		@Then("check the whole word is $expected")
		public void checkGreeting(String expected) {
			want.string(this.wholeword).isEqualTo(expected);
		}
	}
}

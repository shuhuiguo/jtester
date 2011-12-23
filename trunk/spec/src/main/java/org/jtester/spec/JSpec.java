package org.jtester.spec;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryLoader;
import org.jbehave.core.io.StoryPathResolver;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jtester.core.IJTester;
import org.jtester.spec.steps.JSpecConfiguration;
import org.jtester.spec.steps.JSpecStepsFactory;

public abstract class JSpec implements Embeddable, IJTester {
	protected Format[] defaultStoryFormat() {
		// CONSOLE and TXT reporting
		return new Format[] { Format.CONSOLE, Format.HTML };
	}

	protected StoryLoader defaultStoryLoader() {
		// where to find the stories
		return new LoadFromClasspath(this.getClass());
	}

	protected abstract JSpec getJTesterSpec();

	public Configuration configuration() {
		Format[] storyFormats = this.defaultStoryFormat();
		StoryLoader storyLoader = this.defaultStoryLoader();
		StoryReporterBuilder reportBuilder = new StoryReporterBuilder().withDefaultFormats().withFormats(storyFormats);

		Configuration configuration = new JSpecConfiguration().useStoryLoader(storyLoader).useStoryReporterBuilder(
				reportBuilder);
		return configuration;
	}

	// Here we specify the steps classes
	public List<CandidateSteps> candidateSteps() {
		// varargs, can have more that one steps classes
		Configuration configuration = this.configuration();
		JSpec spec = this.getJTesterSpec();
		return new JSpecStepsFactory(configuration, spec).createCandidateSteps();
	}

	@org.junit.Test
	@org.testng.annotations.Test
	public void run() throws Throwable {
		Embedder embedder = configuredEmbedder();
		StoryPathResolver pathResolver = embedder.configuration().storyPathResolver();
		String storyPath = pathResolver.resolve(this.getClass());
		try {
			embedder.runStoriesAsPaths(asList(storyPath));
		} finally {
			embedder.generateCrossReference();
		}
	}

	// ConfigurableEmbedder implements
	private Embedder embedder = new Embedder();
	private List<CandidateSteps> candidateSteps = new ArrayList<CandidateSteps>();
	private InjectableStepsFactory stepsFactory;

	public void useEmbedder(Embedder embedder) {
		this.embedder = embedder;
	}

	public void addSteps(CandidateSteps... steps) {
		addSteps(asList(steps));
	}

	public void addSteps(List<CandidateSteps> steps) {
		this.candidateSteps.addAll(steps);
	}

	public void useStepsFactory(InjectableStepsFactory stepsFactory) {
		this.stepsFactory = stepsFactory;
	}

	public InjectableStepsFactory stepsFactory() {
		return stepsFactory;
	}

	public Embedder configuredEmbedder() {
		embedder.useConfiguration(configuration());
		embedder.useCandidateSteps(candidateSteps());
		embedder.useStepsFactory(stepsFactory());
		return embedder;
	}
}
